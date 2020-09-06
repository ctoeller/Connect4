package test;

import static org.junit.Assert.*;
import core.Connect4;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestConnect4 {
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
	public void setRows() { 
		try {
			game.setRows(-100);
			assertEquals(-100, game.getRows());
			fail();
		} catch (NegativeArraySizeException e) {
			System.out.println("Cannot have negative rows"); // Expected exception for negative row input
		}
		game.setRows(0);
		assertEquals(0, game.getRows());
		game.setRows(100);
		assertEquals(100, game.getRows());
	}

	@Test
	public void setColumns() {
		try {
			game.setColumns(-100);
			assertEquals(-100, game.getColumns());
			fail();
		} catch (NegativeArraySizeException e) {
			System.out.println("Cannot have negative columns"); // Expected exception for negative column input
		}
		game.setColumns(0);
		assertEquals(0, game.getColumns());
		game.setColumns(100);
		assertEquals(100, game.getColumns());
	}
	
	@Test
	public void getRows() {
		try{
			game.setRows(-100);
			assertEquals(-100, game.getRows());
			fail();
		}
		catch (NegativeArraySizeException e) {
			System.out.println("Cannot have negative rows"); // Expected exception for negative row input
		}
		game.setRows(0);
		assertEquals(0, game.getRows());
		game.setRows(100);
		assertEquals(100, game.getRows());
	}
	
	@Test
	public void getColumns() {
		try {
			game.setColumns(-100);
			assertEquals(-100, game.getColumns());
			fail();
		} catch (NegativeArraySizeException e) {
			System.out.println("Cannot have negative columns"); // Expected exception for negative column input
		}
		game.setColumns(0);
		assertEquals(0, game.getColumns());
		game.setColumns(100);
		assertEquals(100, game.getColumns());
	}
	
	@Test
	public void getNumPieces() { // Should return the number of pieces that have been added to the board
		game.addPlayer("Player1","X",game);
		assertEquals(0, game.getNumPieces());
		game.addPiece(1,1,game.player1);
		assertEquals(1,game.getNumPieces());
		game.addPiece(1,2,game.player1);
		assertEquals(2,game.getNumPieces());
	}
	
	@Test
	public void addPiece() { 
		game.addPlayer("Player1","X",game);
		game.addPiece(2,2,game.player1); // Add piece to specified space, then check if space is empty (should return false)
		assertEquals(1,game.getNumPieces());
		assertFalse(game.spaceIsEmpty(2,2));
		game.addPiece(3,1,game.player1);
		assertEquals(2, game.getNumPieces());
		assertFalse(game.spaceIsEmpty(3,1));
		try {
			game.addPiece(22,60, game.player1); // Should throw out of bounds exception, space does not exist
			fail();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Space does not exist.");
		}
		try {
			game.addPiece(-20,30, game.player1); // Should throw out of bounds exception, space does not exist
			fail();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Space does not exist.");
		}
		
	}
	
	@Test
	public void getSize() { // various sized boards
		assertEquals(42, game.getSize());
		game.setRows(10);
		game.setColumns(10);
		assertEquals(100, game.getSize());
		try {
		game.setRows(-10);
		assertEquals(-100,game.getSize());
		fail();
		}catch (NegativeArraySizeException e) {
			System.out.println("Cannot have negative rows"); // Expected exception for negative row input
		}
		try {
			game.setColumns(-20);
			assertEquals(200, game.getSize());
			fail();
		} catch (NegativeArraySizeException ex) {
			System.out.println("Cannot have negative columns"); // Expected exception for negative column input
		}
		
	}
	
	@Test
	public void isEmpty() { // Should return true until piece is added to board
		assertTrue(game.isEmpty());
		game.addPlayer("Player1","X",game);
		game.addPiece(2,2,game.player1);
		assertFalse(game.isEmpty());
	}
	
	@Test
	public void isFull() { // Should return false until board is filled
		assertFalse(game.isFull()); // board empty, should return false
		game.setRows(2); // Resized board to a 2x2 board
		game.setColumns(2);
		game.addPlayer("Player1","X",game);
		game.addPiece(0,0, game.player1); // Fill all spaces
		game.addPiece(0,1, game.player1);
		game.addPiece(1,0, game.player1);
		game.addPiece(1,1,game.player1);
		assertTrue(game.isFull()); // Resized board to a 2x2 board with all spaces filled, so should return true
	}
	
	@Test
	public void displayBoard() { // Just a visual test
		game.addPlayer("Player 1", "@", game);
		game.displayBoard(); // Board should appear in console
		game.addPiece(5,5,game.player1);
		game.displayBoard(); // Board should appear in console with a game piece in row 5, column 5
	}
	
	@Test
	public void spaceIsEmpty() {
		assertTrue(game.spaceIsEmpty(1,1)); // Board is empty, so space should be empty
		assertTrue(game.spaceIsEmpty(2,2));
		try {
		assertTrue(game.spaceIsEmpty(42,70)); //Should throw exception, space does not exist (out of bounds)
		fail();
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Space does not exist.");
		}
		try {
			assertTrue(game.spaceIsEmpty(-1,-1)); //Should throw exception, space does not exist (out of bounds)
		fail();
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Space does not exist.");
		}
		game.addPlayer("Player1","X",game); 
		game.addPiece(1,1, game.player1);
		assertFalse(game.spaceIsEmpty(1,1)); // Piece added to board at this space, so should be occupied
	}
	
	@Test
	public void isWinner() { // Check if there is a winner for all possible winning scenarios (vertical, horizontal, and both diagonals)
		assertFalse(game.isWinner());
		game.addPlayer("Player1","#",game);
		game.addPiece(1,1, game.player1);
		game.addPiece(1,2, game.player1);
		game.addPiece(1,3, game.player1);
		game.addPiece(1,4, game.player1);
		assertTrue(game.isWinner());
		game = new Connect4();
		game.addPlayer("Player1","#",game);
		game.addPiece(1,1, game.player1);
		game.addPiece(2,2, game.player1);
		game.addPiece(3,3, game.player1);
		game.addPiece(4,4, game.player1);
		assertTrue(game.isWinner());
		game = new Connect4();
		game.addPlayer("Player1","#",game);
		game.addPiece(1,1, game.player1);
		game.addPiece(2,1, game.player1);
		game.addPiece(3,1, game.player1);
		game.addPiece(4,1, game.player1);
		assertTrue(game.isWinner());
		game = new Connect4();
		game.addPlayer("Player1","#",game);
		game.addPiece(4,1, game.player1);
		game.addPiece(3,2, game.player1);
		game.addPiece(2,3, game.player1);
		game.addPiece(1,4, game.player1);
		assertTrue(game.isWinner());
		
	}
	
	@Test
	public void getWinner() { // Testing all possible win scenarios for each player
		game.addPlayer("Player1","#",game);

		assertNull(game.getWinner());
		game.addPiece(1,1, game.player1);
		game.addPiece(1,2, game.player1);
		game.addPiece(1,3, game.player1);
		game.addPiece(1,4, game.player1);
		assertSame(game.player1, game.getWinner());
		
		game = new Connect4();
		game.addPlayer("Player1","#",game);
		game.addPiece(1,1, game.player1);
		game.addPiece(2,2, game.player1);
		game.addPiece(3,3, game.player1);
		game.addPiece(4,4, game.player1);
		assertSame(game.player1, game.getWinner());
		
		game = new Connect4();
		game.addPlayer("Player1","#",game);
		game.addPiece(1,1, game.player1);
		game.addPiece(2,1, game.player1);
		game.addPiece(3,1, game.player1);
		game.addPiece(4,1, game.player1);
		assertSame(game.player1, game.getWinner());
		
		game = new Connect4();
		game.addPlayer("Player1","#",game);
		game.addPiece(4,1, game.player1);
		game.addPiece(3,2, game.player1);
		game.addPiece(2,3, game.player1);
		game.addPiece(1,4, game.player1);
		assertSame(game.player1, game.getWinner());
		
		game = new Connect4();
		game.addPlayer("Player1","#",game);
		game.addPlayer("Player2","X", game);
		game.addPiece(1,1, game.player2);
		game.addPiece(1,2, game.player2);
		game.addPiece(1,3, game.player2);
		game.addPiece(1,4, game.player2);
		assertSame(game.player2, game.getWinner());
		game = new Connect4();
		game.addPlayer("Player1","#",game);
		game.addPlayer("Player2","X", game);
		game.addPiece(1,1, game.player2);
		game.addPiece(2,2, game.player2);
		game.addPiece(3,3, game.player2);
		game.addPiece(4,4, game.player2);
		assertSame(game.player2, game.getWinner());
		game = new Connect4();
		game.addPlayer("Player1","#",game);
		game.addPlayer("Player2","X", game);
		game.addPiece(1,1, game.player2);
		game.addPiece(2,1, game.player2);
		game.addPiece(3,1, game.player2);
		game.addPiece(4,1, game.player2);
		assertSame(game.player2, game.getWinner());
		game = new Connect4();
		game.addPlayer("Player1","#",game);
		game.addPlayer("Player2","X", game);
		game.addPiece(4,1, game.player2);
		game.addPiece(3,2, game.player2);
		game.addPiece(2,3, game.player2);
		game.addPiece(1,4, game.player2);
		assertSame(game.player2, game.getWinner());
		
		
		game = new Connect4();
		game.addPlayer("Player1","#",game);
		game.addComputerPlayer("Computer", "O", game);
		
		game.addPiece(1,1, game.computerPlayer);
		game.addPiece(1,2, game.computerPlayer);
		game.addPiece(1,3, game.computerPlayer);
		game.addPiece(1,4, game.computerPlayer);
		assertSame(game.computerPlayer, game.getWinner());
		game = new Connect4();
		game.addPlayer("Player1","#",game);
		game.addComputerPlayer("Computer", "O", game);
		game.addPiece(1,1, game.computerPlayer);
		game.addPiece(2,2, game.computerPlayer);
		game.addPiece(3,3, game.computerPlayer);
		game.addPiece(4,4, game.computerPlayer);
		assertSame(game.computerPlayer, game.getWinner());
		game = new Connect4();
		game.addPlayer("Player1","#",game);
		game.addComputerPlayer("Computer", "O", game);
		game.addPiece(1,1, game.computerPlayer);
		game.addPiece(2,1, game.computerPlayer);
		game.addPiece(3,1, game.computerPlayer);
		game.addPiece(4,1, game.computerPlayer);
		assertSame(game.computerPlayer, game.getWinner());
		game = new Connect4();
		game.addPlayer("Player1","#",game);
		game.addComputerPlayer("Computer", "O", game);
		game.addPiece(4,1, game.computerPlayer);
		game.addPiece(3,2, game.computerPlayer);
		game.addPiece(2,3, game.computerPlayer);
		game.addPiece(1,4, game.computerPlayer);
		assertSame(game.computerPlayer, game.getWinner());
	}
	
	@Test
	public void addPlayer() {
		assertNull(game.player1); // Players have not been added, so they should be null
		assertNull(game.player2);
		game.addPlayer("Player1","#",game);
		game.addPlayer("Player2","X",game);
		assertTrue(game.player1 != null); // Players have been added, so they should not be null
		assertTrue(game.player2 != null);
		game.addPlayer("Player3","O", game); // Should display message in console about not exceeding two players 
	}
	
	@Test
	public void addCompmuterPlayer() {
		assertNull(game.computerPlayer); // Computer player has not been added, should be null
		game.addComputerPlayer("computer", "$", game);
		assertTrue(game.computerPlayer != null); // Computer player has been added, should no longer be null
		game.addComputerPlayer("Computer2","!",game); // Should display message in console about only allowing for 1 computer player
	}
}

