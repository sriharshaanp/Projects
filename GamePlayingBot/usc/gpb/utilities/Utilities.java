/**
 * 
 */
package usc.gpb.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sri Harsha
 *
 */
public class Utilities {
	int [][] nodeWeights = {{99,-8,8,6,6,8,-8,99},
			{-8,-24,-4,-3,-3,-4,-24,-8},
			{8,-4,7,4,4,7,-4,8},
			{6,-3,4,0,0,4,-3,6},
			{6,-3,4,0,0,4,-3,6},
			{8,-4,7,4,4,7,-4,8},
			{-8,-24,-4,-3,-3,-4,-24,-8},
			{99,-8,8,6,6,8,-8,99}
	};
	Boolean success = false;

	public Boolean readFromFile(String argsString, FileData fileData) {
		FileReader in = null;
		String bufferedLine = new String();
		int counter = 1;
		BufferedReader br = null;
		try {
			in = new FileReader(new File(argsString));
			br = new BufferedReader(in);
			while ((bufferedLine = br.readLine()) != null) {
				switch (counter) {
				case 1:// Reading the Example to Be Executed
					int exampleNumber = Integer.parseInt(bufferedLine);
					fileData.setExampleNumber(exampleNumber);
					counter++;
					break;
				case 2:// Reading the Player
					String player = bufferedLine;
					fileData.setPlayer(player);
					counter++;
					break;
				case 3:// Reading the Max Depth
					int timeInterval = Integer.parseInt(bufferedLine);
					fileData.setTimeInterval(timeInterval);
					counter++;
					break;
				case 4:// Parsing the Board
					int internalCounter = 0;
					int i = 0;
					int numberOfNodes = 8;
					String[] nodesWeights = new String[numberOfNodes];
					char[][] adjacencyMatrix = new char[numberOfNodes][numberOfNodes];
					while (internalCounter < numberOfNodes) {
						if(internalCounter==0){
							// Create the adjacency matrix
							nodesWeights = bufferedLine.split("");
							for (int j = 1; j <= numberOfNodes; j++) {
								adjacencyMatrix[i][j-1] = nodesWeights[j].charAt(0);
							}
							internalCounter++;
						}else if ((bufferedLine = br.readLine()) != null) {
							// Create the adjacency matrix
							nodesWeights = bufferedLine.split("");
							for (int j = 1; j <= numberOfNodes; j++) {
								adjacencyMatrix[i][j-1] = nodesWeights[j].charAt(0);
							}
							internalCounter++;
						} else {
							throw new Exception(
									"Number of nodes given does not conform to actual number");
						}
						i++;
					}
					fileData.setBoard(adjacencyMatrix);
					counter++;
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return success;
	}

	/**
	 * Function to print out the matrix
	 * 
	 * @param adjacencyMatrix
	 * @param numberOfNodes
	 */
	public void printMatrix(String[][] adjacencyMatrix, int numberOfNodes) {
		for (int i = 0; i < numberOfNodes; i++) {
			for (int j = 0; j < numberOfNodes; j++) {
				System.out.print("" + adjacencyMatrix[i][j] + "");
			}
			System.out.println("");
		}
	}

	/**
	 * Function to compare the alphabetical order of nodes when the cost is the
	 * same
	 * 
	 * @param fileData
	 * @param node1
	 * @param node2
	 * @return
	 */
	public boolean compareNodes(String node1, String node2) {
		if (node1.compareTo(node2) < 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Function to output to the command line
	 * @param finalPathCost
	 * @param finalPath
	 * @param logPath
	 */
	public void printResult(int finalPathCost, String finalPath,
			String logPath) {
		System.out.println(logPath);
		System.out.println(finalPath);
		System.out.println(finalPathCost);
	}

	public void writeToFile(String[][] bestMatrix, int numberOfNodes) {
		FileWriter out = null;
		BufferedWriter bw = null;
		try {
			out = new FileWriter("./output.txt");
			bw = new BufferedWriter(out);
			for (int i = 0; i < numberOfNodes; i++) {
				for (int j = 0; j < numberOfNodes; j++) {
					bw.write("" + bestMatrix[i][j] + "");
				}
				bw.newLine();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}