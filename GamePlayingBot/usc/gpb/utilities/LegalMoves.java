/**
 * 
 */
package usc.gpb.utilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author sriharsha
 *
 */
public class LegalMoves {

	/**
	 * 
	 */
	public LegalMoves() {
		super();
	}

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
	private Long North(Long bitValues){
		return bitValues << 8;
	}

	private Long South(Long bitValues){
		return bitValues >>> 8;
	}

	private Long East(Long bitValues){
		return (bitValues & 0xFEFEFEFEFEFEFEFEL) >>> 1;
	}

	private Long West(Long bitValues){
		return (bitValues & 0x7F7F7F7F7F7F7F7FL) << 1;
	}

	private Long NorthEast(Long bitValues){
		return North(East(bitValues));
	}

	private Long NorthWest(Long bitValues){
		return North(West(bitValues));
	}

	private Long SouthEast(Long bitValues){
		return South(East(bitValues));
	}

	private Long SouthWest(Long bitValues){
		return South(West(bitValues));
	}

	public static void initiateStandardChess() {
		Long own=0L,enemy=0L;
		/*char reversiBoard[][]={
				{'*','*','*','*','*','*','*','*'},
				{'*','*','*','*','*','*','*','*'},
				{'*','*','O','*','*','*','*','*'},
				{'*','*','*','O','X','*','*','*'},
				{'*','*','*','X','O','*','*','*'},
				{'*','*','*','*','*','O','*','*'},
				{'*','*','*','*','*','X','*','*'},
				{'*','*','*','*','*','*','*','*'}};*/
		char reversiBoard[][]={
				{'*','*','*','*','*','*','*','*'},
				{'*','*','*','*','*','*','*','*'},
				{'*','*','*','X','*','*','*','*'},
				{'*','*','O','O','X','*','*','*'},
				{'*','*','*','*','*','*','*','*'},
				{'*','*','*','*','*','*','*','*'},
				{'*','*','*','*','*','*','*','*'},
				{'*','*','*','*','*','*','*','*'}};
		intializeReversiBoard(reversiBoard,own,enemy);
	}

	/**
	 * 
	 * @param reversiBoard
	 * @param own
	 * @param enemy
	 * This logic has been based on the logic presented in the youtube video by CrazyLogic
	 */
	public static void intializeReversiBoard(char[][] reversiBoard,Long own,Long enemy){
		String tempBinary;
		for(int i=0;i<64;i++){
			tempBinary = "0000000000000000000000000000000000000000000000000000000000000000";
			tempBinary = tempBinary.substring(i+1)+"1"+tempBinary.substring(0,i);
			switch(reversiBoard[i/8][i%8]){
			case 'X': own+=convertStringToBitBoard(tempBinary);
			break;
			case 'O': enemy+=convertStringToBitBoard(tempBinary);
			break;
			}
		}
		printBoard(own, enemy);
		LegalMoves legalMoves = new LegalMoves();
		//Map<Integer,LegalMove> legalMovesList = legalMoves.findLegalMoves(own, enemy);
		//printLegalMoves(legalMovesList,own,enemy);
		//LegalMove legalMove = new LegalMove();
		//
	}
	
	 /*Runnable task = new Runnable(){
	        public void run() {
	            processImplementation(context, itemId);
	        }
	 };*/
	
