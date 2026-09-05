#!/usr/bin/env bash
# Deploys the budget kill switch: a budget overrun detaches the billing account and stops every service.
set -euo pipefail

PROJECT=${PROJECT:-springdoc}
REGION=${REGION:-europe-west1}
BILLING_ACCOUNT=${BILLING_ACCOUNT:-0103FF-9B1617-481854}
TOPIC=billing-alerts
SA=billing-killswitch
SA_EMAIL="$SA@$PROJECT.iam.gserviceaccount.com"

gcloud config set project "$PROJECT"

gcloud services enable pubsub.googleapis.com cloudfunctions.googleapis.com \
	cloudbuild.googleapis.com artifactregistry.googleapis.com eventarc.googleapis.com \
	cloudbilling.googleapis.com cloudresourcemanager.googleapis.com

gcloud pubsub topics create "$TOPIC" 2>/dev/null || true

gcloud iam service-accounts create "$SA" \
	--display-name='Detaches billing when the budget is exceeded' 2>/dev/null || true

# projectManager carries resourcemanager.projects.deleteBillingAssignment, the permission that unlinks.
gcloud projects add-iam-policy-binding "$PROJECT" \
	--member="serviceAccount:$SA_EMAIL" --role=roles/billing.projectManager --condition=None

# The same identity backs the Eventarc trigger, so it must also receive and invoke.
gcloud projects add-iam-policy-binding "$PROJECT" \
	--member="serviceAccount:$SA_EMAIL" --role=roles/eventarc.eventReceiver --condition=None

gcloud functions deploy billing-killswitch \
	--gen2 --runtime=python312 --region="$REGION" \
	--source="$(dirname "$0")" --entry-point=stop_billing \
	--trigger-topic="$TOPIC" \
	--service-account="$SA_EMAIL" \
	--set-env-vars="TARGET_PROJECT=$PROJECT" \
	--max-instances=1 --memory=256Mi

gcloud run services add-iam-policy-binding billing-killswitch --region="$REGION" \
	--member="serviceAccount:$SA_EMAIL" --role=roles/run.invoker

echo
echo "Point the budget at the topic (Cloud Billing grants itself the publisher role):"
echo "  gcloud billing budgets update BUDGET_ID --billing-account=$BILLING_ACCOUNT \\"
echo "    --notifications-rule-pubsub-topic=projects/$PROJECT/topics/$TOPIC"
