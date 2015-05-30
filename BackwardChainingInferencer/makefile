agent: KBMain.class 
		
AppMain.class: FileData.class KB.class KBMain.class  Utilities.class
		javac -classpath . KBMain.java
		
Utilities.class:
		javac -classpath . Utilities.java
				
KBMain.class:
		javac -classpath . KBMain.java

FileData.class: 
		javac -classpath . FileData.java
		
KB.class: 
		javac -classpath . KB.java
		
FILE_PATH = ./input.txt				

run:
	java -classpath . KBMain $(FILE_PATH)
