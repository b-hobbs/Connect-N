/* Bryan Hobbs
 * CS 534 AI
 * Connect-n
 * 
 * Heuristic Class
 * Evaluate a board into a score
 * Counts the number of pieces out of n for diagonals and horizontal and vertical
 * Evaluation = myscore - oppents score
 * 1 out of n = number of occurrences * 1^4 
 * 2 out of n = number of occurrences * 2^4 
 * 3 out of n = number of occurrences * 3^4 
 * n out of n = number of occurrences * n^4 
 */
package agent;

import game.Board;

public class Heuristic {
	int[] myNumSolutions, oppenentNumSolutions;
	final int BLANK = 0, MINE = 1, OPPONENT = 2, numToWin;
	Board board;
	
	/*
	 * Constructor
	 */
	public Heuristic(int numToWin){
		this.numToWin = numToWin;
		myNumSolutions = new int[numToWin + 1];
		oppenentNumSolutions = new int[numToWin + 1];
	}
	
	/*
	 * Evaluates the score of the given board layout
	 */
	public int getScore(Board board){
		int myScore = 0, oppenentScore = 0;
		this.board = board;
		horizontalScore();
		verticalScore();
		negativeDiagonalScore();
		positiveDiagonalScore();
		
		if(myNumSolutions[numToWin] > 0 || oppenentNumSolutions[numToWin] > 0)
			board.setHasWinner(true);
		
		//calculate my score
		for(int i = 1; i < myNumSolutions.length; i++){
			if(myNumSolutions[i] > 0)
				myScore += myNumSolutions[i] * (Math.pow(i, 4));
		}
		
		//calculate opponent score
		for(int i = 1; i < oppenentNumSolutions.length; i++){
			if(oppenentNumSolutions[i] > 0)
				oppenentScore += oppenentNumSolutions[i] * (Math.pow(i, 4));
		}
		
		//resets my scores
		for(int i = 1; i < myNumSolutions.length; i++){
			myNumSolutions[i] = 0;
		}
		
		//reset oppenent scores
		for(int i = 1; i < oppenentNumSolutions.length; i++){
			oppenentNumSolutions[i] = 0;
		}
		
		return myScore - oppenentScore;
	}
	
	/*
	 * Horizontal Score
	 * Finds the count of 1 out of n, 2 out of n ... n out of n
	 * for the two players and places them in their respective arrays 
	 */
	private void horizontalScore() {
		int myCount = 0, oppenentCount = 0;

		for (int i = board.getHeight() - 1; i >= 0; i--) {
			for (int j = 0; j <= board.getWidth() - numToWin; j++) {
				for (int k = 0; k < numToWin; k++) {
					if (board.positionIsBlank(i, j + k))
						continue;
					if (board.positionIsMine(i, j + k) && oppenentCount == 0)
						myCount++;
					else if (board.positionIsOppenents(i, j + k)
							&& myCount == 0)
						oppenentCount++;
					else {
						myCount = 0;
						oppenentCount = 0;
						break;
					}
				}
				if (myCount > 0)
					myNumSolutions[myCount]++;
				if (oppenentCount > 0)
					oppenentNumSolutions[oppenentCount]++;
				myCount = 0;
				oppenentCount = 0;
			}
		}
	}

