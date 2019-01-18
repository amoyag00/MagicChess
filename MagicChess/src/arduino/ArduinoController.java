package arduino;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/*import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;*/

/**
 * This class receives orders 
 * @author alex
 *
 */
public class ArduinoController {
	
	private static ArduinoController instance;
	private final String portName="/dev/ttyUSB0";
	private final int BAUD_RATE=115200;
	private Arduino arduino;
	private int SIZE=8; //board number of squares
	private final int SQUARE_MM=60;//milimmeters of each square
	private int boardDistanceX=106;//distance in x from plotters (0,0) to boards (0,0)
	private int boardDistanceY=50;//distance in y from plotters (0,0) to boards (0,0)
	private int currentX;
	private int currentY;
	

	public ArduinoController() {
		this.arduino=new Arduino(this.portName,this.BAUD_RATE);
		this.connect();
		this.currentX=1;
		this.currentY=1;
		moveToOrigin();
		moveToBoardOrigin();
		serialWrite("M280 P1 S20");
		serialWrite("G91"); //Movements are relative to the last position
		//serialWrite("M220 S100");//Setting speed
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
	public void connect() {
		this.arduino.openConnection();
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
		delay();
		grab();
		delay();
		moveWithPiece(toX,toY);
		delay();
		release();
		serialWrite("M400");
		delay();
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
		String command1="M280 P1 S140";
		String wait="M400"; //Makes arduino wait for the previous buffered commands to finish
		serialWrite(wait);
		serialWrite(command1);
	}
	
	/**
	 * Uses the servomotor to releases the piece grabbed with the magnet.
	 */
	public void release() {
		String command="M280 P1 S20";
		String wait="M400"; //Makes arduino wait for the previous buffered commands to finish
		serialWrite(wait);
		serialWrite(command);
	}
	
	/**
	 * Moves to the origin of the board, the center of the square (A,1)
	 */
	public void moveToBoardOrigin() {
		serialWrite("G90");
		String command= "G1 X "+this.boardDistanceX+" Y"+this.boardDistanceY;
		serialWrite(command);
		serialWrite("G91");
		this.currentX=1;
		this.currentY=1;
	}
	/**
	 * Moves to the origin of the plotter on all axis. Different from the origin of the board.
	 */
	public void moveToOrigin() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		serialWrite("G28");
	}
	
	
	/**
	 * performs a short castling
	 * @param color w if white b if black
	 */
	public void shortCasteling(String color) {
		if(color.equals("w")) {
			//Move king
			moveWithoutPiece(5, 1);
			grab();
			moveWithPiece(7,1);
			release();
			
			//Move rook
			moveWithoutPiece(8,1);
			grab();
			moveWithPiece(6,1);
			release();
		}else if(color.equals("b")) {
			//Move king
			moveWithoutPiece(5, 8);
			grab();
			moveWithPiece(7,8);
			release();
			
			//Move rook
			moveWithoutPiece(8,8);
			grab();
			moveWithPiece(6,8);
			release();
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
			grab();
			moveWithPiece(3,1);
			release();
			
			//Move rook
			moveWithoutPiece(1,1);
			grab();
			moveWithPiece(4,1);
			release();
		}else if(color.equals("b")) {
			//Move king
			moveWithoutPiece(5, 8);
			grab();
			moveWithPiece(3,8);
			release();
			
			//Move rook
			moveWithoutPiece(1,8);
			grab();
			moveWithPiece(4,8);
			release();
		}
		
	}
	
