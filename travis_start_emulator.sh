#!/bin/bash

if [[ $ANDROID == 'true' ]]; then
  echo no | android create avd --force -n test -t android-22 --abi armeabi-v7a
  emulator -avd test -no-skin -no-audio -no-window &
  android-wait-for-emulator
  sleep 10
  adb shell input keyevent 82
fi