agent: AlphaBeta.class 
		
AlphaBeta.class: LegalMove.class FileData.class LegalMoves.class Utilities.class
		javac -classpath . usc/gpb/algo/AlphaBeta.java
		
Utilities.class: FileData.class
		javac -classpath . usc/gpb/utilities/Utilities.java
				
FileData.class:
		javac -classpath . usc/gpb/utilities/FileData.java 

LegalMove.class:
		javac -classpath . usc/gpb/utilities/LegalMove.java		

LegalMoves.class:
		javac -classpath . usc/gpb/utilities/LegalMoves.java
		
run:
	java -classpath . usc.gpb.algo.AlphaBeta
