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
	
	public boolean casteling(String direction, String color) {
		boolean restricted=false;
		
		if(color.equals("w")) {
			if (direction.equals("left") && this.board.getSquare(5, 1).getPiece() instanceof King && 
					this.board.getSquare(1, 1).getPiece() instanceof Rook) {
				
				King kingW=(King)this.board.getSquare(5,1).getPiece();
				Rook rookW=(Rook)this.board.getSquare(1,1).getPiece();
				
				if(!this.board.getSquare(2,1).isEmpty() || !this.board.getSquare(3,1).isEmpty() ||
						!this.board.getSquare(4,1).isEmpty()) {
					restricted=true;
				}
				
				if(kingW.isMoved() || rookW.isMoved() ) {
					restricted=true;
				}
				
			}else if(direction.equals("right")&& this.board.getSquare(5, 1).getPiece() instanceof King && 
					this.board.getSquare(8, 1).getPiece() instanceof Rook) {
				
				King kingW=(King)this.board.getSquare(5,1).getPiece();
				Rook rookW=(Rook)this.board.getSquare(8,1).getPiece();
				
				if(!this.board.getSquare(6,1).isEmpty() || !this.board.getSquare(7,1).isEmpty()) {
					restricted=true;
				}
				
				if(kingW.isMoved() || rookW.isMoved() ) {
					restricted=true;
				}
				
			}
		}else if(color.equals("b")) {
			if (direction.equals("left") && this.board.getSquare(5, 8).getPiece() instanceof King && 
					this.board.getSquare(1, 8).getPiece() instanceof Rook) {
				
				King kingB=(King)this.board.getSquare(5,8).getPiece();
				Rook rookB=(Rook)this.board.getSquare(1,8).getPiece();
				
				if(!this.board.getSquare(2,8).isEmpty() || !this.board.getSquare(3,8).isEmpty() ||
						!this.board.getSquare(4,8).isEmpty()) {
					restricted=true;
				}
				
				if(kingB.isMoved() || rookB.isMoved() ) {
					restricted=true;
				}
				
			}else if(direction.equals("right")&& this.board.getSquare(5, 8).getPiece() instanceof King && 
					this.board.getSquare(8, 8).getPiece() instanceof Rook) {
				
				King kingB=(King)this.board.getSquare(5,8).getPiece();
				Rook rookB=(Rook)this.board.getSquare(8,8).getPiece();
				
				if(!this.board.getSquare(6,8).isEmpty() || !this.board.getSquare(7,8).isEmpty()) {
					restricted=true;
				}
				
				if(kingB.isMoved() || rookB.isMoved() ) {
					restricted=true;
				}
				
			}
		}
		
		return restricted;
		
	}
}
