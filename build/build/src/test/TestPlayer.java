package test;

import static org.junit.Assert.*;
import core.Connect4;
import core.Player;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestPlayer {
	Connect4 game;

	@Before
	public void setUp() throws Exception {
		game = new Connect4();
		
	}

	@After
	public void tearDown() throws Exception {
		game = null;
	}

	@Test
	public void testPlayer() {
		Player player = new Player("Player", "X", game);
		assertTrue(player != null);
		assertTrue(player.getPlayerName().equals("Player"));
		assertTrue(player.getSymbol().equals("X"));
		game.addPlayer("Player 1", "$", game);
		assertTrue(game.player1 != null);
		assertTrue(game.player1.getPlayerName().equals("Player 1"));
		assertTrue(game.player1.getSymbol().equals("$"));
	}

	@Test
	public void testTakeTurn() {
		game.addPlayer("Player 1", "X", game);
		assertEquals(5, game.player1.takeTurn(0));
		game.setRows(2);
		game.setColumns(2);
		game.addPiece(0,0, game.player1);
		game.addPiece(1,0, game.player1);
		assertEquals(-1, game.player1.takeTurn(0)); // Should return -1 because column is full
		try {
			game.player1.takeTurn(-1);
			fail();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Cannot have a negative column number"); // Expected out of bounds error
		}
		try {
			game.player1.takeTurn(10);
			fail();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("There is no 10th column"); // Expected out of bounds error
		}
	}

	@Test
	public void testGetSymbol() {
		game.addPlayer("Player 1", "%%%", game);
		assertTrue(game.player1.getSymbol().equals("%%%"));
		game.addPlayer("Player 2", "12349", game);
		assertTrue(game.player2.getSymbol().equals("12349"));
		assertTrue(game.player1.getSymbol().equals("%%%")); // Additional check to see that player symbols are unique
	}

	@Test
	public void testGetPlayerName() {
		game.addPlayer("I can choose any name I want", "*", game);
		assertTrue(game.player1.getPlayerName().equals("I can choose any name I want"));
		game.addPlayer("John Smith", "99", game);
		assertTrue(game.player2.getPlayerName().equals("John Smith"));
	}

}
