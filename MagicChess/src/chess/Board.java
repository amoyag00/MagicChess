package chess;

public class Board {
	
	private static Board instance;

	private static final int SIZE=8;
	private Square squares [][];
	private Square capturedWhite [][];
	private Square capturedBlack [][];

	private int whiteCapturedX;
	private int whiteCapturedY;
	private int blackCapturedX;
	private int blackCapturedY;
	
	public Board() {
		this.whiteCapturedX=1;
		this.blackCapturedX=1;
		this.whiteCapturedY=8;
		this.blackCapturedY=8;
		squares=new Square[this.SIZE][this.SIZE];
		this.capturedWhite=new Square [2][this.SIZE];
		this.capturedBlack=new Square [2][this.SIZE];
		
		for(int i=0;i<SIZE;i++) {
			for(int j=0;j<SIZE;j++) {
				squares[i][j]=new Square(i+1,j+1);
			}
		}
		
		for(int i=0;i<2;i++) {
			for(int j=0;j<this.SIZE;j++) {
				this.capturedWhite[i][j]=new Square(i,j);
				this.capturedBlack[i][j]=new Square(i,j);
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
			this.squares[i][1].setPiece(pawnW);
		}
		
		this.squares[0][0].setPiece(leftRookW);
		this.squares[1][0].setPiece(leftKnightW);
		this.squares[2][0].setPiece(leftBishopW);
		this.squares[3][0].setPiece(queenW);
		this.squares[4][0].setPiece(kingW);
		this.squares[5][0].setPiece(rightBishopW);
		this.squares[6][0].setPiece(rightKnightW);
		this.squares[7][0].setPiece(rightRookW);
	
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
			this.squares[i][6].setPiece(pawnB);
		}
		
		this.squares[0][7].setPiece(leftRookB);
		this.squares[1][7].setPiece(leftKnightB);
		this.squares[2][7].setPiece(leftBishopB);
		this.squares[3][7].setPiece(queenB);
		this.squares[4][7].setPiece(kingB);
		this.squares[5][7].setPiece(rightBishopB);
		this.squares[6][7].setPiece(rightKnightB);
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
				this.whiteCapturedY--;
			}else if(this.whiteCapturedX==1) {
				this.whiteCapturedX++;
			}
		}else if(color.equals("b")) {
			if(this.blackCapturedX==2) {
				this.blackCapturedX=1;
				this.blackCapturedY--;
			}else if(this.blackCapturedX==1) {
				this.blackCapturedX++;
			}
		}
	}
	
	public void move(int x, int y, int newX, int newY) {
		
		Piece temp=this.getSquare(x,y).getPiece();
		this.getSquare(newX, newY).setPiece(temp);
		this.getSquare(x, y).free();
	}
	
	/**
	 * Moves the piece to the captured board.
	 * @param x
	 * @param y
	 * @param color
	 */
	public void capture(int x, int y, String color) {
		Piece capturedPiece=this.squares[x-1][y-1].getPiece();
		this.squares[x-1][y-1].free();
		
		//If its white's turn the captured piece will be black and vice versa
		
		if(color.equals("b")) {
			this.capturedWhite[this.whiteCapturedX-1][this.whiteCapturedY-1].setPiece(capturedPiece);
			nextCapturedCoord("w");
		}else if(color.equals("w")) {
			this.capturedBlack[this.blackCapturedX-1][this.blackCapturedY-1].setPiece(capturedPiece);
			nextCapturedCoord("b");
		}
		
	}
	
	public Square[][] getCaptured(String color){
		if(color.equals("w")) {
			return this.capturedWhite;
		}else if(color.equals("b")) {
			return this.capturedBlack;
		}
		return null;
	}
	
}
