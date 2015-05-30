/**
 * 
 */
package usc.edu.utilities;

import java.util.Map;

/**
 * @author sriharsha
 *
 */
public class FileData {

	private int exampleNumber;
	private String source;
	private String destination;
	private int numberOfNodes;
	private Map<Integer,String> nodes;
	private Integer [][] adjacencyMatrix;
	private int [] visitedMatrix;
	public int[] getVisitedMatrix() {
		return visitedMatrix;
	}
	public void setVisitedMatrix(int[] visitedMatrix) {
		this.visitedMatrix = visitedMatrix;
	}
	public int getExampleNumber() {
		return exampleNumber;
	}
	public void setExampleNumber(int exampleNumber) {
		this.exampleNumber = exampleNumber;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public int getNumberOfNodes() {
		return numberOfNodes;
	}
	public void setNumberOfNodes(int numberOfNodes) {
		this.numberOfNodes = numberOfNodes;
	}
	public Map<Integer,String> getNodes() {
		return nodes;
	}
	public void setNodes(Map<Integer,String> nodes2) {
		this.nodes = nodes2;
	}
	public Integer[][] getAdjacencyMatrix() {
		return adjacencyMatrix;
	}
	public void setAdjacencyMatrix(Integer[][] adjacencyMatrix) {
		this.adjacencyMatrix = adjacencyMatrix;
	}
	
	
}
