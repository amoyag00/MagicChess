package chess;

public class Pawn extends Piece {

	/**
	 * Check if the move is allowed. If the piece is white the pawn go from 0 to 7 and vice versa
	 * 
	 * @param newX new X coordinate
	 * @param newY new Y coordinate
	 * @return if the move is allowed for the pawn.
	 */
	public boolean isRestricted(int newX, int newY) {

		boolean restrict = false;

		if (color == 'w') {
			
			if ((this.y - newY) < (-1) && this.y != 1) {
				
				if ((this.y - newY)<(-2)) {
					
					restrict = true;
				}
				if(tablero.checkSquare(newX, newY)!=null) {
					restrict=true;
				}
			}else if ((this.y - newY) > 0) {
				
				restrict = true;
			}
			
			//Comprueba cuando come
			if(newX!=this.x) {
				
				if(Math.abs(this.x-newX)<2) {
					restrict = true;
				}else {
					if(tablero.checkSquare(newX, newY)!=null) {
						if(tablero.checkSquare(newX, newY).getColor()==this.color) {
							restrict=true;
						}
					}else {
						restrict=true;
					}
				}
				
			}
			
		} else {
			if ((this.y - newY) > (1) && this.y != 6) {
				
				if ((this.y - newY)>2) {
					
					restrict = true;
				}
				if(tablero.checkSquare(newX, newY)!=null) {
					restrict=true;
				}
				
			}else if ((this.y - newY) < 0) {
				
				restrict = true;
			}
			//Comprueba cuando come
			if(newX!=this.x) {
				
				if(Math.abs(this.x-newX)<2) {
					restrict = true;
				}else {
					if(tablero.checkSquare(newX, newY)!=null) {
						if(tablero.checkSquare(newX, newY).getColor()==this.color) {
							restrict=true;
						}
					}else {
						restrict=true;
					}
				}
				
			}
		}

		return restrict;
	}

}
