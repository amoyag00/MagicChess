import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import chess.Board;
import chess.Pawn;
import chess.Piece;

class PawnTest {

	@Test
	void testIsRestrictedSamePosition() {
		Pawn pawn = new Pawn();
		pawn.x=3;
		pawn.y=3;
		assertTrue(pawn.isRestricted(3,3));
	}
	
	@Test
	void testIsRestrictedCaptureRight() {
		Board board=new Board();
		Pawn pawnW = new Pawn();
		pawnW.x=2;
		pawnW.y=3;
		pawnW.setColor('w');
		pawnW.setBoard(board);
		
		Pawn pawnB=new Pawn();
		pawnB.x=3;
		pawnB.y=4;
		pawnB.setColor('b');
		pawnB.setBoard(board);
		board.getSquare(2, 3).setPiece(pawnW);
		board.getSquare(3, 4).setPiece(pawnB);
		assertTrue(!pawnW.isRestricted(3,4));
	}
	
	@Test
	void testIsRestrictedCaptureLeft() {
		Board board=new Board();
		Pawn pawnW = new Pawn();
		pawnW.x=5;
		pawnW.y=4;
		pawnW.setColor('w');
		pawnW.setBoard(board);
		
		Pawn pawnB=new Pawn();
		pawnB.x=4;
		pawnB.y=5;
		pawnB.setColor('b');
		pawnB.setBoard(board);
		
		board.getSquare(5, 4).setPiece(pawnW);
		board.getSquare(4, 5).setPiece(pawnB);
		assertTrue(!pawnW.isRestricted(4,5));
	}
	
	@Test
	void testFirstMove2Squares() {
		Board board=new Board();
		Pawn pawnW = new Pawn();
		pawnW.x=2;
		pawnW.y=2;
		pawnW.setColor('w');
		assertTrue(!pawnW.isRestricted(2,4));
	}
	
	@Test
	void testSecondMove2Squares() {
		Board board=new Board();
		Pawn pawnW = new Pawn();
		pawnW.x=2;
		pawnW.y=3;
		pawnW.setColor('w');
		assertTrue(pawnW.isRestricted(2,5));
	}
	
	@Test
	void testEat2Squares() {
		Board board=new Board();
		Pawn pawnW = new Pawn();
		pawnW.x=2;
		pawnW.y=2;
		pawnW.setColor('w');
		
		Pawn pawnB=new Pawn();
		pawnB.x=3;
		pawnB.y=4;
		pawnB.setColor('b');
		
		board.getSquare(2, 2).setPiece(pawnW);
		board.getSquare(4, 4).setPiece(pawnB);
		assertTrue(pawnW.isRestricted(4,4));
	}
	
	@Test
	void testMoveBackwards() {
		Board board=new Board();
		Pawn pawnW = new Pawn();
		pawnW.x=2;
		pawnW.y=3;
		pawnW.setColor('w');
		assertTrue(pawnW.isRestricted(2,2));
	}
	@Test
	void testMove2SquaresInX() {
		Board board=new Board();
		Pawn pawnW = new Pawn();
		pawnW.x=2;
		pawnW.y=2;
		pawnW.setColor('w');
		
		Pawn pawnB = new Pawn();
		pawnB.x=4;
		pawnB.y=2;
		pawnB.setColor('b');
		
		board.getSquare(2, 2).setPiece(pawnW);
		board.getSquare(4, 2).setPiece(pawnB);
		assertTrue(pawnW.isRestricted(4,2));
	}
	
	

}
