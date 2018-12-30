package chess;

public class King extends Piece{

	public boolean moved = false;
	
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
		
		if(movementY>1 || movementX>1) {
			restrict=true;
		}

		
		if((newX > board.getSize() || newX < 1 || (newY > board.getSize() || newY < 1))) {
			
			restrict=true;
			
		}else if(this.x == newX && this.y != newY){
			
			if(this.board.checkSquare(newX, newY)!= null) {
				if(this.board.checkSquare(newX, newY).getColor() == this.color) {
					restrict=true;
				}
			}
			
		}else if(this.y == newY && this.x != newX){
			

			if(this.board.checkSquare(newX, newY)!= null) {
				if(this.board.checkSquare(newX, newY).getColor() == this.color) {
					restrict=true;
				}
			}
			
		} else if(this.y != newY && this.x != newX){
			
			if(movementX != movementY) {
				restrict = true;
			} else {
				
				if(this.board.checkSquare(newX, newY)!= null) {
					if(this.board.checkSquare(newX, newY).getColor() == this.color) {
						restrict=true;
					}
				}
				
			}
			
			
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
