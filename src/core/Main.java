/* Bryan Hobbs
 * CS 534 AI
 * Connect-n
 * 
 * Main class for connect-n AI
 */

package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import game.Game;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader Reader = new BufferedReader(new InputStreamReader(System.in));

        //print name of agent
        System.out.println("Hobbs");
        
        //read height, width, numberToWin, playerNumber, timeLimit
        String[] gameConfig = Reader.readLine().split(" ");
        
        int height = Integer.parseInt(gameConfig[0]);
        int width = Integer.parseInt(gameConfig[1]);
        int numberToWin = Integer.parseInt(gameConfig[2]);
        int playerNumber = Integer.parseInt(gameConfig[3]);
        int timeLimit = Integer.parseInt(gameConfig[4]);
        
        Game game = new Game(height, width, numberToWin, playerNumber, timeLimit);
        game.play();
    }   
}
