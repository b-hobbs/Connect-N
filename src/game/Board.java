/* Bryan Hobbs
 * CS 534 AI
 * Connect-n
 * 
 * Board Class
 * Logical board for connect-n
 * Handles moves and updates board
 */
package game;

public class Board {
    private int width, height;
    private int board[][];
    private final char BLANK = 0, MINE = 1, OPPENENTS = 2;
    private boolean hasWinner = false;

    /*
     * Constructor
     */
    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        board = new int[height][width];
    }
    
    /*
     * Places a move on the board; places a 1 if mine, 2 if opponent
     */
    public void handleMove(boolean owner, int move) {
        for (int i = height - 1; i >= 0; i--) {
            if (board[i][move] == 0) {
                board[i][move] = owner ? MINE : OPPENENTS;
                break;
            }
        }
    }
    
    /*
     * Removes a move from the board
     */
    public void undoMove(int move) {
        for (int i = 0; i < height; i++) {
            if (board[i][move] != 0) {
                board[i][move] = 0;
                break;
            }
        }
    }
    
    /*
     * returns the number of open columns
     */
    public int getNumberOfOpenColumns(){
        int count = 0;
        
        for(int i = 0; i < getWidth(); i++){
            if(positionIsBlank(0, i))
                count++;
        }
        
        return count;
    }
    
    /*
     * Returns an array with available moves
     */
    public int[] getOpenColumns(){
        int openColumns[] = new int[getNumberOfOpenColumns()];
        
        for(int i = 0, j = 0; i < getWidth(); i++){
            if(positionIsBlank(0, i)){
                openColumns[j] = i;
                j++;
            }
        }
        return openColumns;
    }
    
    /*
     * returns the piece at row,col
     */
    public int pieceAt(int row, int col){
        return board[row][col];
    }
    
    /*
     * Removes a move from the board
     */
    public boolean positionIsBlank(int row, int col){
        return board[row][col] == BLANK;
    }
    
    /*
     * Returns true if move is mine(MINE == 1)
     */
    public boolean positionIsMine(int row, int col){
        return board[row][col] == MINE;
    }
    
    /*
     * Returns true if piece is opponents(OPPENENTS == 2)  
     */
    public boolean positionIsOppenents(int row, int col){
        return board[row][col] == OPPENENTS;
    }
    
    /*
     * returns width of board
     */
    public int getWidth() {
        return width;
    }

    /*
     * Returns height of board
     */
    public int getHeight() {
        return height;
    }
    
    /*
     * Returns true if board has a winner
     */
    public boolean hasWinner() {
        return hasWinner;
    }

    /*
     * Declare a winner for the board composition
     */
    public void setHasWinner(boolean hasWinner) {
        this.hasWinner = hasWinner;
    }
    
    /*
     * Creates another copy of a board
     */
    public int[][] clone(){
        int[][] newBoard = new int[height][width];
        
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                newBoard[i][j] = board[i][j];
            }
        }
        return newBoard;
    }
    
    
}
