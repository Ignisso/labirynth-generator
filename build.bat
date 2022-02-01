@echo off
del /S *.class > nul
cd src/main/java
echo Compiling .java files...
forfiles /S /M *.java /C "cmd /Q /C for %%I in (@relpath) do echo %%~I" > sources.list
javac @sources.list
del sources.list
echo Packing .class files into .jar...
forfiles /S /M *.class /C "cmd /Q /C for %%I in (@relpath) do echo %%~I" > sources.list
jar cmf MANIFEST.mf ../../../target/Labirynth.jar @sources.list
echo Labirynth.jar has been generated in ./target
del sources.list
cd ../../../