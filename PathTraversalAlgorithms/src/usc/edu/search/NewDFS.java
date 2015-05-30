/**
 * 
 */
package usc.edu.search;

import usc.edu.utilities.FileData;
import usc.edu.utilities.Utilities;

/**
 * @author sriharsha
 *
 */
public class NewDFS implements SearchAlgo{
	int destinationNode = 4;
	String[] nodeName = {"A","B","C","D","A"};

	@Override
	public Boolean startSearch(FileData fileData) {
		dfsSearch(fileData.getAdjacencyMatrix(),fileData.getVisitedMatrix(),0);
		return null;
	}

	public boolean dfsSearch(Integer[][] adjacencyMatrix, int[] visitedMatrix,
			int currentNode) {
		boolean returnValue = false;
		if(currentNode==destinationNode){
				System.out.println("Goal Reached");
				System.out.println(visitedMatrix);
				return true;
		}else if(visitedMatrix[currentNode]==0){
				int counter =0;
				int count = -1;
				while(counter<5){
					if(adjacencyMatrix[currentNode][counter]>0){
						
						//Check if there are any more nodes with the same cost
						//if(adjacencyMatrix[currentNode][counter]==adjacencyMatrix[currentNode][count]){
						//		counter = checkForNodesInAlphabeticalOrder(count,counter); 
						//}
						/*if(counter<4){
							count++;
						}else{
							count =0;
						}
						}*/
						returnValue = dfsSearch(adjacencyMatrix,visitedMatrix,counter);
						//if(returnValue==true){
						//}
					}
					visitedMatrix[currentNode]=1;
					counter++;
			}
		}
		return false;
	}

	private int checkForNodesInAlphabeticalOrder(int count, int counter) {
		Utilities utils = new Utilities();
		if(utils.compareNodes(nodeName[count],nodeName[counter])){
			return count;
		}else{
			return counter;
		}
		
	}
}
