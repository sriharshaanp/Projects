/**
 * 
 */
package usc.edu.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sri Harsha
 *
 */
public class Utilities {

	Boolean success = false;

	public Boolean readFromFile(String argsString, FileData fileData) {
		FileReader in = null;
		String bufferedLine = new String();
		int counter = 1, numberOfNodes = 0;
		BufferedReader br = null;
		try {
			in = new FileReader(new File(argsString));// TODO
																			// :
																			// Add
																			// (argsString)
																			// instaed
																			// of
																			// hardcoded
																			// file
																			// name
			br = new BufferedReader(in);
			while ((bufferedLine = br.readLine()) != null) {
				switch (counter) {
				case 1:// Reading the first line of the code
					int exampleNumber = Integer.parseInt(bufferedLine);
					fileData.setExampleNumber(exampleNumber);
					counter++;
					break;
				case 2:// Reading the Source Node
					String source = bufferedLine;
					fileData.setSource(source);
					counter++;
					break;
				case 3:// Reading the Destination Node
					String destination = bufferedLine;
					fileData.setDestination(destination);
					counter++;
					break;
				case 4:// Reading the Number of Nodes
					numberOfNodes = Integer.parseInt(bufferedLine);
					fileData.setNumberOfNodes(numberOfNodes);
					counter++;
					break;
				case 5:// Reading the Names of the nodes
					int internalCounter = 0;
					Map<Integer,String> nodes = new HashMap<Integer,String>();
					while (internalCounter < numberOfNodes) {
						if(internalCounter==0){
							nodes.put(internalCounter,bufferedLine);
							internalCounter++;
						}else if ((bufferedLine = br.readLine()) != null) {
							nodes.put(internalCounter,bufferedLine);
							internalCounter++;
						} else {
							throw new Exception(
									"Number of nodes given does not conform to actual number");
						}
					}
					fileData.setNodes(nodes);
					counter++;
					break;
				case 6:// Parsing the adjacency matrix
					internalCounter = 0;
					int i = 0;
					String[] nodesWeights = new String[numberOfNodes];
					Integer[][] adjacencyMatrix = new Integer[numberOfNodes][numberOfNodes];
					while (internalCounter < numberOfNodes) {
						if(internalCounter==0){
							// Create the adjacency matrix
							nodesWeights = bufferedLine.split(" ");
							for (int j = 0; j < numberOfNodes; j++) {
								adjacencyMatrix[i][j] = Integer.parseInt(nodesWeights[j]);
							}
							internalCounter++;
						}else if ((bufferedLine = br.readLine()) != null) {
							// Create the adjacency matrix
							nodesWeights = bufferedLine.split(" ");
							for (int j = 0; j < numberOfNodes; j++) {
								adjacencyMatrix[i][j] = Integer.parseInt(nodesWeights[j]);
							}
							internalCounter++;
						} else {
							throw new Exception(
									"Number of nodes given does not conform to actual number");
						}
						i++;
					}
					fileData.setAdjacencyMatrix(adjacencyMatrix);
					counter++;
					break;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//printMatrix(fileData.getAdjacencyMatrix(), numberOfNodes);
		return success;
	}

	/**
	 * Function to print out the matrix
	 * 
	 * @param adjacencyMatrix
	 * @param numberOfNodes
	 */
	public void printMatrix(Integer[][] adjacencyMatrix, int numberOfNodes) {
		for (int i = 0; i < numberOfNodes; i++) {
			for (int j = 0; j < numberOfNodes; j++) {
				System.out.print("" + adjacencyMatrix[i][j] + " ");
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
	 * Function to write the output to the file
	 * @param finalPathCost2
	 * @param finalPath2
	 * @param logPath
	 */
	public void writeToFile(int finalPathCost2, String finalPath2,
			String logPath) {
		FileWriter out = null;
		BufferedWriter bw = null;
		try {
			out = new FileWriter("./output.txt");
			bw = new BufferedWriter(out);
			bw.write(logPath.toString());
			bw.newLine();
			bw.write(finalPath2);
			if(finalPathCost2!=-999999){
				bw.newLine();
				bw.write(finalPathCost2+"");
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
}
