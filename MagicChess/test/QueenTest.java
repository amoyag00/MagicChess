

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import chess.Board;
import chess.Queen;

class QueenTest {

	@Test
	void testDiagonal_Der_Arr() {
		Queen q=new Queen();
		q.x=2;
		q.y=2;
		
		assertTrue(q.isRestricted(3, 4));
		assertFalse(q.isRestricted(4, 4));

	}

	@Test
	void testDiagonal_Der_Abj() {
		Queen q=new Queen();
		q.x=2;
		q.y=2;
		
		assertTrue(q.isRestricted(4, 1));
		assertFalse(q.isRestricted(3, 1));

	}
	
	@Test
	void testDiagonal_Izq_Arr() {
		Queen q=new Queen();
		q.x=2;
		q.y=2;
		
		assertTrue(q.isRestricted(1, 4));
		assertFalse(q.isRestricted(1, 3));

	}
	
	@Test
	void testDiagonal_Izq_Abj() {
		Queen q=new Queen();
		q.x=4;
		q.y=4;
		
		assertTrue(q.isRestricted(3, 2));
		assertFalse(q.isRestricted(2, 2));

	}
	
	@Test
	void testDiagonal_Der_Arr_2() {
		Board t = Board.getInstance();
		
		Queen q=new Queen();
		
		q.x=4;
		q.y=4;
		q.color='w';
		
		Queen c=new Queen();
		
		c.x=7;
		c.y=7;
		c.color='w';
		
		t.getSquare(3, 3).setPiece(q);
		t.getSquare(7, 7).setPiece(c);
		

		
		assertTrue(q.isRestricted(7, 7));
		assertTrue(q.isRestricted(8, 8));
		assertFalse(q.isRestricted(6, 6));
	}
	
	@Test
	void testDiagonal_Der_Abj_2() {
		Board t = Board.getInstance();
		
		Queen q=new Queen();
		
		q.x=4;
		q.y=4;
		q.color='w';
		
		Queen c=new Queen();
		
		c.x=6;
		c.y=2;
		c.color='w';
		
		t.getSquare(4, 4).setPiece(q);
		t.getSquare(6, 2).setPiece(c);
		
	
		assertTrue(q.isRestricted(7, 1));
		assertTrue(q.isRestricted(6, 2));
		assertFalse(q.isRestricted(5, 3));
	}
	
	@Test
	void testDiagonal_Izq_Arr_2() {
		Board t = Board.getInstance();
		
		Queen q=new Queen();
		
		q.x=4;
		q.y=4;
		q.color='w';
		
		Queen c=new Queen();
		
		c.x=2;
		c.y=6;
		c.color='w';
		
		t.getSquare(4, 4).setPiece(q);
		t.getSquare(2, 6).setPiece(c);
		

		assertTrue(q.isRestricted(2, 6));
		assertTrue(q.isRestricted(1, 7));
		assertFalse(q.isRestricted(3, 5));
	}
	
	@Test
	void testDiagonal_Izq_Abj_2() {
		Board t = Board.getInstance();
		
		Queen q=new Queen();
		
		q.x=4;
		q.y=4;
		q.color='w';
		
		Queen c=new Queen();
		
		c.x=2;
		c.y=2;
		c.color='w';
		
		t.getSquare(4, 4).setPiece(q);
		t.getSquare(2, 2).setPiece(c);

		
		assertTrue(q.isRestricted(2, 2));
		assertTrue(q.isRestricted(1, 1));
		assertFalse(q.isRestricted(3, 3));
	}
	
}
