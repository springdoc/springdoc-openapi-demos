#!/usr/bin/env bash
# One-shot setup of the GCP side of the demos deployment: APIs, identities, keyless GitHub auth.
set -euo pipefail

PROJECT=${PROJECT:-springdoc}
REPO=${REPO:-springdoc/springdoc-openapi-demos}
DEPLOYER=gha-cloudrun-deployer
RUNTIME=cloudrun-runtime
POOL=github
PROVIDER=springdoc-demos

gcloud config set project "$PROJECT"

# STS and IAM Credentials back the federated tokens; run.googleapis.com needs billing enabled.
gcloud services enable iam.googleapis.com iamcredentials.googleapis.com sts.googleapis.com
gcloud services enable run.googleapis.com

# The identity GitHub Actions impersonates, and the one the services themselves run as.
gcloud iam service-accounts create "$DEPLOYER" \
	--display-name='GitHub Actions Cloud Run deployer' 2>/dev/null || true
gcloud iam service-accounts create "$RUNTIME" \
	--display-name='Cloud Run runtime identity for the demos' 2>/dev/null || true

DEPLOYER_EMAIL="$DEPLOYER@$PROJECT.iam.gserviceaccount.com"
RUNTIME_EMAIL="$RUNTIME@$PROJECT.iam.gserviceaccount.com"

# run.admin deploys the services; serviceAccountUser lets the deployer assign the runtime identity.
gcloud projects add-iam-policy-binding "$PROJECT" \
	--member="serviceAccount:$DEPLOYER_EMAIL" --role=roles/run.admin --condition=None
gcloud iam service-accounts add-iam-policy-binding "$RUNTIME_EMAIL" \
	--member="serviceAccount:$DEPLOYER_EMAIL" --role=roles/iam.serviceAccountUser

# The pool and provider that trust GitHub's OIDC issuer.
gcloud iam workload-identity-pools create "$POOL" \
	--location=global --display-name='GitHub Actions' 2>/dev/null || true
gcloud iam workload-identity-pools providers create-oidc "$PROVIDER" \
	--location=global --workload-identity-pool="$POOL" \
	--issuer-uri=https://token.actions.githubusercontent.com \
	--attribute-mapping='google.subject=assertion.sub,attribute.repository=assertion.repository' \
	--attribute-condition="assertion.repository == '$REPO'" 2>/dev/null || true

POOL_ID=$(gcloud iam workload-identity-pools describe "$POOL" --location=global --format='value(name)')

# Only workflows of this repository may impersonate the deployer.
gcloud iam service-accounts add-iam-policy-binding "$DEPLOYER_EMAIL" \
	--role=roles/iam.workloadIdentityUser \
	--member="principalSet://iam.googleapis.com/$POOL_ID/attribute.repository/$REPO"

PROVIDER_ID=$(gcloud iam workload-identity-pools providers describe "$PROVIDER" \
	--location=global --workload-identity-pool="$POOL" --format='value(name)')

echo
echo "Set these two repository secrets:"
echo "  GCP_WORKLOAD_IDENTITY_PROVIDER = $PROVIDER_ID"
echo "  GCP_SERVICE_ACCOUNT            = $DEPLOYER_EMAIL"
