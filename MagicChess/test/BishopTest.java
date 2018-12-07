import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import chess.Bishop;

class BishopTest {

	@Test
	void testDiagonal_Der_Arr() {
		Bishop p=new Bishop();
		p.x=2;
		p.y=2;
		
		assertTrue(p.isRestricted(3, 4));
	}

	@Test
	void testDiagonal_Der_Abj() {
		Bishop p=new Bishop();
		p.x=2;
		p.y=2;
		
		assertTrue(p.isRestricted(4, 1));
	}
	
	@Test
	void testDiagonal_Izq_Arr() {
		Bishop p=new Bishop();
		p.x=2;
		p.y=2;
		
		assertTrue(p.isRestricted(1, 4));
	}
	
	@Test
	void testDiagonal_Izq_Abj() {
		Bishop p=new Bishop();
		p.x=4;
		p.y=4;
		
		assertTrue(p.isRestricted(3, 2));
	}
}
