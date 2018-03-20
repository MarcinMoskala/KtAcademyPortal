#!/usr/bin/env bash
set -e
./gradlew :backend:test
./gradlew :common-client-js:test
./gradlew :common-client-jvm:test
git add -A
git commit -m "$1"
git push origin master