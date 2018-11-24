package chess;

import arduino.ArduinoController;

public class ChessController {
	private Board board;
	
	private static ChessController instance;
	private ArduinoController arduinoController;
	
	public ChessController() {
		this.board=new Board();
		this.board.createPieces();
	}
	
	public static ChessController getInstance() {
		if(instance==null) {
			instance=new ChessController();
		}
		
		return instance;
	}
	
	public void move(int originX, int originY, int destX, int destY) {
		// 1. Check if movement is valid
		// 2. If its valid update the board
		/* 3. Then call Arduino controller and perform the movement :
		this.arduinoController.move(originX, originY, destX, destY) */
	}
}
