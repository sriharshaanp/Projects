/**
 * 
 */
package usc.gpb.utilities;

import java.util.List;

/**
 * @author sriharsha
 *
 */
public class LegalMove {
	int parentCol;
	int parentRow;
	int childCol;
	int childRow;
	int numberOfInterimPieces;
	String[][] currentStateBoard;
	
	public LegalMove(){
		super();
	}
	
	public LegalMove(Long currBoard,int legalPositionOnBoard,Long playerBoard,Long enemyBoard){
		this.currBoard = currBoard;
		this.legalPositionOnBoard = legalPositionOnBoard;
		this.playerBoard = playerBoard;
		this.enemyBoard = enemyBoard;
	}
	
	public LegalMove(LegalMove parentLegalMove) {
		this.currBoard = parentLegalMove.getCurrBoard();
		this.legalPositionOnBoard = parentLegalMove.getLegalPositionOnBoard();
		this.setPlayerBoard(parentLegalMove.getEnemyBoard());
		this.setEnemyBoard(parentLegalMove.getPlayerBoard());
	}

	//Code for using Bit Board
	public Long currBoard;
	public int legalPositionOnBoard;
	public Long playerBoard;
	public Long enemyBoard;
		
	public int getChildCol() {
		return childCol;
	}
	public void setChildCol(int childCol) {
		this.childCol = childCol;
	}
	public int getChildRow() {
		return childRow;
	}
	public void setChildRow(int childRow) {
		this.childRow = childRow;
	}
	public int getNumberOfInterimPieces() {
		return numberOfInterimPieces;
	}
	public void setNumberOfInterimPieces(int numberOfInterimPieces) {
		this.numberOfInterimPieces = numberOfInterimPieces;
	}
		
	public String[][] getCurrentStateBoard() {
		return currentStateBoard;
	}
	public void setCurrentStateBoard(String[][] currentStateBoard) {
		this.currentStateBoard = currentStateBoard;
	}
	public int getParentCol() {
		return parentCol;
	}
	public void setParentCol(int parentCol) {
		this.parentCol = parentCol;
	}
	public int getParentRow() {
		return parentRow;
	}
	public void setParentRow(int parentRow) {
		this.parentRow = parentRow;
	}
	/**
	 * @return the currBoard
	 */
	public Long getCurrBoard() {
		return currBoard;
	}
	/**
	 * @param currBoard the currBoard to set
	 */
	public void setCurrBoard(Long currBoard) {
		this.currBoard = currBoard;
	}
	/**
	 * @return the legalPositionOnBoard
	 */
	public int getLegalPositionOnBoard() {
		return legalPositionOnBoard;
	}
	/**
	 * @param legalPositionOnBoard the legalPositionOnBoard to set
	 */
	public void setLegalPositionOnBoard(int legalPositionOnBoard) {
		this.legalPositionOnBoard = legalPositionOnBoard;
	}

	/**
	 * @return the playerBoard
	 */
	public Long getPlayerBoard() {
		return playerBoard;
	}

	/**
	 * @param playerBoard the playerBoard to set
	 */
	public void setPlayerBoard(Long playerBoard) {
		this.playerBoard = playerBoard;
	}

	/**
	 * @return the enemyBoard
	 */
	public Long getEnemyBoard() {
		return enemyBoard;
	}

	/**
	 * @param enemyBoard the enemyBoard to set
	 */
	public void setEnemyBoard(Long enemyBoard) {
		this.enemyBoard = enemyBoard;
	}
	
}
