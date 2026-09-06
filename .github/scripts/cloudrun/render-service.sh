#!/usr/bin/env bash
# Turns a Cloud Run service definition template into something gcloud can apply.
#
# Cloud Run cannot import the multi-platform manifest list jib publishes, so every
# image has to be named by its amd64 child digest. __IMAGE__ is the deployed
# service's own image; __IMAGE_SOME_NAME__ is the sibling repository some-name,
# which is how the microservices demo names its six containers.
#
# Third-party images are multi-platform for the same reason and need the same
# treatment, but they are not ours and carry their own tag, so they are written
# __DIGEST:image:tag__ (for example __DIGEST:mongo:8.0__). Resolving them here
# also pins the sidecar to the exact bits a revision was deployed with, instead
# of letting a moving tag decide what a scale-up gets.
#
# Usage: render-service.sh <template> <service> <tag>
set -euo pipefail

template=$1
service=$2
tag=$3
registry=${REGISTRY:-docker.io/springdocdemos}

# digest <docker hub repository> <tag> -> the linux/amd64 child digest
digest() {
	local repo=$1 ref=$2 token
	token=$(curl -fsSL "https://auth.docker.io/token?service=registry.docker.io&scope=repository:$repo:pull" | jq -r .token)
	curl -fsSL -H "Authorization: Bearer $token" \
		-H 'Accept: application/vnd.docker.distribution.manifest.list.v2+json' \
		-H 'Accept: application/vnd.oci.image.index.v1+json' \
		"https://registry-1.docker.io/v2/$repo/manifests/$ref" \
		| jq -er '.manifests[] | select(.platform.architecture == "amd64" and .platform.os == "linux") | .digest'
}

rendered=$(sed "s|__SERVICE__|$service|g" "$template")

for placeholder in $(printf '%s' "$rendered" | grep -oE '__IMAGE(_[A-Z0-9]+)*__' | sort -u); do
	repo=$service
	if [ "$placeholder" != '__IMAGE__' ]; then
		repo=$(printf '%s' "$placeholder" | sed -e 's/^__IMAGE_//' -e 's/__$//' | tr '[:upper:]_' '[:lower:]-')
	fi
	rendered=${rendered//"$placeholder"/$registry/$repo@$(digest "springdocdemos/$repo" "$tag")}
done

for placeholder in $(printf '%s' "$rendered" | grep -oE '__DIGEST:[A-Za-z0-9._/-]+:[A-Za-z0-9._-]+__' | sort -u); do
	image=$(printf '%s' "$placeholder" | sed -e 's/^__DIGEST://' -e 's/__$//')
	# An official image lives under library/ in the registry API even though nobody
	# ever writes it that way.
	repo=${image%:*}
	case $repo in */*) ;; *) repo=library/$repo ;; esac
	rendered=${rendered//"$placeholder"/docker.io/$repo@$(digest "$repo" "${image##*:}")}
done

printf '%s\n' "$rendered"
