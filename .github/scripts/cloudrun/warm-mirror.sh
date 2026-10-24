#!/usr/bin/env bash
# Cloud Run does not read docker.io directly: it goes through mirror.gcr.io, a
# pull-through cache that only fetches an image once something asks it for one. A
# digest jib pushed a minute earlier is not in there yet, so the import fails with
# "Container import failed", or with "not found" for a repository the mirror has
# never seen at all. Asking for the manifest is what populates the cache, so do
# that here until it answers rather than let the deploy race it.
#
# The manifest alone is not enough: the mirror fetches each blob lazily too, so a
# commit that changes a layer (a dependency bump changes the libs layer of every
# demo) still loses the import race on that blob. Pull the config and every layer
# through the mirror as well; the unchanged ones are already cached and come back
# quickly.
#
# A digest that never turns up is only warned about. The mirror is anycast, so the
# node answering here need not be the one Cloud Run reads, and the deploy is still
# worth attempting.
#
# Usage: warm-mirror.sh <image-ref>...   where a ref is docker.io/<repo>@sha256:...
set -uo pipefail

# Polls a mirror URL until it answers 200, writing the body to $2. Fails after 2m.
fetch() {
	local url=$1 out=$2 code
	for attempt in $(seq 1 30); do
		code=$(curl -sSL -o "$out" -w '%{http_code}' \
			-H 'Accept: application/vnd.docker.distribution.manifest.v2+json' \
			"$url" || true)
		if [ "$code" = 200 ]; then
			echo "$attempt"
			return 0
		fi
		sleep 4
	done
	echo "$code"
	return 1
}

for ref in "$@"; do
	path=${ref#*/}
	repo=${path%@*}
	digest=${path#*@}
	manifest=$(mktemp)

	if ! attempts=$(fetch "https://mirror.gcr.io/v2/$repo/manifests/$digest" "$manifest"); then
		echo "::warning::$repo still answers $attempts from the mirror after 2m; deploying anyway"
		continue
	fi
	echo "$repo manifest mirrored after $attempts attempt(s)"

	for blob in $(jq -r '.config.digest, .layers[]?.digest' "$manifest"); do
		if ! attempts=$(fetch "https://mirror.gcr.io/v2/$repo/blobs/$blob" /dev/null); then
			echo "::warning::$repo blob $blob still answers $attempts from the mirror after 2m; deploying anyway"
		fi
	done
	echo "$repo blobs mirrored"
	rm -f "$manifest"
done
