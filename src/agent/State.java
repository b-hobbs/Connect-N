/* Bryan Hobbs
 * CS 534 AI
 * Connect-n
 * 
 * State Class
 * States for the search space
 * Contains the move that was taken for the state and the score of the state
 */
package agent;

import game.Board;

public class State {
	int moveTaken = -1, score = 0;
	boolean hasWinner = false;
	State nextMoves[];
	
	public State(){	
	}
	
	public State(int moveTaken, Board board, int score){
		this.moveTaken = moveTaken;
		this.score = score;
		this.hasWinner = board.hasWinner();
	}
	
	/*
	 * Returns the state with a max score
	 */
	public static State max(State a, State b){
		return a.score >= b.score ? a : b;
	}
	
	/*
	 * Returns the state with a min score
	 */
	public static State min(State a, State b){
		return a.score <= b.score ? a: b;
	}
}
