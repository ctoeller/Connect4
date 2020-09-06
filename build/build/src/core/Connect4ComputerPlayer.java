package core;
import java.util.Random;
/**
 * 
 * @author Chris Toeller
 * @version 1.0 April 2, 2020
 */

/**
 * Description of Connect4ComputerPlayer:
 * This class is a subclass of the Player class. It contains methods for managing a computer player in the connect 4 game.
 *
 */
public class Connect4ComputerPlayer extends Player {
	
	int chosenCol;
	/**
	 * Class constructor which also calls the constructor of the Player class. 
	 * @param name
	 * @param symbol
	 * @param board
	 */
	public Connect4ComputerPlayer(String name, String symbol, Connect4 board){
		super(name, symbol, board);
	}
	
	/**
	 * Adds a game piece to a random column on the Connect4 game board. 
	 * @return boolean
	 */
	public int takeTurn() {
		chosenCol=-1;
        int row = board.getRows() - 1;
        chosenCol = randInt(1, board.getColumns()) -1;
        
        while (row >= 0 && board.spaceIsEmpty(row, chosenCol) == false) {
            row--;
        }
        if (row >= 0) {
            board.addPiece(row, chosenCol, this);
            
            return row;
        } else {
            return -1;
        }

    }
    public int getChosenCol() {
    	return chosenCol;
    }
	/**
	 * Generates a random integer between two integer values. 
	 * @param min
	 * @param max
	 * @return int randomNum
	 */
	public int randInt(int min, int max) {
		Random randomInt =  new Random();
		int randomNum = randomInt.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}
