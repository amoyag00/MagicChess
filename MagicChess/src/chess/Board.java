package chess;

public class Board {
	
	private static Board instance;

	private static final int SIZE=8;
	private Square squares [][];


	private int whiteCapturedX;
	private int whiteCapturedY;
	private int blackCapturedX;
	private int blackCapturedY;
	
	public Board() {
		//TODO create squares for captured pieces. 2x8 each one
		squares=new Square[this.SIZE][this.SIZE];
		
		for(int i=0;i<SIZE;i++) {
			for(int j=0;j<SIZE;j++) {
				squares[i][j]=new Square(i,j);
			}
		}
		
	}
	
	
	/**
	 * Singleton pattern
	 * @return a unique instance
	 */
	public static Board getInstance() {
		if(instance==null) {
			instance=new Board();
		}
		
		return instance;
	}
	
	public static int getSize() {
		return SIZE;
	}


	public Piece checkSquare(int x, int y) {
		Piece piece = squares[x-1][y-1].getPiece();
		return piece;
	}
	
	/**
	 * Creates the initial state of the board.
	 * 
	 */
	public void createPieces() {
		
	}
	
	public Square getSquare(int x, int y) {
		return squares[x-1][y-1];
	}


	public void setSquare(Square square,int x, int y) {
		this.squares[x-1][y-1] = square;
	}
	
	/**
	 * returns the first empty square x coordinate for the color color
	 * @param color w if white b if black
	 * @return the x coordinate of the first empty square starting in one.
	 */
	public int getCapturedX(String color) {
		int square=0;
		if(color.equals("w")) {
			square=this.whiteCapturedX;
		}else if(color.equals("b")) {
			square=this.blackCapturedX;
		}
		return square;
	}
	
	/**
	 * returns the first empty square y coordinate for the color color
	 * @param color w if white b if black
	 * @return the y coordinate of the first empty square starting in one.
	 */
	public int getCapturedY(String color) {
		int square=0;
		if(color.equals("w")) {
			square=this.whiteCapturedY;
		}else if(color.equals("b")) {
			square=this.blackCapturedY;
		}
		return square;
	}
	
	/**
	 * Calculates the coordinates for the next captured piece
	 * @param color w if white b if black
	 */
	public void nextCapturedCoord(String color) {
		if(color.equals("w")) {
			if(this.whiteCapturedX==2) {
				this.whiteCapturedX=1;
				this.whiteCapturedY++;
			}else if(this.whiteCapturedX==1) {
				this.whiteCapturedX++;
			}
		}else if(color.equals("b")) {
			if(this.blackCapturedX==2) {
				this.blackCapturedX=1;
				this.blackCapturedY++;
			}else if(this.blackCapturedX==1) {
				this.blackCapturedX++;
			}
		}
	}
	
}
