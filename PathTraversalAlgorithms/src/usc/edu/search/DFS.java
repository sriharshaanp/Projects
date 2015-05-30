/**
 * 
 */
package usc.edu.search;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import usc.edu.utilities.FileData;
import usc.edu.utilities.Node;
import usc.edu.utilities.Utilities;

/**
 * @author Sri Harsha
 *
 */
public class DFS implements SearchAlgo{
	private int startNode;
	private String destinationNode;
	private int numOfNodes;

	private int finalPathCost;
	private String finalPath;
	private StringBuffer logPath=new StringBuffer();
	private List<Node> nodeTraversal = new LinkedList<Node>();
	private Stack<Node> stack = new Stack<Node>();
	private Stack<Node> stack_temp = new Stack<Node>();

	public Boolean startSearch(FileData fileData) {
		Utilities utils = new Utilities();
		System.out.println("Starting the DFS !!!");
		checkForStartNode(fileData);
		numOfNodes = fileData.getNumberOfNodes();
		if(!dfsSearch(fileData)){
			int ind = logPath.toString().lastIndexOf("-");
			String temp_log = logPath.toString().substring(0, ind);
			//utils.printResult(-999999,"No Path Available",temp_log);
			utils.writeToFile(-999999,"No Path Available",temp_log);
			return false;
		}else{
			utils.printResult(finalPathCost,finalPath,logPath.toString());
			utils.writeToFile(finalPathCost,finalPath,logPath.toString());
		}
		return true;
	}

	private boolean dfsSearch(FileData fileData) {
		int nodeCounter =1 ,parentNodeId = 0,pathCost=0,stepCost=0;
		Integer [][] adjMatrix = fileData.getAdjacencyMatrix();
		int[] visitedMatrix = fileData.getVisitedMatrix();
		boolean found =false;
		Node currentNode = null;
		Node node = createANewNode(fileData.getNodes().get(startNode),nodeCounter,parentNodeId,startNode,pathCost,stepCost);
		node.getActualPath().append(fileData.getNodes().get(startNode)+"-");
		stack.push(node);
		visitedMatrix[startNode]=1;
		//currentNode = node;//--
		while(!stack.isEmpty()){
			currentNode = stack.pop();
			//printStack(stack);
			stack_temp.push(currentNode);
			//printStack2(stack_temp);
			nodeTraversal.add(currentNode);
			//visitedMatrix[currentNode.getNodeId()] =1;
			if(!currentNode.getNodeName().equals(destinationNode)){
				logPath.append(currentNode.getNodeName()+"-");
				//System.out.println(currentNode.getNodeName()+"-");
			}else {
				logPath.append(currentNode.getNodeName());
				int ind = currentNode.getActualPath().lastIndexOf("-");
				finalPath = currentNode.getActualPath().substring(0, ind);
				finalPathCost = currentNode.getPathCost();
				return true;
			}

			parentNodeId = currentNode.getNodeId();
			pathCost=currentNode.getPathCost();
			stepCost=currentNode.getStepCost();
			for (int i = numOfNodes-1; i >= 0; i--) {
				if(adjMatrix[parentNodeId][i] > 0) {
					if(visitedMatrix[i]==0){
						//checkForNodesWithSimilarCosts(adjMatrix,node,parentNodeId,i);
						nodeCounter++;
						node = createANewNode(fileData.getNodes().get(i),nodeCounter,parentNodeId,i,pathCost+adjMatrix[parentNodeId][i],stepCost+1);//adjMatrix[parentNodeId][i]);
						node.getActualPath().append(currentNode.getActualPath()+fileData.getNodes().get(i)+"-");
						if(currentNode.getNodeName().equals(destinationNode)){
							found = true;
						}
						if(!stack.isEmpty() && !found)
							stack = checkForNodesWithSimilarCosts(node,stack);
						else
							stack.push(node);
					}else{
						for(int j=0;j<stack_temp.size();j++){
							if(stack_temp.get(j).getNodeName().equalsIgnoreCase(fileData.getNodes().get(i))){ 
								//&& stack2.get(i).getNodeParentId()==node.getNodeParentId()){
								if(stack_temp.get(j).getStepCost()>stepCost+1){
									stack_temp.remove(j);
									nodeCounter++;
									node = createANewNode(fileData.getNodes().get(i),nodeCounter,parentNodeId,i,pathCost+adjMatrix[parentNodeId][i],stepCost+1);//adjMatrix[parentNodeId][i]);
									if(!stack.isEmpty() && !found)
										stack = checkForNodesWithSimilarCosts(node,stack);
									else
										stack.push(node);
								}
							}
						}
					}
					visitedMatrix[i] =1;
				}
			}
		}
		return false;
	}


	/*private void checkForNodesWithSimilarCosts(Integer[][] adjMatrix, Node node, int parentNodeId, int currentCounter) {
		int counter = currentCounter;
		Utilities utilities = new Utilities();
		currentCounter++;
		while(currentCounter<numOfNodes){
			if(adjMatrix[parentNodeId][counter] == adjMatrix[parentNodeId][currentCounter]){
				utilities.compareNodes(stack.get(i).getNodeName(),node.getNodeName());

			}
		}	
	}*/

	/*private void printStack(Stack<Node> stack2) {
		System.out.println("\nPrinting the First Stack !!");
		for(int i=0;i<stack2.size();i++){
			System.out.print(stack2.get(i).getNodeName()+"-");
		}
		System.out.println();
	}

	private void printStack2(Stack<Node> stack2) {
		System.out.println("Printing the second Stack !!");
		for(int i=0;i<stack2.size();i++){
			System.out.print(stack2.get(i).getNodeName()+"-");
		}
		System.out.println();
	}*/

	private Stack<Node> checkForNodesWithSimilarCosts(Node node, Stack<Node> stack2) {
		Utilities utilities = new Utilities();
		int currentNode = stack2.size();
		for(int i=0;i<currentNode;i++){
			if(stack2.get(i).getStepCost()==node.getStepCost() 
					&& !utilities.compareNodes(stack.get(i).getNodeName(),node.getNodeName())){ 
				//&& stack2.get(i).getNodeParentId()==node.getNodeParentId()){
				stack2.add(i, node);
				return stack2;
			}
		}
		stack2.push(node);
		return stack2;
	}

	private Node createANewNode(String nodeName, int nodeCounter,
			int parentNodeId, int nodeId, int pathCost, int stepCost) {
		Node node = new Node(nodeCounter,nodeId,nodeName,parentNodeId,pathCost,stepCost);
		return node;
	}

	private void checkForStartNode(FileData fileData) {
		for(int i=0;i<fileData.getNumberOfNodes();i++){
			if(fileData.getNodes().get(i).equalsIgnoreCase(fileData.getSource())){
				startNode = i;
			}
		}	
		destinationNode = fileData.getDestination();
	}
}