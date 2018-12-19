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
		//Creating white pieces
		Rook leftRookW=new Rook();
		Rook rightRookW=new Rook();
		Bishop leftBishopW=new Bishop();
		Bishop rightBishopW=new Bishop();
		Knight leftKnightW=new Knight();
		Knight rightKnightW=new Knight();
		Queen queenW=new Queen();
		King kingW=new King();
		
		for(int i=0;i<this.SIZE;i++) {
			Pawn pawnW=new Pawn();
			pawnW.color='w';
			this.squares[1][i].setPiece(pawnW);
		}
		
		this.squares[0][0].setPiece(leftRookW);
		this.squares[0][1].setPiece(leftKnightW);
		this.squares[0][2].setPiece(leftBishopW);
		this.squares[0][3].setPiece(queenW);
		this.squares[0][4].setPiece(kingW);
		this.squares[0][5].setPiece(rightBishopW);
		this.squares[0][6].setPiece(rightKnightW);
		this.squares[0][7].setPiece(rightRookW);
		
		//Creating black pieces
		Rook leftRookB=new Rook();
		Rook rightRookB=new Rook();
		Bishop leftBishopB=new Bishop();
		Bishop rightBishopB=new Bishop();
		Knight leftKnightB=new Knight();
		Knight rightKnightB=new Knight();
		Queen queenB=new Queen();
		King kingB=new King();
		
		for(int i=0;i<this.SIZE;i++) {
			Pawn pawnB=new Pawn();
			pawnB.color='b';
			this.squares[6][i].setPiece(pawnB);
		}
		
		this.squares[7][0].setPiece(leftRookB);
		this.squares[7][1].setPiece(leftKnightB);
		this.squares[7][2].setPiece(leftBishopB);
		this.squares[7][3].setPiece(queenB);
		this.squares[7][4].setPiece(kingB);
		this.squares[7][5].setPiece(rightBishopB);
		this.squares[7][6].setPiece(rightKnightB);
		this.squares[7][7].setPiece(rightRookB);
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
	
	public void move(int x, int y, int newX, int newY) {
		
		Piece temp=this.getSquare(x,y).getPiece();
		this.getSquare(newX, newY).setPiece(temp);
		this.getSquare(newX, newY).free();
	}
	
}