	public static LegalMove captureTheAdjacentPieces(LegalMove legalMove) {
		Long currentMove = legalMove.getCurrBoard();
		Long originalMove = legalMove.getCurrBoard();
		Long own = legalMove.getPlayerBoard();
		Long enemy = legalMove.getEnemyBoard();
		Long parentOwn = own;
		Long empty = ~(own|enemy);
		LegalMoves legalMoves = new LegalMoves();
		/*System.out.println("Bi O!!!! "+Long.toBinaryString(own));
		System.out.println("Bi C!!!! "+Long.toBinaryString(currentMove));
		System.out.println("Bi E!!!! "+Long.toBinaryString(enemy));*/
		//Check for nodes to capture in the North Side
		//Integrate the currentMove into the agent move
		
		own |= currentMove; 
		//System.out.println(" North  Own : "+Long.toBinaryString(own));
		currentMove = legalMoves.North(currentMove);
		Long flippedPos = originalMove;
		if((currentMove & enemy)!=0L){
			boolean flag = false;
			while((currentMove & own)==0){

				flippedPos|= currentMove ;
				if((currentMove & enemy)!=0L){
					currentMove = legalMoves.North(currentMove);
				}

				if((currentMove & own & empty)!=0L){
					own|=flippedPos;
					flag = true;
				}
				
				if((currentMove & empty) !=0L){
					currentMove = originalMove;
					own = parentOwn;
					break;
				}
			}
			if(!flag){
				currentMove = originalMove;
				own = parentOwn;
				
			}else{
				parentOwn = own;
				
			}
		}else{
			currentMove = originalMove;
			own = parentOwn;
			
		}
		
		/*System.out.println(" North  Own : "+Long.toBinaryString(own));
		System.out.println(" North  Current : "+Long.toBinaryString(currentMove));
		System.out.println(" North  Enemy : "+Long.toBinaryString(enemy));*/
		
		//Check for nodes to capture in the South Side
		own |= currentMove; 
		currentMove = legalMoves.South(currentMove);
		flippedPos = originalMove;
		if((currentMove & enemy)!=0L){
			boolean flag = true;
			while((currentMove & own)==0L){
				flippedPos|= currentMove;
				if((currentMove & enemy)!=0L){
					currentMove = legalMoves.South(currentMove);
				}

				//enemy ^= currentMove;
				if((currentMove & own)!=0L){
					own|=flippedPos;
					flag = true;
				}
				if((currentMove & empty) !=0L){
					currentMove = originalMove;
					own = parentOwn;
					break;
				}
			}
			if(!flag){
				currentMove = originalMove;
				own = parentOwn;
				
			}else{
				parentOwn = own;
				
			}
		}else{
			currentMove = originalMove;
			own = parentOwn;
			
		}
		
		/*System.out.println(" South  Own : "+Long.toBinaryString(own));
		System.out.println(" south  Current : "+Long.toBinaryString(currentMove));
		System.out.println(" South  Enemy : "+Long.toBinaryString(enemy));*/
		
		//Check for nodes to capture in the East Side
		own |= currentMove; 
		currentMove = legalMoves.East(currentMove);
		flippedPos = originalMove;
		if((currentMove & enemy)!=0L){
			boolean flag = true;
			while((currentMove & own)==0L){
				flippedPos|= currentMove;
				if((currentMove & enemy)!=0L){
					currentMove = legalMoves.East(currentMove);
				}

				//enemy ^= currentMove;
				if((currentMove & own)!=0L){
					own|=flippedPos;
					flag = true;
				}
				if((currentMove & empty) !=0L){
					currentMove = originalMove;
					own = parentOwn;
					break;
				}
			}
			if(!flag){
				currentMove = originalMove;
				own = parentOwn;
				
			}else{
				parentOwn = own;
				
			}
		}else{
			currentMove = originalMove;
			own = parentOwn;
			
		}
		/*System.out.println(" East  Own : "+Long.toBinaryString(own));
		System.out.println(" East  Current : "+Long.toBinaryString(currentMove));
		System.out.println(" East  Enemy : "+Long.toBinaryString(enemy));*/
		//Check for nodes to capture in the West Side
		own |= currentMove; 
		currentMove = legalMoves.West(currentMove);
		flippedPos = originalMove;
		if((currentMove & enemy)!=0L){
			boolean flag = true;
			while((currentMove & own)==0L){
				flippedPos|= currentMove;
				if((currentMove & enemy)!=0L){
					currentMove = legalMoves.West(currentMove);
				}

				//enemy ^= currentMove;
				if((currentMove & own)!=0L){
					own|=flippedPos;
					flag = true;
				}
				if((currentMove & empty) !=0L){
					currentMove = originalMove;
					own = parentOwn;
					break;
				}
			}
			if(!flag){
				currentMove = originalMove;
				own = parentOwn;
				
			}else{
				parentOwn = own;
				
			}
		}else{
			currentMove = originalMove;
			own = parentOwn;
			
		}
		/*System.out.println(" West  Own : "+Long.toBinaryString(own));
		System.out.println(" West  Current : "+Long.toBinaryString(currentMove));
		System.out.println(" West  Enemy : "+Long.toBinaryString(enemy));*/
		
		//Check for nodes to capture in the NorthWest Side
		own |= currentMove; 
		currentMove = legalMoves.NorthWest(currentMove);
		flippedPos = originalMove;
		if((currentMove & enemy)!=0L){
			boolean flag = true;
			while((currentMove & own)==0L){
				flippedPos|= currentMove;
				if((currentMove & enemy)!=0L){
					currentMove = legalMoves.NorthWest(currentMove);
				}

				//enemy ^= currentMove;
				if((currentMove & own)!=0L){
					own|=flippedPos;
					flag = true;
				}
				
				if((currentMove & empty) !=0L){
					currentMove = originalMove;
					own = parentOwn;
					break;
				}
			}
			if(!flag){
				currentMove = originalMove;
				own = parentOwn;
				
			}else{
				parentOwn = own;
				
			}
		}else{
			currentMove = originalMove;
			own = parentOwn;
			
		}
		
		//Check for nodes to capture in the NorthEast Side
		own |= currentMove; 
		currentMove = legalMoves.NorthEast(currentMove);
		flippedPos = originalMove;
		if((currentMove & enemy)!=0L){
			boolean flag = true;
			while((currentMove & own)==0L){
				flippedPos|= currentMove;
				if((currentMove & enemy)!=0L){
					currentMove = legalMoves.NorthEast(currentMove);
				}

				//enemy ^= currentMove;
				if((currentMove & own)!=0L){
					own|=flippedPos;
					flag = true;
				}
				
				if((currentMove & empty) !=0L){
					currentMove = originalMove;
					own = parentOwn;
					break;
				}
			}
			if(!flag){
				currentMove = originalMove;
				own = parentOwn;
				
			}else{
				parentOwn = own;
				
			}
		}else{
			currentMove = originalMove;
			own = parentOwn;
			
		}
		
		//Check for nodes to capture in the SouthWest Side
		own |= currentMove; 
		currentMove = legalMoves.SouthWest(currentMove);
		flippedPos = originalMove;
		if((currentMove & enemy)!=0L){
			boolean flag = true;
			while((currentMove & own)==0L){
				flippedPos|= currentMove;
				if((currentMove & enemy)!=0L){
					currentMove = legalMoves.SouthWest(currentMove);
				}

				//enemy ^= currentMove;
				if((currentMove & own)!=0L){
					own|=flippedPos;
					flag = true;
				}
				
				if((currentMove & empty) !=0L){
					currentMove = originalMove;
					own = parentOwn;
					break;
				}
			}
			if(!flag){
				currentMove = originalMove;
				own = parentOwn;
				
			}else{
				parentOwn = own;
				
			}
		}else{
			currentMove = originalMove;
			own = parentOwn;
			
		}
		
		//Check for nodes to capture in the SouthEast Side
		own |= currentMove; 
		currentMove = legalMoves.SouthWest(currentMove);
		flippedPos = originalMove;
		if((currentMove & enemy)!=0L){
			boolean flag = true;
			while((currentMove & own)==0L){
				flippedPos|= currentMove;
				if((currentMove & enemy)!=0L){
					currentMove = legalMoves.SouthEast(currentMove);
				}

				//enemy ^= currentMove;
				if((currentMove & own)!=0L){
					own|=flippedPos;
					flag = true;
				}
				
				if((currentMove & empty) !=0L){
					currentMove = originalMove;
					own = parentOwn;
					break;
				}
			}
			if(!flag){
				currentMove = originalMove;
				own = parentOwn;
				
			}else{
				parentOwn = own;
				
			}
		}else{
			currentMove = originalMove;
			own = parentOwn;
			
		}
		
		enemy &= ~(empty | own);
		legalMove.setPlayerBoard(own);
		legalMove.setEnemyBoard(enemy);
			
		/*System.out.println("Hiiii O!!!! "+Long.toBinaryString(own));
		System.out.println("Hiiii C!!!! "+Long.toBinaryString(currentMove));
		System.out.println("Hiiii E!!!! "+Long.toBinaryString(enemy));*/
		return legalMove;
	}

