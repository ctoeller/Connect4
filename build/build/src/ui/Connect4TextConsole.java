package ui;

import core.*;
import java.util.Scanner;

/**
 *
 * @author Chris Toeller
 * @version 2.0 April 2, 2020
 */
/**
 * Description of Class Connect4TextConsole:
 * <n>
 * This class launches the Connect 4 game for users to play.<n> It extends the
 * game logic provided by class Connect4 from the core package and makes the
 * game playable in the console window.
 */
public class Connect4TextConsole {

    boolean playAgain;
    boolean computerPlayer;
    Connect4 board;

    /**
     * Main method that instantiates a new Connect4TextConsole
     * @param args
     */
//    public static void main(String[] args) throws Exception {
//
//    }
    /**
     * Class Constructor.
     */
    public Connect4TextConsole() {
        playAgain = true;
        computerPlayer = promptComputerPlayer();
        	while(playAgain == true) {
        		startGame();
        		playAgainPrompt();
        	}
    }
    /**
     * Main code for handling game operation.
     */
    public void startGame() {
    	board = new Connect4();// Creates Connect4 object which instantiates a GameBoard and 2 Players
    	board.addPlayer("Player 1", "X", board);
    	if (computerPlayer == false) {
    		board.addPlayer("Player 2", "O", board);
    	}else {
    		board.addComputerPlayer("Computer Player", "0", board);
    		System.out.println("Start game against computer.");
    	}
        int column;
        boolean validEntry = false;
        Player winner;
        board.displayBoard();

        // Play the game until there is a winner or the board is full (tie game)
        while (board.isWinner() == false || board.isFull() == false) {

            // Player 1 turn
            while (validEntry == false) {
                column = turnPrompt(1);
                int row = board.player1.takeTurn(column);
                if (row == -1) {
                	validEntry = false;
                } else {
                	validEntry = true;
                }
                if (validEntry == false) {
                    System.out.println("Column entered is full. You must choose a different column.");
                }
            }

            // Check if winner or board is full
            board.displayBoard();
            if (board.isWinner() == true) {
                winner = board.getWinner();
                displayWinner(winner);
                return;
            } else if (board.isFull() == true) {
                System.out.println("Board is full. Tie match.");
                return;
            }
            validEntry = false;

            //Player 2 turn
            while (validEntry == false) {
            	if (computerPlayer == false) {
            		column = turnPrompt(2);
            		int row = board.player2.takeTurn(column);
            		if (row == -1) {
                    	validEntry = false;
                    } else {
                    	validEntry = true;
                    }
            		if (validEntry == false) {
            			System.out.println("Column entered is full. You must choose a different column.");
            		}
            	}
            	else {
            		System.out.println("Computer Turn.");
                    int row = board.computerPlayer.takeTurn();
                    if (row == -1) {
                    	validEntry = false;
                    } else {
                    	validEntry = true;
                    }
                    if (validEntry == false) {
                        System.out.println("Computer chose a full column. Choosing again...");
                    }
            	}
            }

            // Check if winner or board is full
            board.displayBoard();
            if (board.isWinner() == true) {
                winner = board.getWinner();
                displayWinner(winner);
                return;
            } else if (board.isFull() == true) {
                System.out.println("Board is full. Tie match.");
                return;
            }
            validEntry = false;
        }

    }  
    

    /**
     * Method for prompting a player to take their turn.<n> User inputs a column
     * number which the function returns to the startGame() method.
     *
     * @param integer playerNumber
     * @return integer columnNumber
     */
    public int turnPrompt(int playerNumber) {
        Scanner userInput = new Scanner(System.in);
        int column = 0;
        while (column < 1 || column > 7) {
        	if (computerPlayer == false) {
        		System.out.println("Player " + playerNumber + " - Your Turn: Enter Column Number from 1-7");
        	} else {
        		System.out.println("Your Turn: Enter Column Number from 1-7");
        	}
            // Get user response. Handles invalid input exceptions.
            try {
                column = (int) userInput.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                column = 0;
                userInput.nextLine();
            }
        }
        return column - 1;
    }

    /**
     * This method prompts the user to choose whether or not they would like to
     * play another game.
     *
     * @return boolean playAgain
     */
    public boolean playAgainPrompt() {
        Scanner userInput = new Scanner(System.in);
        String response;
        boolean validInput = false;
        while (validInput == false) {
            System.out.println("Play another game? Enter Y/N: ");
            response = userInput.nextLine();
            if (response.equals("y") || response.equals("Y")) {
                playAgain = true;
                validInput = true;
            } else if (response.equals("n") || response.equals("N")) {
                playAgain = false;
                validInput = true;
            } else {
                validInput = false;
            }
        }
        return playAgain;

    }
    /**
     * This method prompts the user to decide whether or not they would like to play against the computer or another human player.
     * @return boolean computerPlayer
     */
    public boolean promptComputerPlayer() {
        Scanner userInput = new Scanner(System.in);
        String response;
        boolean validInput = false;
        while (validInput == false) {
            System.out.println("Begin Game. Enter ‘P’ if you want to play against another player; enter ‘C’ to play against computer.");
            response = userInput.nextLine();
            if (response.equals("p") || response.equals("P")) {
                computerPlayer = false;
                validInput = true;
            } else if (response.equals("c") || response.equals("C")) {
                computerPlayer = true;
                validInput = true;
            } else {
                validInput = false;
            }
        }
        return computerPlayer;
    }

    /**
     * This method displays the winner of the game on the screen.
     *
     * @param Player winner
     */
    public void displayWinner(Player winner) {
    	if (computerPlayer == false) {
    		System.out.println(winner.getPlayerName() + " Wins!");
    	} else {
    		if (winner.getPlayerName().equals(board.player1.getPlayerName())) {
    			System.out.println("You Win!");
    		}
    		else {
    			System.out.println(winner.getPlayerName() + " Wins!");
    		}
    	}
    }


}
