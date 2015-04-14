#!/Users/david/.homebrew/Cellar/fish/2.1.0/bin/fish
set max_jar /Applications/Max\ 6.1/Cycling\ \'74/java/lib/max.jar
set scala_jar lib/scala-library.jar
set class_dir java-classes
set src_dir java-classes
function javac
  /System/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Home/bin/javac $argv;
end

scalac -cp $max_jar:$src_dir:. -d $class_dir **.scala **.java
javac -cp $max_jar:$scala_jar:$src_dir:. **.java