package ui;

import core.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.Property;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import java.awt.event.ActionEvent;
import java.util.concurrent.TimeUnit;
import javafx.util.Duration; 
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.animation.*;

/**
 * 
 * @author Chris Toeller
 * @version 1.0 4/11/2020
 *
 */

/**
 * Connect4GUI Class Description: This class contains all of the methods for managing the 
 * GUI for a game of Connect 4.
 *
 */
public class Connect4GUI extends Application {
	//Instance Variables
	double[][][] boardCoord;
	boolean playAgain;
	boolean computerPlayer;
	boolean serverGame;
	Player winner;
	Player turnPlayer;
	Connect4 board;
	
	//Game Board GUI Components
	BorderPane bpMain;
	BorderPane topPane;
	Pane leftPane;
	Pane rightPane;
	Pane centerPane;
	FlowPane colButtonPane;
	Label title;
	Label turnLabel;
	Button bt1;
	Button bt2;
	Button bt3;
	Button bt4;
	Button bt5;
	Button bt6;
	Button bt7;
	Rectangle r1;
	Rectangle r2;
	Rectangle r3;
	Rectangle r4;
	Rectangle r5;
	Rectangle r6;
	Rectangle r7;
	Rectangle r8;
	Rectangle r9;
	Rectangle r10;
	Rectangle r11;
	Rectangle r12;
	Rectangle r13;
	Rectangle r14;
	Rectangle r15;
	
	@Override
	public void start(Stage primaryStage)  {
		serverGame = false;
        promptGUI();
	}
	
	/**
	 * The entry point for the game after the user has decided which console to use and how many live players the game will have. 
	 */
	public void startGame()  {
		initBoard();
		
        
		bt1.setOnAction(w -> {
			handleSelection(0);
			checkIfWinner();

			if (computerPlayer == true && turnPlayer==board.computerPlayer && board.isWinner() == false && board.isFull() == false) {
				boolean validEntry = false;
				while (validEntry == false) {
					validEntry = handleSelection(1);
				}
				checkIfWinner();
			}
		});
		bt2.setOnAction(w -> {
			handleSelection(1);
			checkIfWinner();
			if (computerPlayer == true && turnPlayer==board.computerPlayer && board.isWinner() == false && board.isFull() == false) {
				boolean validEntry = false;
				while (validEntry == false) {
					validEntry = handleSelection(1);
				}
				checkIfWinner();
			}
		});
		bt3.setOnAction(w -> {
			handleSelection(2);
			checkIfWinner();
			if (computerPlayer == true && turnPlayer==board.computerPlayer && board.isWinner() == false && board.isFull() == false) {
				boolean validEntry = false;
				while (validEntry == false) {
					validEntry = handleSelection(1);
				}
				checkIfWinner();
			}
		});
		bt4.setOnAction(w -> {
			handleSelection(3);
			checkIfWinner();
			if (computerPlayer == true && turnPlayer==board.computerPlayer && board.isWinner() == false && board.isFull() == false) {
				boolean validEntry = false;
				while (validEntry == false) {
					validEntry = handleSelection(1);
				}
				checkIfWinner();
			}
		});
		bt5.setOnAction(w -> {
			handleSelection(4);
			checkIfWinner();
			if (computerPlayer == true && turnPlayer==board.computerPlayer && board.isWinner() == false && board.isFull() == false) {
				boolean validEntry = false;
				while (validEntry == false) {
					validEntry = handleSelection(1);
				}
				checkIfWinner();
			}
		});
		bt6.setOnAction(w -> {
			handleSelection(5);
			checkIfWinner();
			if (computerPlayer == true && turnPlayer==board.computerPlayer && board.isWinner() == false && board.isFull() == false) {
				boolean validEntry = false;
				while (validEntry == false) {
					validEntry = handleSelection(1);
				}
				checkIfWinner();
			}
		});
		bt7.setOnAction(w -> {
			handleSelection(6);
			checkIfWinner();
			if (computerPlayer == true && turnPlayer==board.computerPlayer && board.isWinner() == false && board.isFull() == false) {
				boolean validEntry = false;
				while (validEntry == false) {
					validEntry = handleSelection(1);
				}
				checkIfWinner();
			}
		});
	}
	
	public void initBoard() {
		board = new Connect4();
		board.addPlayer("Player 1", "X", board);
    	if (computerPlayer == false) {
    		board.addPlayer("Player 2", "O", board);
    	}else {
    		board.addComputerPlayer("Computer Player", "O", board);
    	}
        turnPlayer=board.player1;
        displayBoard();
	}
	
