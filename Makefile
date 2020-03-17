all: main.class

main.class: Main.java CLI.java Database.java
	javac Main.java CLI.java Database.java

run:
	java -cp mysql-connector-java-5.1.48.jar:. Main

clean:
	rm *.class