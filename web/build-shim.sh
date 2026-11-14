#!/bin/bash
# Builds the game against a real (java.awt-backed) MIDP layer and runs it headless.
set -e
cd "$(dirname "$0")"
rm -rf work out && mkdir -p work out
cp -r ../src/main/java/. work/
rm work/javax/microedition/lcdui/{Canvas,Display,Displayable,Font,Graphics,Image}.java work/javax/microedition/rms/RecordStore.java
cp -r shim/. work/
cp Harness.java work/
find work -name "*.java" > srcs.txt
javac -encoding UTF-8 --release 8 -nowarn -d out @srcs.txt 2>&1 | grep -v "^Note" || true
cp -r ../src/main/resources/. out/
echo "built web/out"
