#!/usr/bin/env bash
# Cloud Run does not read docker.io directly: it goes through mirror.gcr.io, a
# pull-through cache that only fetches an image once something asks it for one. A
# digest jib pushed a minute earlier is not in there yet, so the import fails with
# "Container import failed", or with "not found" for a repository the mirror has
# never seen at all. Asking for the manifest is what populates the cache, so do
# that here until it answers rather than let the deploy race it.
#
# A digest that never turns up is only warned about. The mirror is anycast, so the
# node answering here need not be the one Cloud Run reads, and the deploy is still
# worth attempting.
#
# Usage: warm-mirror.sh <image-ref>...   where a ref is docker.io/<repo>@sha256:...
set -uo pipefail

for ref in "$@"; do
	path=${ref#*/}
	repo=${path%@*}
	digest=${path#*@}
	url="https://mirror.gcr.io/v2/$repo/manifests/$digest"

	for attempt in $(seq 1 30); do
		code=$(curl -sS -o /dev/null -w '%{http_code}' \
			-H 'Accept: application/vnd.docker.distribution.manifest.v2+json' \
			"$url" || true)

		if [ "$code" = 200 ]; then
			echo "$repo mirrored after $attempt attempt(s)"
			break
		fi

		if [ "$attempt" = 30 ]; then
			echo "::warning::$repo still answers $code from the mirror after 2m; deploying anyway"
			break
		fi

		sleep 4
	done
done
