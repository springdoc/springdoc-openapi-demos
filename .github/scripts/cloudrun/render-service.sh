#!/usr/bin/env bash
# Turns a Cloud Run service definition template into something gcloud can apply.
#
# Cloud Run cannot import the multi-platform manifest list jib publishes, so every
# image has to be named by its amd64 child digest. __IMAGE__ is the deployed
# service's own image; __IMAGE_SOME_NAME__ is the sibling repository some-name,
# which is how the microservices demo names its six containers.
#
# Usage: render-service.sh <template> <service> <tag>
set -euo pipefail

template=$1
service=$2
tag=$3
registry=${REGISTRY:-docker.io/springdocdemos}

digest() {
	local repo=springdocdemos/$1 token
	token=$(curl -fsSL "https://auth.docker.io/token?service=registry.docker.io&scope=repository:$repo:pull" | jq -r .token)
	curl -fsSL -H "Authorization: Bearer $token" \
		-H 'Accept: application/vnd.docker.distribution.manifest.list.v2+json' \
		"https://registry-1.docker.io/v2/$repo/manifests/$tag" \
		| jq -er '.manifests[] | select(.platform.architecture == "amd64" and .platform.os == "linux") | .digest'
}

rendered=$(sed "s|__SERVICE__|$service|g" "$template")

for placeholder in $(printf '%s' "$rendered" | grep -oE '__IMAGE(_[A-Z0-9]+)*__' | sort -u); do
	repo=$service
	if [ "$placeholder" != '__IMAGE__' ]; then
		repo=$(printf '%s' "$placeholder" | sed -e 's/^__IMAGE_//' -e 's/__$//' | tr '[:upper:]_' '[:lower:]-')
	fi
	rendered=${rendered//"$placeholder"/$registry/$repo@$(digest "$repo")}
done

printf '%s\n' "$rendered"
