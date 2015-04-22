#!/bin/sh
#max_jar="C:\Program Files (x86)\Cycling '74\Max 7\resources\java-classes\lib\max.jar"
#jitter_jar="C:\Program Files (x86)\Cycling '74\Max 7\resources\java-classes\lib\jitter.jar"
#scala_jar="C:\Users\joulejang2012\Documents\Max 7\Packages\ni\lib\scala-library.jar"
#class_dir="C:\Users\joulejang2012\Documents\Max 7\Packages\ni\java-classes"
#src_dir="C:\Users\joulejang2012\Documents\Max 7\Packages\ni\java-classes"
#sc=";"

scalac -classpath "C:\Program Files (x86)\Cycling '74\Max 7\resources\java-classes\lib\*;C:\Users\joulejang2012\Documents\Max 7\Packages\ni\java-classes" -d "C:\Users\joulejang2012\Documents\Max 7\Packages\ni\java-classes" java-classes/ni/*.scala java-classes/ni/*.java java-classes/util/*.java
javac -classpath "C:\Program Files (x86)\Cycling '74\Max 7\resources\java-classes\lib\*;C:\Users\joulejang2012\Documents\Max 7\Packages\ni\lib\scala-library.jar;C:\Users\joulejang2012\Documents\Max 7\Packages\ni\java-classes" java-classes/ni/*.java java-classes/util/*.java