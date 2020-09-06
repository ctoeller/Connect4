package core;
/**
 * 
 * @author Chris Toeller
 * @version 1.0 April 2, 2020
 */

/**
 * Description of class Player:
 * <p>
 * Contains all of the properties of a Connect 4 player as well as the
 * methods for performing the basic functions of a player.
 */
public class Player {

    private String playerName;
    private String pieceSymbol;
    Connect4 board;

    /**
     * Class Constructor which takes in a player's name (String) and
     * symbol (String) as parameters.
     *
     * @param String name
     * @param String symbol
     */
    public Player(String name, String symbol, Connect4 board) {
    	this.board = board;
        playerName = name;
        pieceSymbol = symbol;
    }

    /**
     * Method for checking to see if there is still space in the
     * player's chosen column.<n> If there is space, the method will add
     * the player's piece to that column and return true.<n> If not, the
     * method will return false.
     *
     * @param column
     * @return
     */
    public int takeTurn(int column) {
        int row = board.getRows() - 1;
        while (row >= 0 && board.spaceIsEmpty(row, column) == false) {
            row--;
        }
        if (row >= 0) {
            board.addPiece(row, column, this);
            return row;
        } else {
            return -1;
        }
    }

    /**
     * Gets the piece symbol of the player.
     *
     * @return String pieceSymbol
     */
    public String getSymbol() {
        return pieceSymbol;
    }

    /**
     * Gets the name of the player.
     *
     * @return String playerName
     */
    public String getPlayerName() {
        return playerName;
    }
}
