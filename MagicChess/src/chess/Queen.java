package chess;

public class Queen extends Piece{


	public boolean isRestricted(int newX, int newY) {

		boolean restrict = false;

		int movementX=Math.abs(this.x-newX);
		int movementY=Math.abs(this.y-newY);
		
		byte orientationX;
		byte orientationY;
		
		if(newX >= this.x) {
			orientationX=1;
		}else {
			orientationX=-1;
		}
		
		if(newY >= this.y) {
			orientationY=1;
		}else {
			orientationY=-1;
		}
		
		if((newX > tablero.getSize() || newX < 1 || (newY > tablero.getSize() || newY < 1))) {
			
			restrict=true;
			
		}else if(this.x == newX && this.y != newY){
			
			for(int i=1;i<movementY;i++) {
				
				int nextSquare=this.y+(i*orientationY);
				
				if(this.tablero.checkSquare(this.x, nextSquare)!= null) {
					restrict=true;
				}
			}
			
			if(this.tablero.checkSquare(newX, newY)!= null) {
				if(this.tablero.checkSquare(newX, newY).getColor() == this.color) {
					restrict=true;
				}
			}
			
		}else if(this.y == newY && this.x != newX){
			
			for(int i=1;i<movementX;i++) {
				
				int nextSquare=this.x+(i*orientationX);
				
				if(this.tablero.checkSquare(nextSquare, this.y)!= null) {
					restrict=true;
				}
			}
			
			if(this.tablero.checkSquare(newX, newY)!= null) {
				if(this.tablero.checkSquare(newX, newY).getColor() == this.color) {
					restrict=true;
				}
			}
			
		} else if(this.y != newY && this.x != newX){
			
			if(movementX != movementY) {
				restrict = true;
			} else {
				
				for(int i=1;i<movementX;i++) {
				
					int nextSquareX=this.x+(i*orientationX);
					int nextSquareY=this.y+(i*orientationY);
					
					if(this.tablero.checkSquare(nextSquareX, nextSquareY)!= null) {
						restrict=true;
					}
					
				}
				
				if(this.tablero.checkSquare(newX, newY)!= null) {
					if(this.tablero.checkSquare(newX, newY).getColor() == this.color) {
						restrict=true;
					}
				}
				
			}
			
			
		}
	
		return restrict;
	}
}
