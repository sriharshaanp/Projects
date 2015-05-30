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
 * @author sriharsha
 *
 */
public class UniformCostSearch implements SearchAlgo{
		private int startNode,finalPathCost,numOfNodes;
	    private StringBuffer logPath=new StringBuffer();
	    private String destinationNode,finalPath;
		private List<Node> nodeTraversal = new LinkedList<Node>();
		private LinkedList<Node> enque = new LinkedList<Node>();

		public Boolean startSearch(FileData fileData) {
			Utilities utils = new Utilities();
			System.out.println("Starting the UCS !!!");
			checkForStartNode(fileData);
			numOfNodes = fileData.getNumberOfNodes();
			if(!ucsSearch(fileData)){
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
		
		public boolean ucsSearch(FileData fileData){
			Integer [][] adjMatrix = fileData.getAdjacencyMatrix();
			int[] visitedMatrix = fileData.getVisitedMatrix();
			Node currentNode = null;
			int nodeCounter =1 , parentNodeId = 0, pathCost=0;
			Node node = createANewNode(fileData.getNodes().get(startNode),nodeCounter,parentNodeId,startNode,pathCost,0);
			node.getActualPath().append(fileData.getNodes().get(startNode)+"-");
			enque.add(node);
			//visitedMatrix[startNode]=1;
			while(!enque.isEmpty()){
				currentNode = enque.remove();
				nodeTraversal.add(currentNode);
				if(visitedMatrix[currentNode.getNodeId()] == 0){
					if(!currentNode.getNodeName().equals(destinationNode)){
						logPath.append(currentNode.getNodeName()+"-");
					}else {
						logPath.append(currentNode.getNodeName());
						int ind = currentNode.getActualPath().lastIndexOf("-");
						finalPath = currentNode.getActualPath().substring(0, ind);
						finalPathCost = currentNode.getPathCost();
						return true;
					}
				}
				visitedMatrix[currentNode.getNodeId()] =1;
				parentNodeId = currentNode.getNodeId();
				pathCost=currentNode.getPathCost();
				//System.out.println(currentNode.getNodeName()+""+pathCost);
				for (int i = 0; i < numOfNodes; i++) {
					if(adjMatrix[parentNodeId][i] > 0 && visitedMatrix[i] == 0) {
						nodeCounter++;
						node = createANewNode(fileData.getNodes().get(i),nodeCounter,parentNodeId,i,pathCost+adjMatrix[parentNodeId][i],0);
						node.getActualPath().append(currentNode.getActualPath()+fileData.getNodes().get(i)+"-");
						if(!enque.isEmpty()){
							enque = checkForNodesWithSimilarCosts(node,enque);
						}else{
							enque.add(node);
						}
					}
				}
			}
			return true;
		}
	
		private LinkedList<Node> checkForNodesWithSimilarCosts(Node node, LinkedList<Node> enque2) {
			Utilities utilities = new Utilities();
			int currentNode = enque2.size();
			for(int i=0;i<currentNode;i++){
				if(enque2.get(i).getPathCost()>node.getPathCost()){
					enque2.add(i, node);
					return enque2;
				}else if(enque2.get(i).getPathCost()==node.getPathCost() 
						&& utilities.compareNodes(enque.get(i).getNodeName(),node.getNodeName())){
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
	
		/*private LinkedBlockingQueue<Integer> sortBasedOnValue(Integer [][] adjMatrix, FileData fileData) throws InterruptedException {
			LinkedBlockingQueue<Integer> localQueue = new LinkedBlockingQueue<Integer>();
			for(int i=0; i<enque.size(); i++){
				Utilities utils = new Utilities();
				int temp = 0;
				temp =enque.remove();
				Integer[] value = (Integer[])enque.toArray();
				for(int j=0;j<enque.size();j++){
					if(j!=i){
						localQueue.put(utils.compareNodes(fileData, temp, value[j]));
					}
				}
				
			}
			return null;
		}*/
	
		private void checkForStartNode(FileData fileData) {
			for(int i=0;i<fileData.getNumberOfNodes();i++){
				if(fileData.getNodes().get(i).equalsIgnoreCase(fileData.getSource())){
					startNode = i;
				}
			}	
			destinationNode = fileData.getDestination();
			}
		}