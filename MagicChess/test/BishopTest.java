import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import chess.Bishop;

class BishopTest {

	@Test
	void testDiagonal() {
		Bishop p=new Bishop();
		p.x=2;
		p.y=2;
		
		assertTrue(p.isRestricted(3, 4));
	}

}