	private static void printLegalMoves(Map<Integer,LegalMove> legalMovesMap,Long own, Long enemy) {
		Iterator it = legalMovesMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        //System.out.println(Long.toBinaryString(((LegalMove) pairs.getValue()).getCurrBoard()));
			System.out.println(((LegalMove) pairs.getValue()).getLegalPositionOnBoard());
			LegalMove legalMove = captureTheAdjacentPieces(((LegalMove) pairs.getValue()));
			hueristicEval(legalMove);
	        it.remove(); // avoids a ConcurrentModificationException
	    }
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
		return (ownValue-oppoValue);
	}

	private static Long convertStringToBitBoard(String tempBinary) {
		if(tempBinary.charAt(0)=='0'){//Not going to be a Negative Number
			return Long.parseLong(tempBinary,2);
		}else{
			return Long.parseLong("1"+tempBinary.substring(2),2)*2;
		}
	}

	public static void printBoard(long own,long enemy) {
		String reversiBoard[][]=new String[8][8];
		for (int i=0;i<64;i++) {
			reversiBoard[i/8][i%8]=" ";
		}
		for (int i=0;i<64;i++) {
			if (((own>>i)&1)==1) {reversiBoard[i/8][i%8]="X";}
			if (((enemy>>i)&1)==1) {reversiBoard[i/8][i%8]="O";}
		}
		for (int i=0;i<8;i++) {
			System.out.println(Arrays.toString(reversiBoard[i]));
		}
	}
	public Map<Integer, LegalMove> findLegalMoves(LegalMove legalMove){
		Long own = legalMove.getPlayerBoard();
		Long enemy = legalMove.getEnemyBoard();
		Map<Integer,LegalMove> legalMoves = new HashMap<Integer,LegalMove>();
		Long tempMask = 1L;
		Long resultN= 0L,resultS= 0L,resultE= 0L,resultW= 0L,resultNE= 0L,resultSE= 0L,resultSW= 0L,resultNW = 0L;

		Long empty = ~(own | enemy);

		Long victims = North(own) & enemy;
		if(victims!=0L){//Step avoid any unncessary checking
			for (int i=0; i<8; ++i){
				victims |= North(victims) & enemy;
			}
		}	
		resultN |= North(victims) & empty;
		if(resultN!=0L){
			int counter = 0;
			while(Long.toBinaryString(resultN).length()!=counter){
				if((resultN & tempMask) == 0L){
					tempMask = tempMask<<1;
					counter++;
					continue;
				}else{
					resultN &= tempMask;
					legalMoves.put(counter,new LegalMove(resultN,counter,own,enemy));
					tempMask = tempMask<<1;
					counter++;
				}
			}
		}

		victims = South(own) & enemy;
		if(victims!=0L){//Step avoid any unncessary checking
			for (int i=0; i<8; ++i){
				victims |= South(victims) & enemy;
			}
		}
		resultS |= South(victims) & empty;
		tempMask = 1L;
		if(resultS!=0L){
			int counter = 0;
			while(Long.toBinaryString(resultS).length()!=counter){
				if((resultS & tempMask) == 0L){
					tempMask = tempMask<<1;
					counter++;
					continue;
				}else{
					resultS &= tempMask;
					legalMoves.put(counter,new LegalMove(resultS,counter,own,enemy));
					tempMask = tempMask<<1;
					counter++;
				}
			}
		}


		victims = West(own) & enemy;
		if(victims!=0L){//Step avoid any unncessary checking
			for (int i=0; i<8; ++i){
				victims |= West(victims) & enemy;
			}
		}
		resultW |= West(victims) & empty;
		tempMask = 1L;
		if(resultW!=0L){
			int counter = 0;
			while(Long.toBinaryString(resultW).length()!=counter){
				if((resultW & tempMask) == 0L){
					tempMask = tempMask<<1;
					counter++;
					continue;
				}else{
					resultW &= tempMask;
					legalMoves.put(counter,new LegalMove(resultW,counter,own,enemy));
					tempMask = tempMask<<1;
					counter++;
				}
			}
		}

		victims = East(own) & enemy;
		if(victims!=0L){//Step avoid any unncessary checking
			for (int i=0; i<8; ++i){
				victims |= East(victims) & enemy;
			}
		}
		resultE |= East(victims) & empty;
		tempMask = 1L;
		if(resultE!=0L){
			int counter = 0;
			while(Long.toBinaryString(resultE).length()!=counter){
				if((resultE & tempMask) == 0L){
					tempMask = tempMask<<1;
					counter++;
					continue;
				}else{
					resultE &= tempMask;
					legalMoves.put(counter,new LegalMove(resultE,counter,own,enemy));
					tempMask = tempMask<<1;
					counter++;
				}
			}
		}

		victims = NorthWest(own) & enemy;
		if(victims!=0L){//Step avoid any unncessary checking
			for (int i=0; i<8; ++i){
				victims |= NorthWest(victims) & enemy;
			}
		}
		resultNW |= NorthWest(victims) & empty;
		tempMask = 1L;
		if(resultNW!=0L){
			int counter = 0;
			while(Long.toBinaryString(resultNW).length()!=counter){
				if((resultNW & tempMask) == 0L){
					tempMask = tempMask<<1;
					counter++;
					continue;
				}else{
					resultNW &= tempMask;
					legalMoves.put(counter,new LegalMove(resultNW,counter,own,enemy));
					tempMask = tempMask<<1;
					counter++;
				}
			}
		}

		victims = NorthEast(own) & enemy;
		if(victims!=0L){//Step avoid any unncessary checking
			for (int i=0; i<8; ++i){
				victims |= NorthEast(victims) & enemy;

			}
		}
		resultNE |= NorthEast(victims) & empty;
		tempMask = 1L;
		if(resultNE!=0L){
			int counter = 0;
			while(Long.toBinaryString(resultNE).length()!=counter){
				if((resultNE & tempMask) == 0L){
					tempMask = tempMask<<1;
					counter++;
					continue;
				}else{
					resultNE &= tempMask;
					legalMoves.put(counter,new LegalMove(resultNE,counter,own,enemy));
					tempMask = tempMask<<1;
					counter++;
				}
			}
		}

		victims = SouthWest(own) & enemy;
		if(victims!=0L){//Step avoid any unncessary checking
			for (int i=0; i<8; ++i){
				victims |= SouthWest(victims) & enemy;
			}
		}

		resultSW |= SouthWest(victims) & empty;
		tempMask = 1L;
		if(resultSW!=0L){
			int counter = 0;
			while(Long.toBinaryString(resultSW).length()!=counter){
				if((resultSW & tempMask) == 0L){
					tempMask = tempMask<<1;
					counter++;
					continue;
				}else{
					resultSW &= tempMask;
					legalMoves.put(counter,new LegalMove(resultSW,counter,own,enemy));
					tempMask = tempMask<<1;
					counter++;
				}
			}
		}

		victims = SouthEast(own) & enemy;
		if(victims!=0L){//Step avoid any unncessary checking
			for (int i=0; i<8; ++i){
				victims |= SouthEast(victims) & enemy;
			}
		}
		resultSE |= SouthEast(victims) & empty;
		tempMask = 1L;
		if(resultSE!=0L){
			int counter = 0;
			while(Long.toBinaryString(resultSE).length()!=counter){
				if((resultSE & tempMask) == 0L){
					tempMask = tempMask<<1;
					counter++;
					continue;
				}else{
					resultSE &= tempMask;
					legalMoves.put(counter,new LegalMove(resultSE,counter,own,enemy));
					tempMask = tempMask<<1;
					counter++;
				}
			}
		}

		/*System.out.println("North : "+Long.toBinaryString(resultN));
		System.out.println("South : "+Long.toBinaryString(resultS));
		System.out.println("East : "+Long.toBinaryString(resultE));
		System.out.println("West : "+Long.toBinaryString(resultW));
		System.out.println("NE : "+Long.toBinaryString(resultNE));
		System.out.println("NW : "+Long.toBinaryString(resultNW));
		System.out.println("SE : "+Long.toBinaryString(resultSE));
		System.out.println("SW : "+Long.toBinaryString(resultSW));*/
		//System.out.println(Long.toBinaryString(resultN | resultS | resultE | resultW | resultNE | resultNW | resultSE | resultSW));
		return legalMoves;
	}

	/**
	 * Reference : https://chessprogramming.wikispaces.com/BitScan De Bruijn
	 * Multiplication While the tribute to Frank Zappa is quite 32-bit
	 * friendly [14],Kim Walisch suggested to use the parallel prefix fill
	 * for a MS1B separation with the same De Bruijn multiplication and
	 * lookup as in his bitScanForward routine with separated LS1B, with
	 * less instructions in 64-bit mode. A log base 2 method was already
	 * devised by Eric Cole on January 8, 2006, and shaved off rounded up to
	 * one less than the next power of 2 by Mark Dickinson [15] on December
	 * 10, 2009, as published in Sean Eron Anderson's Bit Twiddling Hacks
	 * for 32-bit integers
	 */
	public int getArrayIndex(Long currentState) {
		currentState |= currentState >> 1;
		currentState |= currentState >> 2;
		currentState |= currentState >> 4;
		currentState |= currentState >> 8;
		currentState |= currentState >> 16;
		currentState |= currentState >> 32;
		currentState |= currentState >> 64;
		int idx = (INDEX64[(int) ((currentState * 0x03f79d71b4cb0a89L) >> 58)]);
		System.out.println(idx);
		return idx;
	}

	public void printAsArray(String string){
		char[] value = string.toCharArray();
		for(int i =0;i<8;i++){
			for(int j=0;j<8;j++){
				System.out.print(value[(8-i-1)*8+(8-j-1)]);
			}
			System.out.println();
		}
	}

	/*public List<Coordinate> getLegalMoves(int player) {
		List<Coordinate> legalMoves = new LinkedList<Coordinate>();
		legalMoves.addAll(movesUpLeft(player));
		legalMoves.addAll(movesUp(player));
		legalMoves.addAll(movesUpRight(player));
		legalMoves.addAll(movesLeft(player));
		legalMoves.addAll(movesRight(player));
		legalMoves.addAll(movesDownLeft(player));
		legalMoves.addAll(movesDown(player));
		legalMoves.addAll(movesDownRight(player));
		return legalMoves;
		}*/
}