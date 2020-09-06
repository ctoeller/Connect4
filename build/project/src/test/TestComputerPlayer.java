package test;

import static org.junit.Assert.*;
import core.Connect4;
import core.Player;
import core.Connect4ComputerPlayer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestComputerPlayer {
	Connect4 game;

	@Before
	public void setUp() throws Exception {
		game = new Connect4();
		game.addPlayer("Player1", "X", game);
		game.addComputerPlayer("Computer", "O", game);
	}

	@After
	public void tearDown() throws Exception {
		game = null;

	}

	@Test 
	public void Connect4ComputerPlayer() {
		assertTrue(game.computerPlayer != null); // calling game.addComputerPlayer calls the constructor of Connect4ComputerPlayer, so computerPlayer should not be null
		Connect4ComputerPlayer cp = new Connect4ComputerPlayer("ComputerPlayer", "#", game);
		assertTrue(cp != null);
	}
	
	@Test
	public void takeTurn() {
		int row = game.computerPlayer.takeTurn();
		System.out.println(row);
		assertEquals(5, row);
		assertTrue(row >= 0 && row < game.getRows()); // Empty board: row should always be index 5
		
		game = new Connect4();
		game.addPlayer("Player1", "X", game);
		game.addComputerPlayer("Computer", "O", game);
		
		game.setRows(2);
		game.setColumns(2);
		game.addPiece(0,0,game.player1);
		game.addPiece(1,0,game.player1);
		game.addPiece(0,1,game.player1);
		game.addPiece(1,1,game.player1);
		assertEquals(-1, game.computerPlayer.takeTurn()); // Should return -1 because all columns are full
		
	}
	
	
	
	@Test
	public void getChosenCol() {
		int column = game.computerPlayer.getChosenCol();
		assertTrue(column > -1 && column < game.getColumns()); // random column choice should be >= zero and less than number of columns on board
	}
}
