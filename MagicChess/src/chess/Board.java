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
			pawnW.setColor('w');
			this.squares[i][1].setPiece(pawnW);
		}
		
		this.squares[0][0].setPiece(leftRookW);
		leftRookW.setColor('w');

		this.squares[1][0].setPiece(leftKnightW);
		leftKnightW.setColor('w');
		this.squares[2][0].setPiece(leftBishopW);
		leftBishopW.setColor('w');
		this.squares[3][0].setPiece(queenW);
		queenW.setColor('w');
		this.squares[4][0].setPiece(kingW);
		kingW.setColor('w');
		this.squares[5][0].setPiece(rightBishopW);
		rightBishopW.setColor('w');
		this.squares[6][0].setPiece(rightKnightW);
		rightKnightW.setColor('w');
		this.squares[7][0].setPiece(rightRookW);
		rightRookW.setColor('w');
	
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
			pawnB.setColor('b');
			this.squares[i][6].setPiece(pawnB);
		}
		
		this.squares[0][7].setPiece(leftRookB);
		leftRookB.setColor('b');
		this.squares[1][7].setPiece(leftKnightB);
		leftKnightB.setColor('b');
		this.squares[2][7].setPiece(leftBishopB);
		leftBishopB.setColor('b');
		this.squares[3][7].setPiece(queenB);
		queenB.setColor('b');
		this.squares[4][7].setPiece(kingB);
		kingB.setColor('b');
		this.squares[5][7].setPiece(rightBishopB);
		rightBishopB.setColor('b');
		this.squares[6][7].setPiece(rightKnightB);
		rightKnightB.setColor('b');
		this.squares[7][7].setPiece(rightRookB);
		rightRookB.setColor('b');
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
	
	public Piece getPiece(int x, int y) {
		return this.squares[x-1][y-1].getPiece();
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
	
	/**
	 * Moves a piece from the captured board into the board
	 * @param color
	 * @param x position of the board where the piece will be revived
	 * @param y
	 */
	public void reviveIn(String color,int x, int y) {
		Piece temp;
		if(color.equals("w")) {
			if(this.whiteCapturedX==1) {
				this.whiteCapturedX=2;
				this.whiteCapturedY--;
			}else if(this.whiteCapturedX==2) {
				this.whiteCapturedX--;
			}
			
			temp=this.capturedWhite[this.whiteCapturedX-1][this.whiteCapturedY-1].getPiece();
			this.capturedWhite[this.whiteCapturedX-1][this.whiteCapturedY-1].free();
			this.squares[x-1][y-1].setPiece(temp);
		}else if(color.equals("b")) {
			if(this.blackCapturedX==1) {
				this.blackCapturedX=2;
				this.blackCapturedY--;
			}else if(this.blackCapturedX==2) {
				this.blackCapturedX--;
			}
			
			temp=this.capturedBlack[this.blackCapturedX-1][this.blackCapturedY-1].getPiece();
			this.capturedBlack[this.blackCapturedX-1][this.blackCapturedY-1].free();
			this.squares[x-1][y-1].setPiece(temp);
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
	
	
	public String toString() {
		return toString(this.squares)+"\nCaptured White\n"+toString(this.capturedWhite)+
				"\nCaptured Black\n"+toString(this.capturedBlack);
	}
	
	
	public String toString(Square [][] squares) {
		String result="+";
		for(int k=0;k<squares.length;k++) {
			result+="---+";
		}
		result+="\n";
		for(int j=squares[1].length-1;j>-1;j--) {
			result+="|";
			for(int i=0;i<squares.length;i++) {

				if(squares[i][j].isEmpty()) {
					result+="   |";
				}else if(squares[i][j].getPiece() instanceof Pawn) {
					if(squares[i][j].getPiece().getColor()=='w') {
						result+=" P |";
					}else {
						result+=" p |";
					}
					
				}else if(squares[i][j].getPiece() instanceof Rook) {
					if(squares[i][j].getPiece().getColor()=='w') {
						result+=" R |";
					}else {
						result+=" r |";
					}
				}else if(squares[i][j].getPiece() instanceof Knight) {
					if(squares[i][j].getPiece().getColor()=='w') {
						result+=" N |";
					}else {
						result+=" n |";
					}
				}else if(squares[i][j].getPiece() instanceof Bishop) {
					if(squares[i][j].getPiece().getColor()=='w') {
						result+=" B |";
					}else {
						result+=" b |";
					}
				}else if(squares[i][j].getPiece() instanceof Queen) {
					if(squares[i][j].getPiece().getColor()=='w') {
						result+=" Q |";
					}else {
						result+=" q |";
					}
				}else if(squares[i][j].getPiece() instanceof King) {
					if(squares[i][j].getPiece().getColor()=='w') {
						result+=" K |";
					}else {
						result+=" k |";
					}
				}
				
			}
			result+="\n+";
			for(int k=0;k<squares.length;k++) {
				result+="---+";
			}
			result+="\n";
		}
		return result;
	}
	
}