	/*
	 * Vertical Score
	 * Finds the count of 1 out of n, 2 out of n ... n out of n
	 * for the two players and places them in their respective arrays 
	 */
	private void verticalScore() {
		int myCount = 0, oppenentCount = 0;

		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = board.getHeight() - 1; j >= numToWin - 1; j--) {
				for (int k = 0; k < numToWin; k++) {
					if (board.positionIsBlank(j - k, i) && myCount == 0
							&& oppenentCount == 0) {
						j = 0;
						break;
					}

					else if (board.positionIsBlank(j - k, i))
						continue;
					if (board.positionIsMine(j - k, i) && oppenentCount == 0)
						myCount++;
					else if (board.positionIsOppenents(j - k, i)
							&& myCount == 0)
						oppenentCount++;
					else {
						myCount = 0;
						oppenentCount = 0;
						break;
					}
				}
				if (myCount > 0)
					myNumSolutions[myCount]++;
				if (oppenentCount > 0)
					oppenentNumSolutions[oppenentCount]++;
				myCount = 0;
				oppenentCount = 0;
			}
		}
	}

	/*
	 * Negative Diagonal Score
	 * Finds the count of 1 out of n, 2 out of n ... n out of n
	 * for the two players and places them in their respective arrays 
	 */
	private void negativeDiagonalScore() {
		int myCount = 0, oppenentCount = 0;

		//finds the diagonals accross the top of the board
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0, k = i; j + numToWin <= board.getHeight()
					&& k + numToWin <= board.getWidth(); j++, k++) {
				for (int n = 0; n < numToWin; n++) {
					if (board.positionIsBlank(j + n, k + n))
						continue;
					if (board.positionIsMine(j + n, k + n)
							&& oppenentCount == 0)
						myCount++;
					else if (board.positionIsOppenents(j + n, k + n)
							&& myCount == 0)
						oppenentCount++;
					else {
						myCount = 0;
						oppenentCount = 0;
						break;
					}
				}
				if (myCount > 0)
					myNumSolutions[myCount]++;
				if (oppenentCount > 0)
					oppenentNumSolutions[oppenentCount]++;
				myCount = 0;
				oppenentCount = 0;
			}
		}
		
		//finds the diagonals along the left side of the board
		for (int i = 1; i <= board.getHeight() - numToWin; i++) {
			for (int j = 0, k = i; j + numToWin <= board.getWidth()
					&& k + numToWin <= board.getHeight(); j++, k++) {
				for (int n = 0; n < numToWin; n++) {
					if (board.positionIsBlank(k + n, j + n))
						continue;
					if (board.positionIsMine(k + n, j + n)
							&& oppenentCount == 0)
						myCount++;
					else if (board.positionIsOppenents(k + n, j + n)
							&& myCount == 0)
						oppenentCount++;
					else {
						myCount = 0;
						oppenentCount = 0;
						break;
					}
				}
				if (myCount > 0)
					myNumSolutions[myCount]++;
				if (oppenentCount > 0)
					oppenentNumSolutions[oppenentCount]++;
				myCount = 0;
				oppenentCount = 0;
			}
		}
	}
	
	/*
	 * Positive Diagonal Score
	 * Finds the count of 1 out of n, 2 out of n ... n out of n
	 * for the two players and places them in their respective arrays 
	 */
	private void positiveDiagonalScore() {
		int myCount = 0, oppenentCount = 0;

		//finds the positive diagonals through the top positions of the board
		for (int i = board.getWidth() - 1; i >= 0; i--) {
			for (int j = 0, k = i; j + numToWin <= board.getHeight()
					&& k - numToWin + 1 >= 0; j++, k--) {
				for (int n = 0; n < numToWin; n++) {
					if (board.positionIsBlank(j + n, k - n))
						continue;
					if (board.positionIsMine(j + n, k - n)
							&& oppenentCount == 0)
						myCount++;
					else if (board.positionIsOppenents(j + n, k - n)
							&& myCount == 0)
						oppenentCount++;
					else {
						myCount = 0;
						oppenentCount = 0;
						break;
					}
				}
				if (myCount > 0)
					myNumSolutions[myCount]++;
				if (oppenentCount > 0)
					oppenentNumSolutions[oppenentCount]++;
				myCount = 0;
				oppenentCount = 0;
			}
		}
		
		//finds the positive diagonals along the right side of the board
		for (int i = 1; i <= board.getHeight() - numToWin; i++) {
			for (int j = board.getWidth() - 1, k = i; j - numToWin + 1>= 0
					&& k + numToWin <= board.getHeight(); j--, k++) {
				for (int n = 0; n < numToWin; n++) {
					if (board.positionIsBlank(k + n, j - n))
						continue;
					if (board.positionIsMine(k + n, j - n)
							&& oppenentCount == 0)
						myCount++;
					else if (board.positionIsOppenents(k + n, j - n)
							&& myCount == 0)
						oppenentCount++;
					else {
						myCount = 0;
						oppenentCount = 0;
						break;
					}
				}
				if (myCount > 0)
					myNumSolutions[myCount]++;
				if (oppenentCount > 0)
					oppenentNumSolutions[oppenentCount]++;
				myCount = 0;
				oppenentCount = 0;
			}
		}
	}
}
