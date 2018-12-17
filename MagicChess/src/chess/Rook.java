package chess;

public class Rook extends Piece{

	Board board;
	boolean moved = false;
	
	

	public boolean isRestricted(int newX, int newY) {
		
		boolean restrict = false;
		
		if(this.x != newX && this.y != newY) {
			restrict = true;
		}
		else if(this.x != newX && this.y == newY) {
			if( newX > this.x) {
				for(int i = this.x; i < newX; i++) {
					if(board.checkSquare(i, this.y) instanceof Piece) {
						restrict = true;
						break;
					}
				}
			}
			else {
				for(int i = this.x; i > newX; i--) {
					if(board.checkSquare(i, this.y) instanceof Piece) {
						restrict = true;
						break;
					}
				}
			}
		}
		else if(this.x == newX && this.y != newY) {
			if( newY > this.y) {
				for(int i = this.y; i < newY; i++) {
					if(board.checkSquare(this.x, i) instanceof Piece) {
						restrict = true;
						break;
					}
				}
			}
			else {
				for(int i = this.y; i > newY; i--) {
					if(board.checkSquare(this.x, i) instanceof Piece) {
						restrict = true;
						break;
					}
				}
			}
		}
		
		if(!restrict && board.checkSquare(newX, newY).getColor() == this.getColor()) {
			restrict = true;
		}
		
		return restrict;
	}
	
	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}
}
