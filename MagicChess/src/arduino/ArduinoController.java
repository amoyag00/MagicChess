package arduino;

/**
 * This class receives orders 
 * @author alex
 *
 */
public class ArduinoController {
	
	private static ArduinoController instance;
	private final String portName="ttyUSB0";
	private final int BAUD_RATE=115200;
	private Arduino arduino;
	private int SIZE=8; //board number of squares
	private final int SQUARE_MM=40;//milimmeters of each square
	private int boardDistanceX;//distance in x from plotters (0,0) to boards (0,0)
	private int boardDistanceY;//distance in y from plotters (0,0) to boards (0,0)
	private int currentX;
	private int currentY;
	

	public ArduinoController() {
		//this.connect();
		this.currentX=1;
		this.currentY=1;
	}
	
	/**
	 * Singleton
	 * @return a unique instance of ChessController
	 */
	public static ArduinoController getInstance() {
		if(instance==null) {
			instance=new ArduinoController();
		}
		
		return instance;
	}
	
	
	
	/**
	 * Stablishes a serial connection with arduino.
	 * @return true if the connection was made, false otherwise.
	 */
	public boolean connect() {
		this.arduino = new Arduino(this.portName, this.BAUD_RATE);
		return arduino.openConnection();
	}
	
	/**
	 * Moves a piece from (fromX, fromY) to (toX, toY)
	 * @param fromX x origin coordinate
	 * @param fromY y origin coordinate
	 * @param toX x destination coordinate
	 * @param toY y destination coordinate
	 */
	public void move(int fromX, int fromY, int toX, int toY) {
		moveWithoutPiece(fromX, fromY);
		//grab();
		moveWithPiece(toX,toY);
		//release();
		
	}
	
	/**
	 * Goes to the specified point directly. Used when a piece is not grabbed.
	 * @param x
	 * @param y
	 */
	public void moveWithoutPiece(int x, int y) {
		int destX=( x - this.currentX )* (this.SQUARE_MM);
		int destY=( y - this.currentY )* (this.SQUARE_MM);
		String command="G1 X"+destX+" Y"+destY;
		serialWrite(command);
		this.currentX=x;
		this.currentY=y;
	}
	
	/**
	 * Moves through the edges of the squares with a piece grabbed.
	 * @param x
	 * @param y
	 */
	public void moveWithPiece(int x, int y) {
		String command1="G1 X";//Takes the piece out of the square into the edge
		String command2="G1 Y";//Moves Y axis
		String command3="G1 X";//Moves X axis
		String command4="G1 Y";//Puts the piece inside the square
		
		if(x>=this.currentX) {
			command1+=this.SQUARE_MM/2;
			command3+=(x-1-this.currentX)*this.SQUARE_MM + this.SQUARE_MM/2;
		}else {
			command1+="-"+this.SQUARE_MM/2;
			command3+=(x+1-this.currentX)*this.SQUARE_MM + -1*this.SQUARE_MM/2;
		}
		
		if(y>=this.currentY) {
			command2+=this.SQUARE_MM/2+ this.SQUARE_MM*(y-this.currentY);
			command4+=-1*this.SQUARE_MM/2;
		}else {
			command2+=-1*this.SQUARE_MM/2+ this.SQUARE_MM*(y-this.currentY);
			command4+=this.SQUARE_MM/2;
		}
		
		serialWrite(command1);
		serialWrite(command2);
		serialWrite(command3);
		serialWrite(command4);
		this.currentX=x;
		this.currentY=y;
	}
	
	/**
	 * Uses the servomotor to rise the magnet and grab the piece
	 */
	public void grab() {
		String command="M280 P0 S180";
		serialWrite(command);
	}
	
	/**
	 * Uses the servomotor to releases the piece grabbed with the magnet.
	 */
	public void release() {
		String command="M280 P0 S0";
		serialWrite(command);
	}
	
	/**
	 * Moves to the origin of the board, the center of the square (A,1)
	 */
	public void moveToBoardOrigin() {
		String command= "G1 X "+this.boardDistanceX+" Y"+this.boardDistanceY;
		serialWrite(command);
		this.currentX=1;
		this.currentY=1;
	}
	/**
	 * Moves to the origin of the plotter on all axis. Different from the origin of the board.
	 */
	public void moveToOrigin() {
		serialWrite("G28");
	}
	
