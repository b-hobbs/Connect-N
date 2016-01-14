/* Bryan Hobbs
 * CS 534 AI
 * Connect-n
 * 
 * Minimax agent
 * Decides what move to make based a score heuristic and 
 * minimax with alpha-beta pruning
 */
package agent;

import game.Board;

public class MinimaxAgent {
	State state = new State();
	int depth = 6;
	int moveToTake = 0;
	Board board;
	Heuristic heuristic;
	State bestState;
	
	/*
	 * Constructor
	 */
	public MinimaxAgent(Board board, int numToWin){
		this.board = board;
		heuristic = new Heuristic(numToWin);
		if(board.getHeight() > 10 && board.getWidth() > 10)
			depth = 4;
	}
	
	/*
	 * Starts the recursive createSearchSpace
	 */
	public void createSearchSpace(){
		createSearchSpace(1, state, board, true);
	}
	
	/*
	 * Creates a tree of moves to take with their evaluated scores
	 */
	public void createSearchSpace(int level, State state, Board board, boolean myTurn){
		int[] openColumns = board.getOpenColumns();
		state.nextMoves = new State[openColumns.length];
		
		for(int i = 0; i < openColumns.length; i++){
			int move = openColumns[i];
			board.handleMove(myTurn, move);
			int score = heuristic.getScore(board);
			state.nextMoves[i] = new State(move, board, score);
			
			if(level != depth && board.hasWinner() == false)
				createSearchSpace(level + 1, state.nextMoves[i], board, !myTurn);
			
			board.undoMove(move);
			board.setHasWinner(false);
		}	
	}
	
	/*
	 * Minimax with alpha beta pruning
	 * Returns a move for the agent to take
	 */
	public int alphaBetaMinimax(){
		//create the tree
		createSearchSpace();
		
		//check if there is a winning move that we can take
		int killMove = killMove();
		if(killMove() > 0)
			return killMove;
		
		//starts minimax
		int score = maxValue(state, Integer.MIN_VALUE, Integer.MAX_VALUE);
		
		//returns the move based on the score returned from minimax
		for( int i = 0; i < state.nextMoves.length; i++)
			if(state.nextMoves[i].score == score)
				return state.nextMoves[i].moveTaken;
		
		//returns 0 if no move was found for some reason
		return moveToTake;
	}
	
	/*
	 * Minimax maxvalue finds the max part of minimax
	 */
	private int maxValue(State state, int bestMax, int bestMin){
		if(state.nextMoves == null)
			return state.score;
		
		int max = Integer.MIN_VALUE;
		int oldmax = max;
		for(int i = 0; i < state.nextMoves.length; i++){
			max = Math.max(max, minValue(state.nextMoves[i], bestMax, bestMin));
			if(max != oldmax){
				moveToTake = state.moveTaken;
				oldmax = max;
			}
			if (max >= bestMin)
				return max;
			bestMax = Math.max(bestMax, max);
		}
		
		state.score = max;
		return max;
	}
	
	/*
	 * Minimax minvalue finds the min part of minimax
	 */
	private int minValue(State state, int bestMax, int bestMin){
		if(state.nextMoves == null)
			return state.score;
		
		int min = Integer.MAX_VALUE; 
		for(int i = 0; i < state.nextMoves.length; i++){
			min = Math.min(min, maxValue(state.nextMoves[i], bestMax, bestMin));
			if(min <= bestMax)
				return min;
			bestMin = Math.min(bestMin, min);
		}
		
		state.score = min;
		return min;
	}
	
	/*
	 * Checks if there is a move that would end the game for agent
	 * Returns the move if found
	 */
	private int killMove(){
		for( int i = 0; i < state.nextMoves.length; i++)
			if(state.nextMoves[i].hasWinner == true)
				return state.nextMoves[i].moveTaken;
		
		return -1;
	}
}
