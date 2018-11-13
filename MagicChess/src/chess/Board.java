package chess;

public class Board {
	private static final int SIZE=8;
	private Square boxes [][];
	
	public Board() {
		boxes=new Square[this.SIZE][this.SIZE];
		
		for(int i=0;i<SIZE;i++) {
			for(int j=0;j<SIZE;j++) {
				boxes[i][j]=new Square(i,j);
			}
		}
		
	}
	
	
	
}
