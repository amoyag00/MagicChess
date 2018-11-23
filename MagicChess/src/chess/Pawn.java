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
			
			if ((this.coordinates[1] - newY) < (-1) && this.coordinates[1] != 1) {
				
				if ((this.coordinates[1] - newY)<(-2)) {
					
					restrict = true;
				}
				
			}else if ((this.coordinates[1] - newY) > 0) {
				
				restrict = true;
			}
			
			//Comprueba cuando come
			if(newX!=this.coordinates[0]) {
				
				if(Math.abs(this.coordinates[0]-newX)<2) {
					restrict = true;
				}else {
					//TODO Comprobar que la casilla no isEmpty()
				}
				
			}
			
		} else {
			if ((this.coordinates[1] - newY) > (1) && this.coordinates[1] != 6) {
				
				if ((this.coordinates[1] - newY)>2) {
					
					restrict = true;
				}
				
			}else if ((this.coordinates[1] - newY) < 0) {
				
				restrict = true;
			}
			//Comprueba cuando come
			if(newX!=this.coordinates[0]) {
				
				if(Math.abs(this.coordinates[0]-newX)<2) {
					restrict = true;
				}else {
					//TODO Comprobar que la casilla no isEmpty()
				}
				
			}
		}

		return restrict;
	}

}
