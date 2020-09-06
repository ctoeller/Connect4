package core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import java.lang.Thread;
import java.lang.Runnable;

/**
 * Connect4Client Class Description: This class creates a Connect 4 game client
 * with a GUI to play against another player across the network. The client only
 * interacts with a Connect 4 server. 
 * @author Chris Toeller 
 * @version 1.0 April 18, 2020
 *
 */
public class Connect4Client extends Application implements Connect4Constants {
	DataInputStream fromServer;
	DataOutputStream toServer;
	Socket socket;
	double[][][] boardCoord;
	public int playerNumber;
	int choice = 0;
	Thread mainThread;
	private boolean waiting = true;
	
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
	
	
	/**
	 * This is the method where the program begins execution. 
	 */
	@Override
	public void start(Stage primaryStage)  {
		displayBoard();
		connectToServer();
	}

	/**
	 * This method connects to a game server and then creates a new thread for the game logic.
	 */
	public void connectToServer() {
		try {
			socket = new Socket("localhost", 8000);
			fromServer = new DataInputStream(socket.getInputStream());
			toServer = new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		(mainThread = new Thread(new PlayGame())).start();
	}
	
	/**
	 * This method contains all of the action listeners for the UI buttons.
	 */
	public void chooseButton() {
		choice = 0;
		bt1.setOnAction(w -> {
			choice = 1;
			waiting = false;
			return;
		});
		bt2.setOnAction(w -> {
			choice = 2;
			waiting = false;
			return;
		});
		bt3.setOnAction(w -> {
			choice = 3;
			waiting = false;
			return;
		});
		bt4.setOnAction(w -> {
			choice = 4;
			waiting = false;
			return;
		});
		bt5.setOnAction(w -> {
			choice = 5;
			waiting = false;
			return;
		});
		bt6.setOnAction(w -> {
			choice = 6;
			waiting = false;
			return;
		});
		bt7.setOnAction(w -> {
			choice = 7;
			waiting = false;
			return;
		});
		
	}
	
	/**
	 * Class description: This class creates a runnable task for starting a new game. It contains all of the logic and procedures for the game client to follow.
	 * @author Chris Toeller
	 * @version 1.0 April 18, 2020
	 *
	 */
	class PlayGame implements Runnable, Connect4Constants {
		/**
		 * Class constructor.
		 */
		PlayGame(){
			
		}
		
		/**
		 * This is the runnable method which contains all of the game logic and procedures.
		 */
		public void run() {
			getPlayerNumber();
			boolean continuePlaying = true;
			while (continuePlaying ==true) {
				try {
					int playerTurn = fromServer.readInt();
					Platform.runLater(() -> {
						if (playerTurn == playerNumber) {
							Platform.runLater(() -> { 
								turnLabel.setText("Your Turn");
							});
						} else {
							if (playerNumber == 1) {
								Platform.runLater(() -> { 
									turnLabel.setText("Wait for Player 2 Turn");
								});
							} else {
								Platform.runLater(() -> { 
									turnLabel.setText("Wait for Player 1 Turn");
								});
							}
						}
					});
					if (playerTurn == playerNumber) {
						boolean validEntry = false;
						while (validEntry == false) {
							chooseButton();
							waitForChoice();
							toServer.writeInt(choice);
							validEntry = handleServerResponse();
						}
						if (playerNumber == 1 ) {
							handleMoveInfo(1);
						}else {
							handleMoveInfo(2);
						}
						//Wait to see if winner
						int response = getServerResponse();
						if (response == 1 || response == 2 || response == 3) {
							displayWinner(response);
							continuePlaying = false;
						} else if (response == CONTINUE) {
							Platform.runLater(() -> { 
								bt1.setDisable(true);
								bt2.setDisable(true);
								bt3.setDisable(true);
								bt4.setDisable(true);
								bt5.setDisable(true);
								bt6.setDisable(true);
								bt7.setDisable(true);
							});
						}
					} else {
						Platform.runLater(() -> { 
							bt1.setDisable(true);
							bt2.setDisable(true);
							bt3.setDisable(true);
							bt4.setDisable(true);
							bt5.setDisable(true);
							bt6.setDisable(true);
							bt7.setDisable(true);
						});

						if (playerNumber ==1) {
							handleMoveInfo(2);
						} else {
							handleMoveInfo(1);
						}
						//Wait to see if winner
						int response = getServerResponse();
						if (response == 1 || response == 2 || response == 3) {
							displayWinner(response);

						} else if (response == CONTINUE) {
							Platform.runLater(() -> {
								bt1.setDisable(false);
								bt2.setDisable(false);
								bt3.setDisable(false);
								bt4.setDisable(false);
								bt5.setDisable(false);
								bt6.setDisable(false);
								bt7.setDisable(false);
							});
						}
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * This method displays the winner in the client GUI.
	 * @param playerNumber
	 */
	public void displayWinner(int playerNumber) {
		Platform.runLater(() -> {
		if (playerNumber == 3) {
			turnLabel.setText("Tie Game");
		} else {
			turnLabel.setText("Player " + playerNumber + " Wins!!!");
		}
			bt1.setDisable(true);
			bt2.setDisable(true);
			bt3.setDisable(true);
			bt4.setDisable(true);
			bt5.setDisable(true);
			bt6.setDisable(true);
			bt7.setDisable(true);
		//playAgainPrompt();
		 });
	}
	
	/**
	 * This method simply receives an integer value from the server.
	 * @return int response
	 */
	public int getServerResponse() {
		int response = 0;
		try {
			response = fromServer.readInt();
		} catch (Exception e) {
			
		}
		return response;
	}
	
	/**
	 * This method receives the row and column from the server and 
	 * adds a game piece to the board.
	 * @param playerNum
	 */
	public void handleMoveInfo(int playerNum) {
		try {
			int row = fromServer.readInt();
			int column = fromServer.readInt();
			Platform.runLater(() -> { 
				addVisualPiece(playerNum, row, column - 1);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method waits for the server's response to a player's move.
	 * If the move is invalid, it will display a message in the UI.
	 * @return
	 */
	public boolean handleServerResponse() {
		try {
			int response = fromServer.readInt();
			if (response == COLUMN_FULL) {
				Platform.runLater(() -> { 
					turnLabel.setText("Column Full. Choose Another.");
				});
				return false;
			} else if (response == 4) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * This method receives the player's designated number for the game.
	 */
	public int getPlayerNumber() {
		try {
			playerNumber = fromServer.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return playerNumber;
	}
	
	/**
	 * This method pauses the game logic thread to wait for the player
	 * to choose a column.
	 */
	public void waitForChoice()  {
		try {
		waiting = true;
		while (waiting) {
			mainThread.sleep(100);
		}
		} catch (InterruptedException e ) {
			
		}	
	}

	/**
	 * This method displays the game GUI.
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
		turnLabel = new Label("Waiting for another player...");
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
	public void addVisualPiece(int playerNum, int row, int column) {
		double yInc = 73;
		
		Color fill;
		if (playerNum == 1) {
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

