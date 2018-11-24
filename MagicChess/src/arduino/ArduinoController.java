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
		this.currentX=toX;
		this.currentY=toY;
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
	}
	
	/**
	 * Uses the servomotor to rise the magnet and grab the piece
	 */
	public void grab() {
		String command="";
		serialWrite(command);
	}
	
	/**
	 * Uses the servomotor to releases the piece grabbed with the magnet.
	 */
	public void release() {
		String command="";
		serialWrite(command);
	}
	
	/**
	 * Moves to the origin of the board, the center of the square (A,1)
	 */
	public void moveToBoardOrigin() {
		String command= "G1 X "+this.boardDistanceX+" Y"+this.boardDistanceY;
		serialWrite(command);
		this.currentX=0;
		this.currentY=0;
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
}
