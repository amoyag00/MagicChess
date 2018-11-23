import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import chess.Pawn;
import chess.Piece;

class PawnTest {

	@Test
	void testIsRestrictedSamePosition() {
		Pawn pawn = new Pawn();
		int coordinates[]= new int [2];
		coordinates[0]=3;
		coordinates[1]=3;
		assertTrue(pawn.isRestricted(3,3));
	}
	
	@Test
	void testIsRestrictedCaptureRight() {
		Pawn pawn = new Pawn();
		int coordinates[]= new int [2];
		coordinates[0]=2;
		coordinates[1]=3;
		assertTrue(!pawn.isRestricted(3,4));
	}
	
	@Test
	void testIsRestrictedCaptureLeft() {
		Pawn pawn = new Pawn();
		int coordinates[]= new int [2];
		coordinates[0]=1;
		coordinates[1]=3;
		assertTrue(!pawn.isRestricted(1,4));
	}

}
