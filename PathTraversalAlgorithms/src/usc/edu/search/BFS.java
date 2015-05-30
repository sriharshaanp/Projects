/**
 * 
 */
package usc.edu.search;

import java.util.LinkedList;
import java.util.List;

import usc.edu.utilities.FileData;
import usc.edu.utilities.Node;
import usc.edu.utilities.Utilities;

/**
 * @author Sri Harsha
 *
 */
public class BFS implements SearchAlgo{
	private int startNode,finalPathCost,numOfNodes;
	private String destinationNode,finalPath;
	private StringBuffer logPath=new StringBuffer(); 
	private List<Node> nodeTraversal = new LinkedList<Node>();
	private LinkedList<Node> enque = new LinkedList<Node>();

	public Boolean startSearch(FileData fileData) {
		Utilities utils = new Utilities();
		System.out.println("Starting the BFS !!!");
		checkForStartNode(fileData);
		numOfNodes = fileData.getNumberOfNodes();
		if(!bfsSearch(fileData)){
			int ind = logPath.toString().lastIndexOf("-");
			String temp_log = logPath.toString().substring(0, ind);
			utils.writeToFile(-999999,"No Path Available",temp_log);
			return false;
		}else{
			//utils.printResult(finalPathCost,finalPath,logPath.toString());
			utils.writeToFile(finalPathCost,finalPath,logPath.toString());
		}
		return true;
	}

	private boolean bfsSearch(FileData fileData) {
		int nodeCounter =1 , parentNodeId = 0, pathCost=0, stepCost=0;
		int[] visitedMatrix = fileData.getVisitedMatrix();
		Integer [][] adjMatrix = fileData.getAdjacencyMatrix();
		Node currentNode = null;
		Node node = createANewNode(fileData.getNodes().get(startNode),nodeCounter,parentNodeId,startNode,pathCost,stepCost);
		node.getActualPath().append(fileData.getNodes().get(startNode)+"-");
		enque.add(node);
		visitedMatrix[startNode]=1;
		while(!enque.isEmpty()){
			currentNode = enque.remove();
			nodeTraversal.add(currentNode);
			boolean found = false;
			if(!currentNode.getNodeName().equals(destinationNode)){
				logPath.append(currentNode.getNodeName()+"-");
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
			for (int i = 0; i < numOfNodes; i++) {
				if(adjMatrix[parentNodeId][i] > 0 && visitedMatrix[i] == 0 && i!=node.getNodeId()) {
					nodeCounter++;
					node = createANewNode(fileData.getNodes().get(i),nodeCounter,parentNodeId,i,pathCost+adjMatrix[parentNodeId][i],stepCost+1);//+adjMatrix[parentNodeId][i]);
					node.getActualPath().append(currentNode.getActualPath()+fileData.getNodes().get(i)+"-");
					if(currentNode.getNodeName().equals(destinationNode)){
						found = true;
					}
					if(!enque.isEmpty() && !found)
						enque = checkForNodesWithSimilarCosts(node,enque);
					else
						enque.add(node);
					visitedMatrix[i] =1;
				}
			}
		}
		return false;
	}



	private LinkedList<Node> checkForNodesWithSimilarCosts(Node node, LinkedList<Node> enque2) {
		Utilities utilities = new Utilities();
		int currentNode = enque2.size();
		for(int i=0;i<currentNode;i++){
			if(enque2.get(i).getStepCost()==node.getStepCost() 
					&& utilities.compareNodes(enque.get(i).getNodeName(),node.getNodeName())){//&& enque2.get(i).getNodeParentId()==node.getNodeParentId()
				enque2.add(i, node);
				return enque2;
			}
		}
		enque2.add(node);
		return enque2;
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