	/**
	 * Writes a command in the USB serial connection.
	 * @param command
	 */
	public void serialWrite(String command) {
		//this.arduino.serialWrite(command);
		System.out.println(command);
	}
	
	/**
	 * performs a short castling
	 * @param color w if white b if black
	 */
	public void shortCasteling(String color) {
		if(color.equals("w")) {
			//Move king
			moveWithoutPiece(5, 1);
			//grab();
			moveWithPiece(7,1);
			//release()
			
			//Move rook
			moveWithoutPiece(8,1);
			//grab();
			moveWithPiece(6,1);
		}else if(color.equals("b")) {
			//Move king
			moveWithoutPiece(5, 8);
			//grab();
			moveWithPiece(7,8);
			//release()
			
			//Move rook
			moveWithoutPiece(8,8);
			//grab();
			moveWithPiece(6,8);
		}
	}
	
	/**
	 * performs a long castling
	 * @param color w if white b if black
	 */
	public void longCasteling(String color) {
		if(color.equals("w")) {
			//Move king
			moveWithoutPiece(5, 1);
			//grab();
			moveWithPiece(3,1);
			//release()
			
			//Move rook
			moveWithoutPiece(1,1);
			//grab();
			moveWithPiece(4,1);
		}else if(color.equals("b")) {
			//Move king
			moveWithoutPiece(5, 8);
			//grab();
			moveWithPiece(3,8);
			//release()
			
			//Move rook
			moveWithoutPiece(1,8);
			//grab();
			moveWithPiece(4,8);
		}
		
	}
	
	/**
	 * Moves a piece from the board to the captured pieces' section
	 * @param color of the piece captured
	 * @param x coordinate x where the piece is located
	 * @param y coordinate y where the piece is located
	 * @param capturedX coordinate x of the captured pieces section
	 *  where the piece must be moved
	 * @param capturedY coordinate y of the captured pieces section
	 *  where the piece must be moved
	 */
	public void capturePiece(String color, int x, int y, int capturedX, int capturedY) {
		String command1="";//get out of the square
		String command2="";//get out of the board and enter in middle line of captured section
		String command3="";//move Y in the captured section
		String command4="";//move X in the captured section
		
		moveWithoutPiece(x, y);
		//grab();
		
		
		if(color.equals("w")) {
			command1="G1 Y"+this.SQUARE_MM/2;
			command2="G1 X-"+( (this.currentX + 1)*this.SQUARE_MM + this.SQUARE_MM/2 ) ;
			
			if(capturedY>=this.currentY) {
				command3="G1 Y"+ ( (capturedY-this.currentY)*this.SQUARE_MM - this.SQUARE_MM/2 );
			}else {
				command3="G1 Y"+ ( (capturedY-this.currentY)*this.SQUARE_MM - this.SQUARE_MM/2 );
			}
			
			if(capturedX==1) {
				command4="G1 X-"+this.SQUARE_MM/2;
			}else if(capturedX==2) {
				command4="G1 X"+this.SQUARE_MM/2;
			}
			
			
		}else if(color.equals("b")) {
			command1="G1 Y"+this.SQUARE_MM/2;
			command2="G1 X"+( (this.SIZE-this.currentX + 2)*this.SQUARE_MM + this.SQUARE_MM/2 ) ;
			
			if(capturedY>=this.currentY) {
				command3="G1 Y"+ ( (capturedY-this.currentY)*this.SQUARE_MM - this.SQUARE_MM/2 );
			}else {
				command3="G1 Y"+ ( (capturedY-this.currentY)*this.SQUARE_MM - this.SQUARE_MM/2 );
			}
			
			if(capturedX==1) {
				command4="G1 X-"+this.SQUARE_MM/2;
			}else if(capturedX==2) {
				command4="G1 X"+this.SQUARE_MM/2;
			}
		}
		serialWrite(command1);
		serialWrite(command2);
		serialWrite(command3);
		serialWrite(command4);
		//release();
		moveToBoardOrigin();
			
	}
}
