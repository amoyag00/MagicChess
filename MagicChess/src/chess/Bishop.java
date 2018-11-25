package chess;

public class Bishop extends Piece {

	Board board;
	
	public boolean isRestricted(int newX, int newY) {
		
		boolean restrict = false;
		
		if(this.coordinates[0] == newX || this.coordinates[1] == newY) {
			restrict = true;
		}
		else if(this.coordinates[0] < newX && this.coordinates[1] < newY) {
			for(int i = this.coordinates[0], j = this.coordinates[1]; (i <= newX && j <= newY);
					i++, j++) {
				if(board.checkSquare(i, j) instanceof Piece) {
					restrict = true;
					break;
				}
			}
		}
		else if(this.coordinates[0] > newX && this.coordinates[1] < newY) {
			for(int i = this.coordinates[0], j = this.coordinates[1]; (i >= newX && j <= newY);
					i--, j++) {
				if(board.checkSquare(i, j) instanceof Piece) {
					restrict = true;
					break;
				}
			}
		}
		else if(this.coordinates[0] < newX && this.coordinates[1] > newY) {
			for(int i = this.coordinates[0], j = this.coordinates[1]; (i <= newX && j >= newY);
					i++, j--) {
				if(board.checkSquare(i, j) instanceof Piece) {
					restrict = true;
					break;
				}
			}
		}
		else if(this.coordinates[0] > newX && this.coordinates[1] > newY) {
			for(int i = this.coordinates[0], j = this.coordinates[1]; (i >= newX && j >= newY);
					i--, j--) {
				if(board.checkSquare(i, j) instanceof Piece) {
					restrict = true;
					break;
				}
			}
		}
		
		return restrict;
	}
}
