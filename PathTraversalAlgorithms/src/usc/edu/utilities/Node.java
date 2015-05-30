/**
 * 
 */
package usc.edu.utilities;

/**
 * @author sriharsha
 *
 */
public class Node {
	int nodeCounter;
	int nodeId;
	String nodeName;
	int nodeParentId;
	int pathCost;
	int stepCost;
	StringBuilder actualPath = new StringBuilder();
	public Node(int nodeCounter,int nodeId, String nodeName,int nodeParentId,int pathCost,int stepCost) {
		this.nodeCounter = nodeCounter;
		this.nodeId = nodeId;
		this.nodeParentId = nodeParentId;
		this.pathCost = pathCost;
		this.nodeName = nodeName;
		this.stepCost = stepCost;
	}
	
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public int getNodeParentId() {
		return nodeParentId;
	}
	public void setNodeParentId(int nodeParentId) {
		this.nodeParentId = nodeParentId;
	}
	public int getPathCost() {
		return pathCost;
	}
	public void setPathCost(int pathCost) {
		this.pathCost = pathCost;
	}
	public int getNodeCounter() {
		return nodeCounter;
	}
	public void setNodeCounter(int nodeCounter) {
		this.nodeCounter = nodeCounter;
	}

	public StringBuilder getActualPath() {
		return actualPath;
	}

	public void setActualPath(StringBuilder actualPath) {
		this.actualPath = actualPath;
	}

	public int getStepCost() {
		return stepCost;
	}

	public void setStepCost(int stepCost) {
		this.stepCost = stepCost;
	}
	
}
