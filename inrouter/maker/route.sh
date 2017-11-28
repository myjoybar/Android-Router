echo "Start make"
javac ./inrouter/maker/java/com/me/obo/maker/utils/*.java ./inrouter/maker/java/com/me/obo/maker/*.java -d inrouter/maker/class/  -cp inrouter/maker/libs/javapoet-1.9.0.jar
java -Djava.ext.dirs=inrouter/maker/libs -classpath inrouter/maker/class com.me.obo.maker.CodeMaker $1 $2
echo "End make"
