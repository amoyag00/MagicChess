package chess;

public class Board {
	private static final int SIZE=8;
	private Square squares [][];
	
	public Board() {
		squares=new Square[this.SIZE][this.SIZE];
		
		for(int i=0;i<SIZE;i++) {
			for(int j=0;j<SIZE;j++) {
				squares[i][j]=new Square(i,j);
			}
		}
		
	}
	
	public Piece checkSquare(int x, int y) {
		Piece piece = squares[x][y].getPiece();
		return piece;
	}
	
	/**
	 * Creates the initial state of the board.
	 * 
	 */
	public void createPieces() {
		
	}
	
	
	
}
