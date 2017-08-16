#!/bin/bash

export ANDBIN=/home/xtof/hdd/softs/androidsdk/build-tools/24.0.2
export ANDJAR=/home/xtof/softs/android-sdk-linux/platforms/android-19/android.jar

mkdir gen out

$ANDBIN/aapt package -f \
-M AndroidManifest.xml \
-I $ANDJAR \
-S res/ \
-J gen/ \
-m

libs=$(ls libs/*.jar)
lidx=0
imports=""
clp=""
for lib in $libs; do
    java -jar $ANDBIN/jill.jar --output libs/xx$lidx --verbose $lib
    imports=$imports" --import libs/xx"$lidx
    clp=$clp":"$lib
    lidx=$(($lidx+1))
done
echo $imports
echo $clp

mkdir out
java -jar $ANDBIN/jack.jar --classpath "$ANDJAR"$clp --output-dex out $imports src/ gen/

$ANDBIN/aapt package -f -M AndroidManifest.xml -I $ANDJAR -S res/ -F out/app.apk

find assets -type f -exec $ANDBIN/aapt add -v out/app.apk {} \;
cd out
$ANDBIN/aapt add app.apk classes.dex

# run it once in your HOME:
# keytool -genkey -v -keystore PATH/TO/YOUR_RELEASE_KEY.keystore -alias YOUR_ALIAS_NAME -keyalg RSA -keysize 2048 -validity 10000

jarsigner -verbose -keystore $HOME/apkkeystore -storepass xtof54 -keypass xtof54 -sigalg SHA1withRSA -digestalg SHA1 app.apk xtokapks


