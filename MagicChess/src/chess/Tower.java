package chess;

public class Tower extends Piece{

	Board board;
	boolean moved = false;
	
	public boolean isRestricted(int newX, int newY) {
		
		boolean restrict = false;
		
		if(this.coordinates[0] != newX && this.coordinates[1] != newY) {
			restrict = true;
		}
		else if(this.coordinates[0] != newX && this.coordinates[1] == newY) {
			if( newX > this.coordinates[0]) {
				for(int i = this.coordinates[0]; i<= newX; i++) {
					if(board.checkSquare(i, this.coordinates[1]) instanceof Piece) {
						restrict = true;
						break;
					}
				}
			}
			else {
				for(int i = this.coordinates[0]; i>= newX; i--) {
					if(board.checkSquare(i, this.coordinates[1]) instanceof Piece) {
						restrict = true;
						break;
					}
				}
			}
		}
		else if(this.coordinates[0] == newX && this.coordinates[1] != newY) {
			if( newY > this.coordinates[1]) {
				for(int i = this.coordinates[1]; i<= newY; i++) {
					if(board.checkSquare(this.coordinates[0], i) instanceof Piece) {
						restrict = true;
						break;
					}
				}
			}
			else {
				for(int i = this.coordinates[1]; i >= newY; i--) {
					if(board.checkSquare(this.coordinates[0], i) instanceof Piece) {
						restrict = true;
						break;
					}
				}
			}
		}
		
		return restrict;
	}
}
