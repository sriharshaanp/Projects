agent: AppMain.class 
		
AppMain.class: FileData.class Node.class BFS.class DFS.class UniformCostSearch.class Utilities.class
		javac -classpath . usc/edu/AppMain.java
		
Utilities.class: FileData.class
		javac -classpath . usc/edu/utilities/Utilities.java
				
FileData.class:
		javac -classpath . usc/edu/utilities/FileData.java 

Node.class:
		javac -classpath . usc/edu/utilities/Node.java
		
BFS.class: SearchAlgo.java
		javac -classpath . usc/edu/search/BFS.java
		
DFS.class: SearchAlgo.java
		javac -classpath . usc/edu/search/DFS.java
		
UniformCostSearch.class: SearchAlgo.java
		javac -classpath . usc/edu/search/UniformCostSearch.java
		
SearchAlgo.java:
		javac -classpath . usc/edu/search/SearchAlgo.java

FILE_PATH = ./input.txt				

run:
	java -classpath . usc.edu.AppMain $(FILE_PATH)