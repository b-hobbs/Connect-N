/* Bryan Hobbs
 * CS 534 AI
 * Connect-n
 * 
 * Game Class
 * Manages the game play of connect N between some outside player and MinimaxAgent
 */
package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import agent.MinimaxAgent;

public class Game {
	int numToWin, playerNumber, timeLimit;
	boolean myTurn;
	Board board;
	MinimaxAgent agent;
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	public Game(int height, int width, int numToWin, int playerNumber, int timeLimit){
		this.numToWin = numToWin;
		this.playerNumber = playerNumber;
		this.timeLimit = timeLimit;
		
		board = new Board(height, width);
		agent = new MinimaxAgent(board, numToWin);
	}
	
	/*
	 * Main loop for playing connect N
	 */
	public void play() throws NumberFormatException, IOException{
		myTurn = (playerNumber == 0) ? true: false;
		int move;
        while (true) {
            if (myTurn) {
               
                move = getMove();
                
                //place my move
                board.handleMove(true, move);
                
                // send move
                System.out.print(String.valueOf(move));
                System.out.flush();
            } else {
                // read move
                move = Integer.parseInt(reader.readLine().split(" ")[0]);
                
                // check for end
                if (move < 0)
                    break;
                
                //place oppenent move
                board.handleMove(false, move);
            }

            // switch turns
            myTurn = !myTurn;
        }

	}
	
	/*
	 * Returns the move chosen from minimax algorithm
	 */
	public int getMove(){
		return agent.alphaBetaMinimax();
	}
}
