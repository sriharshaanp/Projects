/**
 * 
 */
package usc.gpb.algo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import usc.gpb.utilities.FileData;
import usc.gpb.utilities.LegalMove;
import usc.gpb.utilities.LegalMoves;
import usc.gpb.utilities.Utilities;

/**
 * sriharsha
 *
 */

public class AlphaBeta {
	FileData fileData;
	Utilities utils = new Utilities();
	static AlphaBeta  aB = new AlphaBeta();
	private String maxPlayer ;
	private String minPlayer ;
	private char max ;
	private char min ;
	LegalMoves legalMoves = new LegalMoves();
	LegalMove bestMove = new LegalMove();
	int INDEX64[] = {
			0, 47,  1, 56, 48, 27,  2, 60,
			57, 49, 41, 37, 28, 16,  3, 61,
			54, 58, 35, 52, 50, 42, 21, 44,
			38, 32, 29, 23, 17, 11,  4, 62,
			46, 55, 26, 59, 40, 36, 15, 53,
			34, 51, 20, 43, 31, 22, 10, 45,
			25, 39, 14, 33, 19, 30,  9, 24,
			13, 18,  8, 12,  7,  6,  5, 63
	};
	
	static int [] nodeWeights = {99,-8,8,6,6,8,-8,99,
			-8,-24,-4,-3,-3,-4,-24,-8,
			8,-4,7,4,4,7,-4,8,
			6,-3,4,0,0,4,-3,6,
			6,-3,4,0,0,4,-3,6,
			8,-4,7,4,4,7,-4,8,
			-8,-24,-4,-3,-3,-4,-24,-8,
			99,-8,8,6,6,8,-8,99};
	
	private int minmaxPlay(int depth, String player, LegalMove legalMove,int alpha,int beta) {
		 
		if(gameEnd(legalMove,maxPlayer,minPlayer) || depth==10){
			return hueristicEval(legalMove);
		}

		Map<Integer, LegalMove> legalMoveMap = new HashMap<Integer, LegalMove>();
		legalMoveMap = legalMoves.findLegalMoves(legalMove);
		
		//code for Empty 
		if(legalMoveMap.isEmpty()){
			
			if(player==maxPlayer){
				int score = minmaxPlay(depth+1, minPlayer, legalMove, alpha, beta);
				if (score <= alpha){
					   return alpha;
				    }
					if( score < beta ){
					   beta = score; // beta acts like min in MiniMax
					}
					return beta;
				
			}else{
				int score = minmaxPlay(depth+1, maxPlayer, legalMove, alpha, beta);
				
					if (score >= beta){
						   return beta;
						
				        }
					if( score > alpha ){
						   alpha = score; // beta acts like min in MiniMax
							   aB.bestMove = legalMove;
					}
					return alpha;
			}
		}
		if(player.equals(maxPlayer)){
				return callMaxPlayer(depth,legalMoveMap,alpha,beta);
		}else{
				return callMinPlayer(depth,legalMoveMap,alpha,beta);
		}
	}
	
	private boolean gameEnd(LegalMove legalMove, String maxPlayer2,
			String minPlayer2) {
		Long own = legalMove.getPlayerBoard();
		Long enemy = legalMove.getEnemyBoard();
		Long empty = ~(own|enemy); 
		if(Long.numberOfLeadingZeros(empty) == 64){
			return true;	
		}
		if(enemy==0L){
			return true;
		}
		if(own==0L){
			return true;
		}
		return false;
	}

	private static int hueristicEval(LegalMove legalMove) {
		char[] own = Long.toBinaryString(Long.reverse(legalMove.getPlayerBoard())).toCharArray();
		char[] opponent = Long.toBinaryString(Long.reverse(legalMove.getEnemyBoard())).toCharArray();
		int ownValue = 0,oppoValue = 0;
		for(int i=0;i<64;i++){
			if(own.length>i && own[i]=='1'){
				ownValue += nodeWeights[i];
			}
			if(opponent.length>i && opponent[i]=='1'){
				oppoValue += nodeWeights[i];
			}
		}
		//aB.printBoard(legalMove.getPlayerBoard(),legalMove.getEnemyBoard(),'O');
		return (oppoValue-ownValue);
	}
	
