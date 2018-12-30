package chess;

public class Knight extends Piece{

	
	public boolean isRestricted(int newX, int newY) {
		
		boolean restrict = false;
		

		if(!((((Math.abs(this.x - newX) == 2 && Math.abs(this.y - newY) == 1)) 
				|| ((Math.abs(this.y - newY) == 2 && Math.abs(this.x - newX) == 1))))) {
			restrict = true;
		}

		/*if(!restrict && board.checkSquare(newX, newY).getColor() == this.getColor()) {
			restrict = true;
		}*/
		
		return restrict;
	}
}
