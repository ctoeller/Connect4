package core;
/**
 * @author Chris Toeller
 * @version 2.0 April 2, 2020
 */

/**
 * Class which contains all of the properties and methods for managing a
 * Connect 4 game board.
 */
public class Connect4
{

    private String boardArray[][];
    public Player player1;
    public Player player2;
    public Connect4ComputerPlayer computerPlayer;
    private int rows;
    private int columns;
    private int numPieces;
    private int totalSpaces;

    /**
     * Class Constructor which defines the
     * size of the GameBoard object and creates a 2
     * dimensional array which acts as the game board and its available
     * spaces.
     */
    public Connect4() {

        setRows(6);
        setColumns(7);
        boardArray = new String[rows][columns];
    }

    /**
     * Set the number of rows on the Game Board
     *
     * @param int numRows
     */
    public void setRows(int numRows) {
        rows = numRows;
        boardArray = new String[rows][getColumns()];
    }

    /**
     * Set the number of columns on the Game Board
     *
     * @param int numCols
     */
    public void setColumns(int numCols) {
        columns = numCols;
        boardArray = new String[getRows()][columns];
    }

    /**
     * Gets the number of rows on the Game Board
     *
     * @return int rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Gets the number of columns on the Game Board
     *
     * @return int columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Gets the number of pieces on the Game Board
     *
     * @return
     */
    public int getNumPieces() {
        return numPieces;
    }

    /**
     * Adds a game piece to the board at the corresponding row and
     * column.<n> The game piece is represented by the corresponding
     * player's symbol.
     *
     * @param int row
     * @param int column
     * @param Player player
     */
    public void addPiece(int row, int column, Player player) {
        boardArray[row][column] = player.getSymbol();
        numPieces++;
    }

    /**
     * Gets the size (total number of spaces) of the game board.
     *
     * @return int size
     */
    public int getSize() {
        return rows * columns;
    }

    /**
     * Checks to see if the game board is empty.
     *
     * @return boolean
     */
    public boolean isEmpty() {
        if (getNumPieces() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks to see if the game board is full.
     *
     * @return boolean
     */
    public boolean isFull() {
        if (getNumPieces() == getSize()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks to see if a particular space on the board is unoccupied.
     *
     * @param int row
     * @param int column
     * @return boolean
     */
    public boolean spaceIsEmpty(int row, int column) {
        if (boardArray[row][column] == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks the game board to see if there is a winner.
     *
     * @return boolean
     */
    public boolean isWinner() {
        if (getWinner() == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks the game board to see if there is a winner and returns who the
     * winner is.
     *
     * @return Player winner
     */
    public Player getWinner() {
        // Check for horizontal wins
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns - 3; j++) {
                if (boardArray[i][j] != null) {
                    if (boardArray[i][j].equals(boardArray[i][j + 1])
                            && boardArray[i][j].equals(boardArray[i][j + 2])
                            && boardArray[i][j].equals(boardArray[i][j + 3])) {
                        if (boardArray[i][j].equals(player1.getSymbol())) {
                            return player1;
                        } else if (player2 != null){
                            return player2;
                        }
                        else {
                        	return computerPlayer;
                        }
                    }
                }
            }
        }
        // Check for vertical wins
        for (int i = 0; i < rows - 3; i++) {
            for (int j = 0; j < columns; j++) {
                if (boardArray[i][j] != null) {
                    if (boardArray[i][j].equals(boardArray[i + 1][j])
                            && boardArray[i][j].equals(boardArray[i + 2][j])
                            && boardArray[i][j].equals(boardArray[i + 3][j])) {
                        if (boardArray[i][j].equals(player1.getSymbol())) {
                            return player1;
                        } else if (player2 != null){
                            return player2;
                        } else {
                        	return computerPlayer;
                        }
                    }
                }
            }
        }
        // Check for diagonal wins
        for (int i = 0; i < rows - 3; i++) {
            for (int j = 0; j < columns - 3; j++) {
                if (boardArray[i][j] != null) {
                    if (boardArray[i][j].equals(boardArray[i + 1][j + 1])
                            && boardArray[i][j].equals(boardArray[i + 2][j + 2])
                            && boardArray[i][j].equals(boardArray[i + 3][j + 3])) {
                        if (boardArray[i][j].equals(player1.getSymbol())) {
                            return player1;
                        } else if (player2 != null ){
                            return player2;
                        } else {
                        	return computerPlayer;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < rows - 3; i++) {
            for (int j = 3; j < columns; j++) {
                if (boardArray[i][j] != null) {
                    if (boardArray[i][j].equals(boardArray[i + 1][j - 1])
                            && boardArray[i][j].equals(boardArray[i + 2][j - 2])
                            && boardArray[i][j].equals(boardArray[i + 3][j - 3])) {
                        if (boardArray[i][j].equals(player1.getSymbol())) {
                            return player1;
                        } else if (player2 != null){
                            return player2;
                        } else {
                        	return computerPlayer;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Draws a visual representation of the game board in the console
     * window.
     */
    public void displayBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (spaceIsEmpty(i, j)) {
                    System.out.print("| ");
                } else {
                    System.out.print("|" + boardArray[i][j]);
                }
            }
            System.out.print("|");
            System.out.println();
        }
    }
    
    /**
     * Adds a player to the game.
     * @param name
     * @param symbol
     * @param board
     * @return Player
     */
    public Player addPlayer(String name, String symbol, Connect4 board) {
    	if (player1 == null) {
    		player1 = new Player(name, symbol, board);
    		return player1;
    	}
    	else if (player2 == null) {
    		player2 = new Player(name, symbol, board);
    		return player2;
    	}
    	else {
    		System.out.println("Cannot have more than 2 players!");
    		return null;
    	}
    }
    
    /**
     * Adds a computer player to the game.
     * @param name
     * @param symbol
     * @param board
     * @return Connect4ComputerPlayer
     */
    public Connect4ComputerPlayer addComputerPlayer(String name, String symbol, Connect4 board) {
    	if (computerPlayer == null) {
    		computerPlayer = new Connect4ComputerPlayer(name, symbol, board);
    		return computerPlayer;
    	}
    	else {
    		System.out.println("There can only be 1 computer player");
    		return null;
    	}
    }
}