	/** 
	 * Checks to see if the game has a winner.
	 */
	public void checkIfWinner() {
	       if (board.isWinner() == true) {
             winner = board.getWinner();
             displayWinner(winner);
         } else if (board.isFull() == true) {
             displayWinner(null);
         }
	}
	/**
	 * Displays the winner of the game. If game is a tie, this will be displayed as well.
	 * @param winner
	 */
	public void displayWinner(Player winner) {
		if (winner == null) {
			turnLabel.setText("Tie Game");
		} else {
			turnLabel.setText(winner.getPlayerName() + " Wins!!!");
		}
		bt1.setDisable(true);
		bt2.setDisable(true);
		bt3.setDisable(true);
		bt4.setDisable(true);
		bt5.setDisable(true);
		bt6.setDisable(true);
		bt7.setDisable(true);
		playAgainPrompt();
	}
	
	/**
	 * Receives the input data from the buttons that the user pushed. If it is a computer players turn, 
	 * this method will perform the computer's move as well.
	 * @param column
	 * @return
	 */
	public boolean handleSelection(int column) {
		boolean validEntry;
		int row;
		if (turnPlayer == board.player1) {
			row = board.player1.takeTurn(column);
		} else if (turnPlayer == board.player2) {
			row = board.player2.takeTurn(column);
		} else {
			row = board.computerPlayer.takeTurn();
			column = board.computerPlayer.getChosenCol();
		}
		if (row == -1) {
			validEntry = false;
			turnLabel.setText("Column full. Try again...");
        	System.out.println("Column entered is full. You must choose a different column.");
        } else {
        	validEntry = true;
			addVisualPiece(row, column);
			switchPlayer();
			
        }
		return validEntry;
	}
	/**
	 * Alternates the active player when called and displays the next player who is up to take a turn.
	 */
	public void switchPlayer() {
		if (turnPlayer ==  board.player1) {
			if (computerPlayer == false) {
				turnPlayer = board.player2;
				turnLabel.setText("Player 2 Turn");
			} else {
				turnPlayer = board.computerPlayer;
				turnLabel.setText("Computer Turn");

			}
		} else {
			turnPlayer = board.player1;
			turnLabel.setText("Player 1 Turn");
		}
	}
	/**
	 * Opens a dialog box asking the user if they would like to use the GUI or text console.
	 */
	public void promptGUI() {
		Stage stage2 = new Stage();
		BorderPane newBP = new BorderPane();
		Label promptText = new Label("Which user interface would you like to use?");
		newBP.setPrefWidth(300);
		Button btText = new Button("Text");
		Button btGUI = new Button("GUI");
		FlowPane buttonPane = new FlowPane();
		BorderPane lowerPane = new BorderPane();
		buttonPane.getChildren().add(btText);
		buttonPane.getChildren().add(btGUI);
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.setHgap(10);
		Insets pad = new Insets(15);
		buttonPane.setMargin(btText, pad);
		lowerPane.setCenter(buttonPane);
		lowerPane.setAlignment(buttonPane, Pos.BOTTOM_CENTER);
		newBP.setTop(promptText);
		newBP.setAlignment(promptText, Pos.CENTER);
		newBP.setBottom(lowerPane);
		Scene promptWindow = new Scene(newBP);
		stage2.setScene(promptWindow);
		stage2.show();
		
		// Action listeners for Buttons
		btText.setOnAction(e -> {
			stage2.close();
			initTextGame();
			return;
		});
		btGUI.setOnAction(t -> {
			stage2.close();
			promptComputerPlayer();
			
			return;
		});
	}
	/**
	 * Opens a small window that asks the user if they would like to play against 
	 * a computer player.
	 */
	public void promptComputerPlayer() {
		Stage newStage = new Stage();
		BorderPane mainBP = new BorderPane();
		Label promptText = new Label("Would you like to play against the computer?");
		mainBP.setPrefWidth(300);
		Button btYes = new Button("Yes");
		Button btNo = new Button("No");
		FlowPane buttonPane = new FlowPane();
		BorderPane lowerPane = new BorderPane();
		buttonPane.getChildren().add(btYes);
		buttonPane.getChildren().add(btNo);
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.setHgap(10);
		Insets pad = new Insets(15);
		buttonPane.setMargin(btYes, pad);
		lowerPane.setCenter(buttonPane);
		lowerPane.setAlignment(buttonPane, Pos.BOTTOM_CENTER);
		mainBP.setTop(promptText);
		mainBP.setAlignment(promptText, Pos.CENTER);
		mainBP.setBottom(lowerPane);
		Scene promptWindow = new Scene(mainBP);
		newStage.setScene(promptWindow);
		newStage.show();
		
		// Action listeners for Buttons
		btYes.setOnAction(e -> {
			computerPlayer = true;
			startGame();
			newStage.close();
			return;
		});
		btNo.setOnAction(t -> {
			computerPlayer=false;
			startGame();
			newStage.close();
			return;
		});

	}
	/**
	 * Opens a text console version of the game if the user chooses that option.
	 */
	public void initTextGame() {
		Connect4TextConsole textUI = new Connect4TextConsole();
	}

