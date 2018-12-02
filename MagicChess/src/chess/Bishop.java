package chess;

public class Bishop extends Piece {

	Board board;
	
	public boolean isRestricted(int newX, int newY) {
		
		boolean restrict = false;
		
		if(this.x == newX || this.y == newY) {
			restrict = true;
		}
		else if(this.x < newX && this.y < newY) {
			for(int i = this.x + 1, j = this.y + 1; (i < newX && j < newY);
					i++, j++) {
				if(board.checkSquare(i, j) instanceof Piece) {
					restrict = true;
					break;
				}
			}
		}
		else if(this.x > newX && this.y < newY) {
			for(int i = this.x - 1, j = this.y + 1; (i > newX && j < newY);
					i--, j++) {
				if(board.checkSquare(i, j) instanceof Piece) {
					restrict = true;
					break;
				}
			}
		}
		else if(this.x < newX && this.y > newY) {
			for(int i = this.x + 1, j = this.y - 1; (i < newX && j > newY);
					i++, j--) {
				if(board.checkSquare(i, j) instanceof Piece) {
					restrict = true;
					break;
				}
			}
		}
		else if(this.x > newX && this.y > newY) {
			for(int i = this.x - 1, j = this.y - 1; (i > newX && j > newY);
					i--, j--) {
				if(board.checkSquare(i, j) instanceof Piece) {
					restrict = true;
					break;
				}
			}
		}
		
		if(!restrict && board.checkSquare(newX, newY).getColor() == this.getColor()) {
			restrict = true;
		}
		
		return restrict;
	}
}
