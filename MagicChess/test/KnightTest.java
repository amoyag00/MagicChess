import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import chess.Knight;

class KnightTest {

	@Test
	void test2Top1Right() {
		Knight k=new Knight();
		k.x=2;
		k.y=2;
		assertFalse(k.isRestricted(3, 4));
		assertTrue(k.isRestricted(3, 5));
		assertTrue(k.isRestricted(1, 6));
	}

}
