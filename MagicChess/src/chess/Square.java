package chess;

public class Square {
	private Piece piece;
	private int x;
	private int y;
	
	public Square(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	public boolean isEmpty() {
		return this.piece==null;
	}
	
	public Piece getPiece() {
		return this.piece;
	}
	
	public void setPiece(Piece piece) {
		piece.setX(this.x);
		piece.setY(this.y);
		this.piece=piece;
	}
	
	public void free() {
		this.piece=null;
	}
	/**
	 * returns the type of the piece located in this square
	 * @return the type of piece
	 */
	public String getPieceClass() {
		return this.piece.getClass().toString();
	}
}