	private int callMinPlayer(int depth, Map<Integer, LegalMove> legalMoveMap,int alpha,int beta) {
		Iterator it = legalMoveMap.entrySet().iterator();
		// find min
	   while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        LegalMove parentLegalMove = legalMoves.captureTheAdjacentPieces((LegalMove)pairs.getValue());
	        LegalMove legalMove = new LegalMove(parentLegalMove);
	        int score = minmaxPlay(depth+1,maxPlayer,legalMove,alpha,beta);
	        if (score <= alpha){
			   return alpha;
	        }
			if( score < beta ){
			   beta = score; // beta acts like min in MiniMax
			}
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	   return beta;	
	}
	
	private int callMaxPlayer(int depth, Map<Integer, LegalMove> legalMoveMap,int alpha,int beta) {
		Iterator it = legalMoveMap.entrySet().iterator();
		// find max
	   while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        LegalMove parentLegalMove = legalMoves.captureTheAdjacentPieces(((LegalMove) pairs.getValue()));
	        LegalMove legalMove = new LegalMove(parentLegalMove);
	        int score = minmaxPlay(depth+1,minPlayer,legalMove,alpha,beta);
	        if (score >= beta){
			   return beta;
			
	        }
			if( score > alpha ){
			   alpha = score; // beta acts like min in MiniMax
			   if((depth+1)==1){
				   aB.bestMove = parentLegalMove;
			   }
			}
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	   return alpha;	
	}
	
	/**
	 * 
	 * @param reversiBoard
	 * @param own
	 * @param enemy
	 * This logic has been based on the logic presented in the youtube video by CrazyLogic
	 */
	public void intializeReversiBoard(char[][] reversiBoard,Long own,Long enemy){
		String tempBinary;
		LegalMove legalMove = new LegalMove(); 
		for(int i=0;i<64;i++){
			tempBinary = "0000000000000000000000000000000000000000000000000000000000000000";
			tempBinary = tempBinary.substring(i+1)+"1"+tempBinary.substring(0,i);
			
			if(reversiBoard[i/8][i%8] == max){
				own+=convertStringToBitBoard(tempBinary);
			}else{
				enemy+=convertStringToBitBoard(tempBinary);
			}
		}
		legalMove.setPlayerBoard(own);
		legalMove.setEnemyBoard(enemy);
		aB.bestMove = legalMove; 
		aB.minmaxPlay(0, maxPlayer, legalMove, -99999, 99999);
		aB.printBoard(aB.bestMove.getPlayerBoard(),aB.bestMove.getEnemyBoard(),max);
	}
	
	public void printBoard(long own,long enemy,char player) {
		String reversiBoard[][]=new String[8][8];
		for (int i=0;i<64;i++) {
			reversiBoard[i/8][i%8]="*";
		}
		String opp = null,play=null;
		if(player == 'X'){
			opp = "O";
			play = "X";
		}else{
			opp = "X";
			play = "O";
		}
		for (int i=0;i<64;i++) {
			if (((own>>i)&1)==1) {reversiBoard[i/8][i%8]=play;}
			if (((enemy>>i)&1)==1) {reversiBoard[i/8][i%8]=opp;}
		}
		Utilities utils = new Utilities();
		utils.writeToFile(reversiBoard, 8);
	}

	private static Long convertStringToBitBoard(String tempBinary) {
		if(tempBinary.charAt(0)=='0'){//Not going to be a Negative Number
			return Long.parseLong(tempBinary,2);
		}else{
			return Long.parseLong("1"+tempBinary.substring(2),2)*2;
		}
	}
	
	public static void main(String[] args) {
		FileData fileData = new FileData();
		Utilities utils = new Utilities();
		utils.readFromFile("./input.txt", fileData);
		aB.setFileData(fileData);
		if(fileData.getPlayer().equals("X")){
			aB.maxPlayer = "X";
			aB.minPlayer = "O";
			aB.max='X';
			aB.min='O';
		}else{
			aB.maxPlayer = "O";
			aB.minPlayer = "X";
			aB.max='O';
			aB.min='X';
		}
		aB.intializeReversiBoard(fileData.getBoard(),0L,0L);
	}

	public FileData getFileData() {
		return fileData;
	}

	public void setFileData(FileData fileData) {
		this.fileData = fileData;
	}

}