	/**
	 * Prompts the user to decide whether or not they would like to play another game.
	 */
	public void playAgainPrompt() {
		BorderPane mainBP2 = new BorderPane();
		Stage newStage2 = new Stage();
		Label promptText2 = new Label("Would you like to play another game?");
		mainBP2.setPrefWidth(300);
		Button btYes2 = new Button("Yes");
		Button btNo2 = new Button("No");
		FlowPane buttonPane2 = new FlowPane();
		BorderPane lowerPane2 = new BorderPane();
		buttonPane2.getChildren().add(btYes2);
		buttonPane2.getChildren().add(btNo2);
		buttonPane2.setAlignment(Pos.CENTER);
		buttonPane2.setHgap(10);
		Insets pad = new Insets(15);
		buttonPane2.setMargin(btYes2, pad);
		lowerPane2.setCenter(buttonPane2);
		lowerPane2.setAlignment(buttonPane2, Pos.BOTTOM_CENTER);
		mainBP2.setTop(promptText2);
		mainBP2.setAlignment(promptText2, Pos.CENTER);
		mainBP2.setBottom(lowerPane2);
		Scene prompt = new Scene(mainBP2);
		newStage2.setScene(prompt);
		newStage2.show();
		
		// Action listeners for Buttons
		btYes2.setOnAction(e -> {
			newStage2.close();
			Stage tempStage = (Stage) bpMain.getScene().getWindow();
			tempStage.close();
			startGame();
			
			return;
		});
		btNo2.setOnAction(t -> {

			newStage2.close();
			Stage tempStage = (Stage) bpMain.getScene().getWindow();
			tempStage.close();
			return;
		});
	}

