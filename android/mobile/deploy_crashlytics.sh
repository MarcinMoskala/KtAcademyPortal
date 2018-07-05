#!/bin/bash

RELEASE_NOTES="`git log --oneline -1`"

echo "$RELEASE_NOTES" > crashlytics_release_notes.txt

./gradlew :android:mobile:crashlyticsUploadDistributionProdDebug
