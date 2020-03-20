all: main.class

main.class:
	javac cli/*.java models/db/*.java models/file/*.java models/*.java *.java

run:
	java -cp mysql-connector-java-5.1.48.jar:. Main

clean:
	rm cli/*.class
	rm models/*.class
	rm models/db/*.class
	rm models/file/*.class
	rm *.class