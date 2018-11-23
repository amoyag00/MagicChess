package chess;

public class Piece {
	
	int[] coordinates = new int[2];
	
	char color;

	public void Piece() {}
	
	public char getColor() {
		return color;
	}

	public void setColor(char color) {
		this.color = color;
	}
}
