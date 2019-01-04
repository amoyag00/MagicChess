package chess;

import java.util.Stack;

import arduino.ArduinoController;

public class ChessController {
	private Board board;
	private String gameMode;
	private static ChessController instance;
	private ArduinoController arduinoController;
	private String color;
	private Stockfish stockfish;
	private Stack<Movement> moves;	
	
	public ChessController() {
		this.board=Board.getInstance();
		this.board.createPieces();
		this.arduinoController=ArduinoController.getInstance();
		this.color="w";
		this.gameMode="";
		//this.stockfish=new Stockfish();
		this.moves=new Stack<Movement>();
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
	
	/**
	 * Method move used for handle the castling from the user
	 * @param type
	 * @return true if moved was made, false otherwise
	 */
	public boolean move(String type) {

		if(castelingIsRestricted(type)) {
			return false;
		}else {
			if(this.gameMode.equals("1player")) {
				moveCastling(type);
				
				if (type.equals("longW")) {
					this.stockfish.move(5,1,3,1);
				} else if (type.equals("shortW")) {
					this.stockfish.move(5,1,7,1);
				} else if (type.equals("shortB")) {
					this.stockfish.move(5,8,7,8);
				} else if (type.equals("longB")) {
					this.stockfish.move(5,8,3,8);
				}
				
	
				Movement sFishMove=stockfish.calculateMove();
				if(sFishMove.isCastling()) {
					moveCastling(sFishMove.getCastling());
				}else {
					doMovement(sFishMove.getOriginX(),sFishMove.getOriginY(),sFishMove.getDestX(),sFishMove.getDestY());
				}
			}else if(this.gameMode.equals("2player")) {
				moveCastling(type);
			}
		}
		return true;
		
	}
	
	/**
	 * Performs the castling
	 * @param type
	 * @return true if the movement was made, false otherwise
	 */
	public void moveCastling(String type) {

		if (type.equals("longW")) {
			//this.arduinoController.longCasteling("w");
			this.board.move(5,1,3,1);
			this.board.move(1,1,4,1);
		} else if (type.equals("shortW")) {
			//this.arduinoController.shortCasteling("w");
			this.board.move(5,1,7,1);
			this.board.move(8,1,6,1);
		} else if (type.equals("shortB")) {
			//this.arduinoController.shortCasteling("b");
			this.board.move(5,8,7,8);
			this.board.move(8,8,6,8);
		} else if (type.equals("longB")) {
			//this.arduinoController.longCasteling("b");
			this.board.move(5,8,3,8);
			this.board.move(1,8,4,8);
		}
		this.moves.push(new Movement(type));

		this.changeColor();
	}
	/**
	 * Checks if a movement is valid and if it is, then the movement is performed.
	 * @param originX
	 * @param originY
	 * @param destX
	 * @param destY
	 * @return true if the movement was made, false otherwise
	 */
	public boolean move(int originX, int originY, int destX, int destY) {
		// 1. Check if movement is valid
		Piece piece=this.board.checkSquare(originX, originY);
		if(piece!=null) {
			if(piece.isRestricted(destX, destY)) {
				return false;
			}
		}else {
			return false;
		}
		
		if(this.gameMode.equals("1player")) {
			doMovement(originX, originY, destX, destY);
			this.stockfish.move(originX,originY,destX,destY);

			Movement sFishMove=stockfish.calculateMove();
			if(sFishMove.isCastling()) {
				moveCastling(sFishMove.getCastling());
			}else {
				doMovement(sFishMove.getOriginX(),sFishMove.getOriginY(),sFishMove.getDestX(),sFishMove.getDestY());
			}
						
		}else if(this.gameMode.equals("2player")){
			this.doMovement(originX, originY, destX, destY);
		}
		return true;
	}
	
	
	/**
	 * Assuming that the movement is valid performs it by calling arduinoController.
	 * @param originX
	 * @param originY
	 * @param destX
	 * @param destY
	 * @return true if movement was made, false otherwise
	 */
	public boolean doMovement(int originX, int originY, int destX, int destY) {
		
		Piece piece=board.getPiece(originX, originY);
		Movement move=new Movement(originX,originY,destX,destY);
		
		if(piece instanceof King ) {
			if(!((King)piece).isMoved()){
				((King)piece).setMoved(true);
				move.setKingFirstMove(true);
				move.setPiece(piece);
			}
			
		}else if(piece instanceof Rook) {
			if(!((Rook)piece).isMoved()){
				((Rook)piece).setMoved(true);
				move.setRookFirstMove(true);
				move.setPiece(piece);
			}
			
		}
		
		// 2. Update the board and the moves stack
		if(this.board.getSquare(destX, destY).isEmpty()) {
			this.board.move(originX, originY, destX, destY);
			this.moves.push(move);
		}else {
			// 2.1. A piece will be captured
			
			 this.board.capture(destX, destY, color);
			 Movement capture=new Movement(destX,destY,board.getCapturedX(color),board.getCapturedY(color));
			 if(this.color.equals("w")) {
				 capture.setCapturedColor("b");
			 }else if(this.color.equals("b")) {
				 capture.setCapturedColor("w");
			 }
			move.setCapture(true);
			this.moves.push(capture);
			this.moves.push(move);
			this.arduinoController.capturePiece(color,destX,destY,
						board.getCapturedX(color),board.getCapturedY(color));
			this.board.move(originX, originY, destX, destY);
			 
		}
		
		
		/* 3. Then call Arduino controller and perform the movement :*/
		this.arduinoController.move(originX, originY, destX, destY);
		
		this.changeColor();
		return true;
	}
	
	/**
	 * Checks the castling restrictions
	 * @param direction
	 * @return
	 */
	public boolean castelingIsRestricted(String type) {
		boolean restricted=false;
	
		if(color.equals("w")) {
			if (type.equals("longW") && this.board.getSquare(5, 1).getPiece() instanceof King && 
					this.board.getSquare(1, 1).getPiece() instanceof Rook) {
				
				King kingW=(King)this.board.getSquare(5,1).getPiece();
				Rook rookW=(Rook)this.board.getSquare(1,1).getPiece();
				
				if(!this.board.getSquare(2,1).isEmpty() || !this.board.getSquare(3,1).isEmpty() ||
						!this.board.getSquare(4,1).isEmpty()) {
					restricted=true;
				}
				
				if(kingW.isMoved() || rookW.isMoved() ) {
					restricted=true;
				}else {
					restricted=true;
				}
				
			}else if(type.equals("shortW")&& this.board.getSquare(5, 1).getPiece() instanceof King && 
					this.board.getSquare(8, 1).getPiece() instanceof Rook) {
				
				King kingW=(King)this.board.getSquare(5,1).getPiece();
				Rook rookW=(Rook)this.board.getSquare(8,1).getPiece();
				
				if(!this.board.getSquare(6,1).isEmpty() || !this.board.getSquare(7,1).isEmpty()) {
					restricted=true;
				}
				
				if(kingW.isMoved() || rookW.isMoved() ) {
					restricted=true;
				}
				
			}else {
				restricted=true;
			}
		}else if(color.equals("b")) {
			if (type.equals("longB") && this.board.getSquare(5, 8).getPiece() instanceof King && 
					this.board.getSquare(1, 8).getPiece() instanceof Rook) {
				
				King kingB=(King)this.board.getSquare(5,8).getPiece();
				Rook rookB=(Rook)this.board.getSquare(1,8).getPiece();
				
				if(!this.board.getSquare(2,8).isEmpty() || !this.board.getSquare(3,8).isEmpty() ||
						!this.board.getSquare(4,8).isEmpty()) {
					restricted=true;
				}
				
				if(kingB.isMoved() || rookB.isMoved() ) {
					restricted=true;
				}else {
					restricted=true;
				}
				
			}else if(type.equals("shortB")&& this.board.getSquare(5, 8).getPiece() instanceof King && 
					this.board.getSquare(8, 8).getPiece() instanceof Rook) {
				
				King kingB=(King)this.board.getSquare(5,8).getPiece();
				Rook rookB=(Rook)this.board.getSquare(8,8).getPiece();
				
				if(!this.board.getSquare(6,8).isEmpty() || !this.board.getSquare(7,8).isEmpty()) {
					restricted=true;
				}
				
				if(kingB.isMoved() || rookB.isMoved() ) {
					restricted=true;
				}else {
					restricted=true;
				}
				
			}
		}
		
		
		return restricted;
		
	}
	
	/**
	 * Undoes the last movement
	 */
	public void undo() {
		if(gameMode.equals("1player")) {
			stockfish.undo();//Undoes the movement of stockfish and the player
			stockfish.undo();
			performUndo();
			performUndo();
		}else {
			performUndo();
			this.changeColor();
		}
		
	}
	
	/**
	 * Undoes the movement
	 */
	public void performUndo() {
		Movement move=this.moves.pop();
		Movement captured;
		if(move.isCastling()) {
			undoCastling(move.getCastling());
		}else {
			board.move(move.getDestX(),move.getDestY(),move.getOriginX(),move.getOriginY());
			if(move.isKingFirstMove() ) {
				((King)move.getPiece()).setMoved(false);
			}else if( move.isRookFirstMove()) {
				((Rook)move.getPiece()).setMoved(false);
			}
			
			if(move.isPromotion()) {
				board.undoPromotion(move.getOriginX(),move.getOriginY());
			}
			this.arduinoController.move(move.getDestX(),move.getDestY(),move.getOriginX(),move.getOriginY());
		
			if(move.isCapture()) {//Get the captured piece back to the board
				captured=this.moves.pop();
				
				if(captured.getCapturedColor().equals("w")) {
					board.reviveIn("w",captured.getOriginX(),captured.getOriginY());
				}else if(captured.getCapturedColor().equals("b")) {
					board.reviveIn("b",captured.getOriginX(),captured.getOriginY());
				}
				String capturedBoard;
				if(captured.getCapturedColor().equals("w")) {
					capturedBoard="b";
				}else {
					capturedBoard="w";
				}
				this.arduinoController.undoCapture(capturedBoard, captured.getDestX(),
					captured.getDestY(),captured.getOriginX(),captured.getOriginY());
			}
				
			
		}
		
	}
	
	/**
	 * Undoes a castling
	 * @param type
	 */
	public void undoCastling(String type) {
		if (type.equals("longW")) {
			this.board.move(3,1,5,1);
			this.board.move(4,1,1,1);
			this.arduinoController.undoLongCastling("w");
		} else if (type.equals("shortW")) {
			this.board.move(7,1,5,1);
			this.board.move(6,1,8,1);
			this.arduinoController.undoShortCastling("w");
		} else if (type.equals("shortB")) {
			this.board.move(7,8,5,8);
			this.board.move(6,8,8,8);
			this.arduinoController.undoShortCastling("b");
		} else if (type.equals("longB")) {
			this.board.move(3,8,5,8);
			this.board.move(4,8,1,8);
			this.arduinoController.undoLongCastling("b");
		}
		
		
	}
	
	/**
	 * Promotes a pawn
	 * @param originX
	 * @param originY
	 * @param destX
	 * @param destY
	 * @param piece
	 */
	public boolean promote(int originX,int originY, int destX, int destY, String promotedPiece) {
		boolean promoted=false;
		
		if(promotedPiece.equals("reina")) {
			promotedPiece="queen";
		}else if(promotedPiece.equals("torre")) {
			promotedPiece="rook";
		}else if(promotedPiece.equals("caballo")) {
			promotedPiece="knight";
		}else if(promotedPiece.equals("alfil")) {
			promotedPiece="bishop";
		}
		
		Piece piece=this.board.getPiece(originX, originY);
		if(piece!=null) {
			if(piece.isRestricted(destX, destY)) {
				return false;
			}
		}else {
			return false;
		}
		
		Movement move= new Movement(originX, originY, destX, destY);
		move.setIsPromotion(true);
		if(board.getSquare(destX, destY).isEmpty()) {
			board.move(originX, originY, destX, destY);
			this.board.promote(destX,destY,promotedPiece);
			this.moves.push(move);
		}else {
			 this.board.capture(destX, destY, color);
			 Movement capture=new Movement(destX,destY,board.getCapturedX(color),board.getCapturedY(color));
			 if(this.color.equals("w")) {
				 capture.setCapturedColor("b");
			 }else if(this.color.equals("b")) {
				 capture.setCapturedColor("w");
			 }
			move.setCapture(true);
			this.moves.push(capture);
			this.moves.push(move);
			this.arduinoController.capturePiece(color,destX,destY,
						board.getCapturedX(color),board.getCapturedY(color));
			this.board.move(originX, originY, destX, destY);
			 
		}
		
		
		if(this.gameMode.equals("1player")) {
			this.arduinoController.move(originX, originY, destX, destY);
			this.stockfish.promote(originX,originY,destX,destY, promotedPiece);

			Movement sFishMove=stockfish.calculateMove();
			if(sFishMove.isCastling()) {
				moveCastling(sFishMove.getCastling());
			}else {
				doMovement(sFishMove.getOriginX(),sFishMove.getOriginY(),sFishMove.getDestX(),sFishMove.getDestY());
			}
		}else if(this.gameMode.equals("2player")){
			this.arduinoController.move(originX, originY, destX, destY);
			this.changeColor();
		}
		
		return true;
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
	
	public String getColor() {
		return this.color;
	}
}
