package chess;

public class Piece {
	
	public int x;
	public int y;
	public char color;

	protected Board tablero= Board.getInstance();
	
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
		return tablero;
	}

	public void setTablero(Board tablero) {
		this.tablero = tablero;
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
}