	/**
	 * Creates the main GUI window containing the game board and button components
	 */
	public void displayBoard() {
		Stage stage = new Stage();
		
		bpMain = new BorderPane();
		bpMain.setPrefWidth(800);
		bpMain.setPrefHeight(600);
		bpMain.setMaxHeight(600);
		bpMain.setMinHeight(600);
		centerPane = new Pane();
		centerPane.prefWidthProperty().bind(bpMain.prefHeightProperty().subtract(100));
		centerPane.prefHeightProperty().bind(bpMain.prefHeightProperty().subtract(100));
		centerPane.setMaxHeight(600);
		leftPane = new Pane();
		leftPane.prefWidthProperty().bind(bpMain.widthProperty().subtract(600).divide(2)); //1/8 the width of borderpane --- 100 to 800
		rightPane = new Pane();
		rightPane.prefWidthProperty().bind(bpMain.widthProperty().subtract(600).divide(2)); //1/8 width of borderpane
		colButtonPane = new FlowPane();
		colButtonPane.setPrefHeight(90);
		colButtonPane.setMaxHeight(90);
		bt1 = new Button("1");
		bt1.setPrefHeight(40);
		bt1.setPrefWidth(40);
		bt1.setStyle("-fx-font-size: 2em; ");
		bt1.setAlignment(Pos.CENTER);
		bt2 = new Button("2");
		bt2.setPrefHeight(40);
		bt2.setPrefWidth(40);
		bt2.setStyle("-fx-font-size: 2em; ");
		bt2.setAlignment(Pos.CENTER);
		bt3 = new Button("3");
		bt3.setPrefHeight(40);
		bt3.setPrefWidth(40);
		bt3.setStyle("-fx-font-size: 2em; ");
		bt3.setAlignment(Pos.CENTER);
		bt4 = new Button("4");
		bt4.setPrefHeight(40);
		bt4.setPrefWidth(40);
		bt4.setStyle("-fx-font-size: 2em; ");
		bt4.setAlignment(Pos.CENTER);
		bt5 = new Button("5");
		bt5.setPrefHeight(40);
		bt5.setPrefWidth(40);
		bt5.setStyle("-fx-font-size: 2em; ");
		bt5.setAlignment(Pos.CENTER);
		bt6 = new Button("6");
		bt6.setPrefHeight(40);
		bt6.setPrefWidth(40);
		bt6.setStyle("-fx-font-size: 2em; ");
		bt6.setAlignment(Pos.CENTER);
		bt7 = new Button("7");
		bt7.setPrefHeight(40);
		bt7.setPrefWidth(40);
		bt7.setStyle("-fx-font-size: 2em; ");
		bt7.setAlignment(Pos.CENTER);
		colButtonPane.getChildren().add(bt1);
		colButtonPane.getChildren().add(bt2);
		colButtonPane.getChildren().add(bt3);
		colButtonPane.getChildren().add(bt4);
		colButtonPane.getChildren().add(bt5);
		colButtonPane.getChildren().add(bt6);
		colButtonPane.getChildren().add(bt7);
		bt1.setAlignment(Pos.TOP_CENTER);
		bt2.setAlignment(Pos.TOP_CENTER);
		bt3.setAlignment(Pos.TOP_CENTER);
		bt4.setAlignment(Pos.TOP_CENTER);
		bt5.setAlignment(Pos.TOP_CENTER);
		bt6.setAlignment(Pos.TOP_CENTER);
		bt7.setAlignment(Pos.TOP_CENTER);
		colButtonPane.setAlignment(Pos.CENTER);
		colButtonPane.setHgap(40);
		boardCoord = new double[6][7][2];
		r1 = new Rectangle(15, 450);
		r1.setX(14);
		r1.setY(20);
		r2 = new Rectangle(15, 450);
		r2.setX(94);
		r2.setY(20);
		r3 = new Rectangle(15, 450);
		r3.setX(174);
		r3.setY(20);
		r4 = new Rectangle(15, 450);
		r4.setX(254);
		r4.setY(20);
		r5 = new Rectangle(15, 450);
		r5.setX(334);
		r5.setY(20);
		r6 = new Rectangle(15, 450);
		r6.setX(414);
		r6.setY(20);
		r7 = new Rectangle(15, 450);
		r7.setX(494);
		r7.setY(20);
		r8 = new Rectangle(15, 450);
		r8.setX(574);
		r8.setY(20);
		r9 = new Rectangle(575, 15);
		r9.setX(14);
		r9.setY(20);
		r10 = new Rectangle(575, 15);
		r10.setX(14);
		r10.setY(455);
		r11 = new Rectangle(575, 15);
		r11.setX(14);
		r11.setY(238);
		r12 = new Rectangle(575, 15);
		r12.setX(14);
		r12.setY(93);
		r13 = new Rectangle(575, 15);
		r13.setX(14);
		r13.setY(166);
		r14 = new Rectangle(575, 15);
		r14.setX(14);
		r14.setY(311);
		r15 = new Rectangle(575, 15);
		r15.setX(14);
		r15.setY(384);
		centerPane.getChildren().add(r1);
		centerPane.getChildren().add(r2);
		centerPane.getChildren().add(r3);
		centerPane.getChildren().add(r4);
		centerPane.getChildren().add(r5);
		centerPane.getChildren().add(r6);
		centerPane.getChildren().add(r7);
		centerPane.getChildren().add(r8);
		centerPane.getChildren().add(r9);
		centerPane.getChildren().add(r10);
		centerPane.getChildren().add(r11);
		centerPane.getChildren().add(r12);
		centerPane.getChildren().add(r13);
		centerPane.getChildren().add(r14);
		centerPane.getChildren().add(r15);
		topPane = new BorderPane();
		topPane.setMaxHeight(50);
		bpMain.setCenter(centerPane);
		bpMain.setLeft(leftPane);
		bpMain.setRight(rightPane);
		bpMain.setBottom(colButtonPane);
		bpMain.setTop(topPane);
		title = new Label("CONNECT FOUR");
		title.setRotate(-90);
		title.setStyle("-fx-font-size: 4em;" );
		leftPane.getChildren().add(title);
		title.setTranslateX(-105);
		title.setTranslateY(200);
		turnLabel = new Label("Player 1 Turn:");
		turnLabel.setStyle("-fx-font-size: 3em;");
		topPane.setCenter(turnLabel);
		Scene scene = new Scene(bpMain);
		stage.setScene(scene);
		stage.show();
		
		//initialize board grid matrix
		for (int i = 0; i < 6; i++) {
			for (int j =0; j < 7; j++) {
				for (int x = 0; x < 2; x++) {
					if (x == 0) {
						boardCoord[i][j][x] = 62 + j*80;
					} else {
						boardCoord[i][j][x] = 65 + i*73;
					}
				}
			}
		}

	}
	
	/**
	 * Adds a visual game piece to the GUI Board
	 * @param row
	 * @param column
	 */
	public void addVisualPiece(int row, int column) {
		double yInc = 73;
		
		Color fill;
		if (turnPlayer == board.player1) {
			fill = Color.RED;
		} else {
			fill = Color.BLUE;
		}
		Circle circle = new Circle(boardCoord[row][column][0], boardCoord[row][column][1]-(73*(row+1)), 36, fill);

		centerPane.getChildren().add(circle);
		circle.toBack();
		
		TranslateTransition translateTransition = new TranslateTransition();
		translateTransition.setDuration(Duration.millis(1000*(row+1)/6));
		translateTransition.setNode(circle);
		translateTransition.setByY(yInc * (row+1)); 
		translateTransition.setCycleCount(1);
		translateTransition.setAutoReverse(false);
		translateTransition.play();


		
	}
	
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
