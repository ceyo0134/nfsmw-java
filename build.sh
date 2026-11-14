#!/bin/sh
# Builds NFSMW.jar for J2ME Loader (Java 6 bytecode, javax stubs excluded).
# Needs JDK 8. Output: dist/NFSMW.jar
set -e
rm -rf out && mkdir -p out dist
find src/main/java -name "*.java" > srcs.txt
javac -encoding UTF-8 -source 1.6 -target 1.6 -g -nowarn -d out @srcs.txt
rm -rf out/javax
cp -r src/main/resources/. out/
cat > manifest.txt <<M
MIDlet-1: NFSMW, /icon.png, AppMain
MIDlet-Data-Size: 1024
MIDlet-Name: NFSMW
MIDlet-Vendor: Electronic Arts
MicroEdition-Configuration: CLDC-1.1
MicroEdition-Profile: MIDP-2.0
MIDlet-Version: 1.0.2
MIDlet-Icon: icon.png
M
jar cfm dist/NFSMW.jar manifest.txt -C out .
echo "built dist/NFSMW.jar"
