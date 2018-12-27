package chess;

import arduino.ArduinoController;

public class ChessController {
	private Board board;
	private String gameMode;
	private static ChessController instance;
	private ArduinoController arduinoController;
	private String color;
	private Stockfish stockfish;
	
	public ChessController() {
		this.board=new Board();
		this.board.createPieces();
		//this.arduinoController=ArduinoController.getInstance();
		this.color="w";
		this.gameMode="";
		this.stockfish=new Stockfish();
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
	
	public boolean move(int originX, int originY, int destX, int destY) {
		// 1. Check if movement is valid
		Piece piece=this.board.checkSquare(originX, originY);
		if(piece!=null) {
			if(piece.isRestricted(destX, destY)) {
				//System.out.println(piece.x+","+ piece.y);
				//System.out.println(piece instanceof Knight);
				return false;
			}
		}else {
			return false;
		}
		// 2. If its valid update the board
		if(this.board.getSquare(destX, destY).isEmpty()) {
			this.board.move(originX, originY, destX, destY);
		}else {
			// 2.1. If a piece will be captured call 
			 this.board.capture(destX, destY, color);
			 /*this.arduinoController.capturePiece(color,destX,destY,
						board.getCapturedX(color),board.getCapturedY(color));*/
			 this.board.move(originX, originY, destX, destY);
		}
		
		/* 3. Then call Arduino controller and perform the movement :*/
		//this.arduinoController.move(originX, originY, destX, destY);
		
		if(this.gameMode.equals("1jugador")) {
			this.stockfish.move(originX,originY,destX,destY);
			stockfish.calculateMove();
			move(stockfish.getOriginX(),stockfish.getOriginY(),stockfish.getDestX(),stockfish.getDestY());
		}else {
			this.changeColor();
		}
		
		return true;
	}
	
	public boolean casteling(String direction) {
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
	
	/**
	 * Changes the color according the turn.
	 */
	public void changeColor() {
		if(this.color.equals("w")) {
			this.color="b";
		}else if(this.color.equals("b")) {
			this.color="w";
		}
	}
	
	public void setColor(String color) {
		this.color=color;
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public void setGameMode(String gameMode) {
		this.gameMode=gameMode;
	}
	
	public String getGameMode() {
		return this.gameMode;
	}
}
