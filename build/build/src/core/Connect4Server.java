package core;

import core.Player;
import core.Connect4;
import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Connect4Server Class Description: This class creates a Connect 4 
 * game server which facilitates a Connect 4 game between two players
 * across the network. 
 * @author Chris Toeller 
 * @version 1.0 April 18, 2020
 *
 */
public class Connect4Server extends Application {
  private int sessionNo = 1; // Number a session
  
  /**
   * This is the method where the program begins execution.
   */
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    TextArea taLog = new TextArea();

    // Create a scene and place it in the stage
    Scene scene = new Scene(new ScrollPane(taLog), 450, 200);
    primaryStage.setTitle("Connect 4 Server"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage

    new Thread( () -> {
      try {
        // Create a server socket
        ServerSocket serverSocket = new ServerSocket(8000);
        Platform.runLater(() -> taLog.appendText(new Date() +
          ": Server started at socket 8000\n"));
  
        // Ready to create a session for every two players
        while (true) {
          Platform.runLater(() -> taLog.appendText(new Date() +
            ": Wait for players to join session " + sessionNo + '\n'));
  
          // Connect to player 1
          Socket player1 = serverSocket.accept();
  
          Platform.runLater(() -> {
            taLog.appendText(new Date() + ": Player 1 joined session " 
              + sessionNo + '\n');
            taLog.appendText("Player 1's IP address" +
              player1.getInetAddress().getHostAddress() + '\n');
          });
  
          // Notify that the player is Player 1
          new DataOutputStream(
            player1.getOutputStream()).writeInt(1);
  
          // Connect to player 2
          Socket player2 = serverSocket.accept();
  
          Platform.runLater(() -> {
            taLog.appendText(new Date() +
              ": Player 2 joined session " + sessionNo + '\n');
            taLog.appendText("Player 2's IP address" +
              player2.getInetAddress().getHostAddress() + '\n');
          });
  
          // Notify that the player is Player 2
          new DataOutputStream(
            player2.getOutputStream()).writeInt(2);
  
          // Display this session and increment session number
          Platform.runLater(() -> 
            taLog.appendText(new Date() + 
              ": Start a thread for session " + sessionNo++ + '\n'));
  
          // Launch a new thread for this session of two players
          new Thread(new HandleASession(player1, player2)).start();
        }
      }
      catch(IOException ex) {
        ex.printStackTrace();
      }
    }).start();
  }

  // Define the thread class for handling a new session for two players
  /**
   * HandleASession Class Description: This class creates a runnable
   * task for facilitating a Connect 4 game session between two players
   * over the network.
   * @author Chris Toeller
   * @version April 18, 2020
   *
   */
  class HandleASession implements Runnable, Connect4Constants {
    private Socket player1;
    private Socket player2;
    Connect4 board;
  
    private DataInputStream fromPlayer1;
    private DataOutputStream toPlayer1;
    private DataInputStream fromPlayer2;
    private DataOutputStream toPlayer2;
  
    // Continue to play
    private boolean continueToPlay = true;
  
    /**
     * Class Constructor 
     */
    public HandleASession(Socket player1, Socket player2) {
      this.player1 = player1;
      this.player2 = player2;
      board = new Connect4();
      board.addPlayer("Player 1", "X", board);
      board.addPlayer("Player 2", "O", board);
    }
  
    /** 
     * This is the runnable method that handles the game logic and procedure 
     */
    public void run() {
      try {
        // Create data input and output streams
        DataInputStream fromPlayer1 = new DataInputStream(
          player1.getInputStream());
        DataOutputStream toPlayer1 = new DataOutputStream(
          player1.getOutputStream());
        DataInputStream fromPlayer2 = new DataInputStream(
          player2.getInputStream());
        DataOutputStream toPlayer2 = new DataOutputStream(
          player2.getOutputStream());
  
        // Write anything to notify player 1 to start
        // This is just to let player 1 know to start
        toPlayer1.writeInt(PLAYER1);
        toPlayer2.writeInt(PLAYER1);


  
        // Continuously serve the players and determine and report
        // the game status to the players
        while (true) {
          // Receive a move from player 1
        	int row = -1;
        	int column = 0;
        	while (row == -1) {
        		column = fromPlayer1.readInt();
        		row = board.player1.takeTurn(column-1);
        		if (row == -1) {
        			toPlayer1.writeInt(COLUMN_FULL);
        		}
        	}
    		toPlayer1.writeInt(CONTINUE); //Tell player1 that their entry was valid
    		sendMove(toPlayer1, row, column);
    		sendMove(toPlayer2, row, column);
          
          // Check if Player 1 wins
          if (board.isWinner() == true) {
        	  Player winner = board.getWinner();
        	  if (winner.getPlayerName().equals("Player 1")) {
        		  toPlayer1.writeInt(PLAYER1_WON);
        		  toPlayer2.writeInt(PLAYER1_WON);
        		  break;
        	  } else {
        		  toPlayer1.writeInt(PLAYER2_WON);
        		  toPlayer2.writeInt(PLAYER2_WON);
        		  break;
        	  }
          } else if (board.isFull() == true) {
    		  toPlayer1.writeInt(DRAW);
    		  toPlayer2.writeInt(DRAW);
    		  break;
          } else {
        	  toPlayer1.writeInt(CONTINUE);
        	  toPlayer2.writeInt(CONTINUE);
        	  toPlayer1.writeInt(PLAYER2);
        	  toPlayer2.writeInt(PLAYER2);
          }
          
        //Recieve move from Player 2 

        row = -1;
      	column = 0;
      	while (row == -1) {
      	column = fromPlayer2.readInt();
      	row = board.player2.takeTurn(column - 1);
      		if (row == -1) {
      			toPlayer2.writeInt(COLUMN_FULL);
      		}
      	}
  		toPlayer2.writeInt(CONTINUE); //Tell player1 that their entry was valid
  		sendMove(toPlayer1, row, column);
  		sendMove(toPlayer2, row, column);
        
        // Check if Player 1 wins
        if (board.isWinner() == true) {
      	  Player winner = board.getWinner();
      	  if (winner.getPlayerName().equals("Player 1")) {
      		  toPlayer1.writeInt(PLAYER1_WON);
      		  toPlayer2.writeInt(PLAYER1_WON);
      		  break;
      	  } else {
      		  toPlayer1.writeInt(PLAYER2_WON);
      		  toPlayer2.writeInt(PLAYER2_WON);
      		  break;
      	  }
        } else if (board.isFull() == true) {
  		  toPlayer1.writeInt(DRAW);
  		  toPlayer2.writeInt(DRAW);
  		  break;
        } else {
      	  toPlayer1.writeInt(CONTINUE);
      	  toPlayer2.writeInt(CONTINUE);
    	  toPlayer1.writeInt(PLAYER1);
    	  toPlayer2.writeInt(PLAYER1);
        }


        }
        } catch (IOException ex) {
        	ex.printStackTrace();
        }
      }
    }
  
  /**
   * This method sends a player's choice row and column to a client
   * @param out
   * @param row
   * @param column
   * @throws IOException
   */
  private void sendMove(DataOutputStream out, int row, int column)
	        throws IOException {
	      out.writeInt(row); // Send row index
	      out.writeInt(column); // Send column index
	    }
  public int getSessionNumber() {
	  return sessionNo;
  }
  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}