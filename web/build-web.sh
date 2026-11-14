#!/bin/bash
# Builds web/dist (game + java.awt MIDP shim + WebMain as nfsmw-web.jar, plus index.html and the M3G natives).
# Serve web/dist over http, e.g.:  cd web/dist && python3 -m http.server 8080
set -e
cd "$(dirname "$0")"
rm -rf work out dist && mkdir -p work out dist
cp -r ../src/main/java/. work/
rm work/javax/microedition/lcdui/{Canvas,Display,Displayable,Font,Graphics,Image}.java work/javax/microedition/rms/RecordStore.java
cp -r shim/. work/
cp WebMain.java Harness.java work/
# Under CheerpJ the M3G natives are provided by JavaScript (m3g-natives.js), so skip System.loadLibrary("javam3g").
sed -i 's/System.loadLibrary("javam3g");//' work/javax/microedition/m3g/Platform.java
find work -name "*.java" > srcs.txt
javac -encoding UTF-8 --release 8 -nowarn -d out @srcs.txt 2>&1 | grep -v "^Note" || true
cp -r ../src/main/resources/. out/
jar cf dist/nfsmw-web.jar -C out .
cp m3g-natives.js index.html dist/
echo "built web/dist"
