/**
 * 
 */
package usc.gpb.utilities;


/**
 * @author sriharsha
 *
 */
public class FileData {

	private int exampleNumber;
	private int timeInterval;
	private String player;
	private int maxDepth;
	private int numberOfNodes;
	private char [][] board;
	public int getExampleNumber() {
		return exampleNumber;
	}
	public void setExampleNumber(int exampleNumber) {
		this.exampleNumber = exampleNumber;
	}
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public int getMaxDepth() {
		return maxDepth;
	}
	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}
	public int getNumberOfNodes() {
		return numberOfNodes;
	}
	public void setNumberOfNodes(int numberOfNodes) {
		this.numberOfNodes = numberOfNodes;
	}
	
	/**
	 * @return the timeInterval
	 */
	public int getTimeInterval() {
		return timeInterval;
	}
	/**
	 * @param timeInterval the timeInterval to set
	 */
	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}
	/**
	 * @return the board
	 */
	public char[][] getBoard() {
		return board;
	}
	/**
	 * @param board the board to set
	 */
	public void setBoard(char[][] board) {
		this.board = board;
	}
		
}
