package chess;

public class Movement {
	private int originX;
	private int originY;
	private int destX;
	private int destY;
	private String castling;
	private boolean isCastling;
	private boolean isCapture;
	private boolean rookFirstMove;
	private boolean kingFirstMove;
	private Piece piece;
	private String capturedColor;
	private boolean isPromotion;
	
	public Movement(int originX, int originY, int destX, int destY) {
		this.originX=originX;
		this.originY=originY;
		this.destX=destX;
		this.destY=destY;
		this.isCastling=false;
		this.isCapture=false;
		rookFirstMove=false;
		kingFirstMove=false;
	}
	
	public Movement(String casteling) {
		this.castling=casteling;
		this.isCastling=true;
	}
	
	public int getOriginX() {
		return originX;
	}
	public void setOriginX(int originX) {
		this.originX = originX;
	}
	public int getOriginY() {
		return originY;
	}
	public void setOriginY(int originY) {
		this.originY = originY;
	}
	public int getDestX() {
		return destX;
	}
	public void setDestX(int destX) {
		this.destX = destX;
	}
	public int getDestY() {
		return destY;
	}
	public void setDestY(int destY) {
		this.destY = destY;
	}
	
	public boolean isCastling() {
		return this.isCastling;
	}
	
	public String getCastling() {
		return this.castling;
	}
	
	public void setCapture(boolean capture) {
		this.isCapture=true;
	}
	
	public boolean isCapture() {
		return this.isCapture;
	}

	public boolean isRookFirstMove() {
		return rookFirstMove;
	}

	public void setRookFirstMove(boolean rookFirstMove) {
		this.rookFirstMove = rookFirstMove;
	}

	public boolean isKingFirstMove() {
		return kingFirstMove;
	}

	public void setKingFirstMove(boolean kingFirstMove) {
		this.kingFirstMove = kingFirstMove;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public String getCapturedColor() {
		return capturedColor;
	}

	public void setCapturedColor(String capturedColor) {
		this.capturedColor = capturedColor;
	}
	
	
	public void setIsPromotion(boolean isPromotion) {
		this.isPromotion=isPromotion;
	}
	
	public boolean isPromotion() {
		return this.isPromotion;
	}
	
	
}
