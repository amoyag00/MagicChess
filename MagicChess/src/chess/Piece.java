package chess;

public abstract class Piece {
	
	public int x;
	public int y;
	public char color;

	protected Board board= Board.getInstance();
	

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public Board getTablero() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void Piece() {}
	
	public char getColor() {
		return color;
	}

	public void setColor(char color) {
		this.color = color;
	}
	
	public abstract boolean isRestricted(int newX, int newY);
}
