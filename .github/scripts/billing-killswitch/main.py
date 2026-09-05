# Detaches the billing account from the project as soon as a budget alert reports an overrun.
import base64
import json
import os

import googleapiclient.discovery

PROJECT = os.environ["TARGET_PROJECT"]


def stop_billing(event, context):
	payload = json.loads(base64.b64decode(event["data"]).decode("utf-8"))
	cost = payload.get("costAmount", 0)
	budget = payload.get("budgetAmount", 0)

	if cost <= budget:
		print(f"under budget: {cost} <= {budget}")
		return

	billing = googleapiclient.discovery.build("cloudbilling", "v1", cache_discovery=False)
	name = f"projects/{PROJECT}"
	info = billing.projects().getBillingInfo(name=name).execute()

	# An empty billingAccountName is what actually detaches the project.
	if not info.get("billingAccountName"):
		print("billing already disabled")
		return

	billing.projects().updateBillingInfo(name=name, body={"billingAccountName": ""}).execute()
	print(f"BILLING DISABLED on {name}: cost {cost} exceeded budget {budget}")
