#!/bin/sh
cd "src/main/java"
echo "Compiling .java files ..."
for f in $(find . -name '*.java'); do 
	echo $f >> sources.list
done
javac @sources.list
rm sources.list
echo "Packing .class files into .jar..."
for f in $(find . -name '*.class'); do 
	echo $f >> sources.list
done
mkdir ../../../target
jar cmf MANIFEST.mf ../../../target/Labirynth.jar @sources.list
echo "Labirynth.jar jas been generated in ./target"
rm sources.list
cd ../../../