	/**
	 * Undoes a shortCastling
	 * @param color
	 */
	public void undoShortCastling(String color) {
		if(color.equals("w")) {
			//Move king
			moveWithoutPiece(7, 1);
			grab();
			moveWithPiece(5,1);
			release();
			
			//Move rook
			moveWithoutPiece(6,1);
			grab();
			moveWithPiece(8,1);
			release();
		}else if(color.equals("b")) {
			//Move king
			moveWithoutPiece(7, 8);
			grab();
			moveWithPiece(5,8);
			release();
			
			//Move rook
			moveWithoutPiece(6,8);
			grab();
			moveWithPiece(8,8);
			release();
		}
	}
	
	
	/**
	 * Undoes a long castling
	 * @param color
	 */
	public void undoLongCastling(String color) {
		if(color.equals("w")) {
			//Move king
			moveWithoutPiece(3, 1);
			grab();
			moveWithPiece(5,1);
			release();
			
			//Move rook
			moveWithoutPiece(4,1);
			grab();
			moveWithPiece(1,1);
			release();
		}else if(color.equals("b")) {
			//Move king
			moveWithoutPiece(3, 8);
			grab();
			moveWithPiece(5,8);
			release();
			
			//Move rook
			moveWithoutPiece(4,8);
			grab();
			moveWithPiece(1,8);
			release();
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
		System.out.println("Capture");
		String command1="";//get out of the square
		String command2="";//get out of the board and enter in middle line of captured section
		String command3="";//move Y in the captured section
		String command4="";//move X in the captured section
		
		moveWithoutPiece(x, y);
		delay();
		grab();
		delay();
		
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
		delay();
		release();
		delay();
		moveToBoardOrigin();
			
	}
	
	/**
	 * Undoes a capture
	 * @param color
	 * @param capturedX
	 * @param capturedY
	 * @param destX
	 * @param destY
	 */
	public void undoCapture(String color, int capturedX, int capturedY, int destX, int destY) {
		System.out.println("Undo capture");
		String command1="";//get out of the square
		String command2="";//get out of the capturedBoard and enter in the board
		String command3="";//move Y in the board
		String command4="";//move X in the captured section
		String command5="";//Enter in the square
		
		
		if(color.equals("w")) {
			moveWithoutPiece( capturedX-3, capturedY);
			delay();
			grab();
			delay();
		
			command2="G1 X"+( (3-capturedX)*this.SQUARE_MM + this.SQUARE_MM/2 ) ;
			
			if(destY>capturedY) {
				command1="G1 Y"+this.SQUARE_MM/2;
				command3="G1 Y"+ ( (destY-capturedY-1)*this.SQUARE_MM );
				command5="G1 Y"+ this.SQUARE_MM/2;
			}else if(destY==capturedY){
				command1="G1 Y"+this.SQUARE_MM/2;
				command3="G1 Y"+ ( (destY-capturedY)*this.SQUARE_MM );
				command5="G1 Y-"+ this.SQUARE_MM/2;
			}else{
				command1="G1 Y-"+this.SQUARE_MM/2;
				command3="G1 Y"+ ( (destY-capturedY+1)*this.SQUARE_MM );
				command5="G1 Y-"+ this.SQUARE_MM/2;
			}
			
			command4= "G1 X"+ (destX*this.SQUARE_MM - this.SQUARE_MM/2) ;
			
		}else if(color.equals("b")) {
			moveWithoutPiece( capturedX+9, capturedY);
			delay();
			grab();
			delay();
		
			
			command2="G1 X-"+( (capturedX)*this.SQUARE_MM + this.SQUARE_MM/2 ) ;
			
			if(destY>capturedY) {
				command1="G1 Y"+this.SQUARE_MM/2;
				command3="G1 Y"+ ( (destY-capturedY-1)*this.SQUARE_MM );
				command5="G1 Y"+ this.SQUARE_MM/2;
			}else if(destY==capturedY){
				command1="G1 Y"+this.SQUARE_MM/2;
				command3="G1 Y"+ ( (destY-capturedY)*this.SQUARE_MM );
				command5="G1 Y-"+ this.SQUARE_MM/2;
			}else{
				command1="G1 Y-"+this.SQUARE_MM/2;
				command3="G1 Y"+ ( (destY-capturedY+1)*this.SQUARE_MM );
				command5="G1 Y-"+ this.SQUARE_MM/2;
			}
			
			command4= "G1 X-"+ ((8-destX)*this.SQUARE_MM + this.SQUARE_MM/2) ;
			
		}
		serialWrite(command1);
		serialWrite(command2);
		serialWrite(command3);
		serialWrite(command4);
		serialWrite(command5);
		release();
		/*delay();
		release();
		delay();
		moveToBoardOrigin();*/
	}
					
	
	/**
	 * Writes the command into arduino through serial USB port
	 * @param command
	 */
	public void serialWrite(String command) {
		System.out.println(command);
		this.arduino.serialWrite(command+"\n");
		
	}
	
	/**
	 * Introduces a small delay of 500ms
	 */
	public void delay() {
		String delay="G4 P500";
		this.serialWrite(delay);
	}
	
	
}
