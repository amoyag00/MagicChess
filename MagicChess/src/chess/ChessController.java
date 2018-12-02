package chess;

import arduino.ArduinoController;

public class ChessController {
	private Board board;
	
	private static ChessController instance;
	private ArduinoController arduinoController;
	
	public ChessController() {
		this.board=new Board();
		this.board.createPieces();
		this.arduinoController=ArduinoController.getInstance();
	}
	
	/**
	 * Singleton pattern
	 * @return a unique instance
	 */
	public static ChessController getInstance() {
		if(instance==null) {
			instance=new ChessController();
		}
		
		return instance;
	}
	
	public void move(int originX, int originY, int destX, int destY) {
		//TODO
		// 1. Check if movement is valid
		// 2. If its valid update the board
		/* 3. Then call Arduino controller and perform the movement :*/
		this.arduinoController.move(originX, originY, destX, destY);
		/* 3.1. If a piece will be captured call 
		this.arduinoController.capturePiece(color,destX,destY,
		board.getCapturedX(color),board.getCapturedY(color) */
		// 3.2. Calculate the next Captured coordinates: board.nextCapturedCoord(color);
	}
}
