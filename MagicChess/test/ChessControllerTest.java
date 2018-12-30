import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import chess.Bishop;
import chess.ChessController;
import chess.Pawn;
import chess.Queen;
import chess.Square;

class ChessControllerTest {

	@Test
	void testMoveKnight() {
		ChessController controller=new ChessController();
		controller.setGameMode("2player");
		assertTrue(controller.move(2, 1, 3, 3));
		assertFalse(controller.move(3, 3, 5, 6));
		assertTrue(controller.move(3, 3, 5, 4));
		assertTrue(controller.move(5, 4, 3, 3));
		assertTrue(controller.move(3, 3, 2,1));
	}
	
	@Test
	void eatPawBishop() {
		ChessController controller=new ChessController();
		controller.setGameMode("2player");
		assertTrue(controller.move(2, 1, 3, 3));
		assertTrue(controller.move(3, 3, 2, 5));
		controller.setColor("w");
		assertTrue(controller.move(2, 5, 1, 7)); //Capture pawn
		Square [][]capturedBlack=controller.getBoard().getCaptured("b");
		assertTrue(capturedBlack[0][7].getPiece() instanceof Pawn);
		
		controller.setColor("w");
		assertTrue(controller.move(1, 7, 3, 8));//Capture Bishop
		capturedBlack=controller.getBoard().getCaptured("b");

		assertTrue(capturedBlack[1][7].getPiece() instanceof Bishop);
		
		controller.setColor("w");
		assertTrue(controller.move(3, 8, 5, 7));//Capture Bishop
		capturedBlack=controller.getBoard().getCaptured("b");
		
		assertTrue(capturedBlack[0][6].getPiece() instanceof Pawn);

	}

}
