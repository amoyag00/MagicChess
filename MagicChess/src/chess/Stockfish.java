package chess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Stack;

/**
 * A simple and efficient client to run Stockfish from Java
 * 
 * @author Rahul A R https://github.com/rahular/chess-misc
 * 
 * 
 */
public class Stockfish {

	private Process engineProcess;
	private BufferedReader processReader;
	private OutputStreamWriter processWriter;
	private int originX;
	private int originY;
	private int destX;
	private int destY;
	private String fen="";
	private Stack<String> movements;
	private static final String PATH = "/usr/games/stockfish";
	
	
	public Stockfish() {
		startEngine();
		sendCommand("position startpos");
		sendCommand("d");
		updateFen();
		this.movements=new Stack<String>();		
	}
	
	
	/**
	 * Starts Stockfish engine as a process and initializes it
	 * 
	 * @param None
	 * @return True on success. False otherwise
	 */
	public boolean startEngine() {
		try {
			engineProcess = Runtime.getRuntime().exec(PATH);
			processReader = new BufferedReader(new InputStreamReader(
					engineProcess.getInputStream()));
			processWriter = new OutputStreamWriter(
					engineProcess.getOutputStream());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Takes in any valid UCI command and executes it
	 * 
	 * @param command
	 */
	public void sendCommand(String command) {
		try {
			processWriter.write(command + "\n");
			processWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This is generally called right after 'sendCommand' for getting the raw
	 * output from Stockfish
	 * 
	 * @param waitTime
	 *            Time in milliseconds for which the function waits before
	 *            reading the output. Useful when a long running command is
	 *            executed
	 * @return Raw output from Stockfish
	 */
	public String getOutput(int waitTime) {
		StringBuffer buffer = new StringBuffer();
		try {
			Thread.sleep(waitTime);
			sendCommand("isready");
			while (true) {
				String text = processReader.readLine();
				if (text.equals("readyok"))
					break;
				else
					buffer.append(text + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/**
	 * This function returns the best move for a given position after
	 * calculating for 'waitTime' ms
	 * 
	 * @param fen
	 *            Position string
	 * @param waitTime
	 *            in milliseconds
	 * @return Best Move in PGN format
	 */
	public String getBestMove(String fen, int waitTime) {
		sendCommand("position fen " + fen);
		sendCommand("go movetime " + waitTime);
		return getOutput(waitTime + 2000).split("bestmove ")[1].split(" ")[0];
	}

	/**
	 * Stops Stockfish and cleans up before closing it
	 */
	public void stopEngine() {
		try {
			sendCommand("quit");
			processReader.close();
			processWriter.close();
		} catch (IOException e) {
		}
	}

	/**
	 * Get a list of all legal moves from the given position
	 * 
	 * @param fen
	 *            Position string
	 * @return String of moves
	 */
	public String getLegalMoves(String fen) {
		sendCommand("position fen " + fen);
		sendCommand("d");
		return getOutput(0).split("Legal moves: ")[1];
	}

	/**
	 * Draws the current state of the chess board
	 * 
	 * @param fen
	 *            Position string
	 */
	public void drawBoard(String fen) {
		sendCommand("position fen " + fen);
		sendCommand("d");

		String[] rows = getOutput(0).split("\n");

		for (int i = 1; i < 18; i++) {
			System.out.println(rows[i]);
		}
	}

	/**
	 * Get the evaluation score of a given board position
	 * @param fen Position string
	 * @param waitTime in milliseconds
	 * @return evalScore
	 */
	public float getEvalScore(String fen, int waitTime) {
		sendCommand("position fen " + fen);
		sendCommand("go movetime " + waitTime);

		float evalScore = 0.0f;
		String[] dump = getOutput(waitTime + 20).split("\n");
		for (int i = dump.length - 1; i >= 0; i--) {
			if (dump[i].startsWith("info depth ")) {
				try {
				evalScore = Float.parseFloat(dump[i].split("score cp ")[1]
						.split(" nodes")[0]);
				} catch(Exception e) {
					evalScore = Float.parseFloat(dump[i].split("score cp ")[1]
							.split(" upperbound nodes")[0]);
				}
			}
		}
		return evalScore/100;
	}
	
	/**
	 * Moves the piece in originX,destX to originY, destY
	 * @param originX
	 * @param originY
	 * @param destX
	 * @param destY
	 */
	public void move(int originX,int originY,int destX, int destY) {
		String originx=letterOf(originX);
		String originy=String.valueOf(originY);
		String destx=letterOf(destX);
		String desty=String.valueOf(destY);
		
		String movement=originx+originy+destx+desty;
		this.movements.push(movement);
		this.sendCommand( "position startpos moves "+getMovements() );
		this.sendCommand("d");
		updateFen();
		
	}
	
	/**
	 * Calculates the best move
	 */
	public Movement calculateMove() {
		String bestMove=this.getBestMove(this.fen, 10000);
		this.movements.push(bestMove);
		this.sendCommand( "position startpos moves "+getMovements() );
		this.sendCommand("d");
		updateFen();
		Movement lastMovement;
		
		if(bestMove.equals("e1g1")) {
			lastMovement=new Movement("shortW");
		}else if(bestMove.equals("e1c1")) {
			lastMovement=new Movement("longW");
		}else if(bestMove.equals("e8g8")) {
			lastMovement=new Movement("shortB");
		}else if(bestMove.equals("e8c8")) {
			lastMovement=new Movement("longB");
		}else {
			this.originX= bestMove.charAt(0) - 'a' + 1;
			this.originY=Character.getNumericValue(bestMove.charAt(1));
			this.destX=bestMove.charAt(2) - 'a' + 1;
			this.destY=Character.getNumericValue(bestMove.charAt(3));
			lastMovement=new Movement(originX,originY,destX,destY);
		}
		
		return lastMovement;
	}
	
	/**
	 * Undoes a movement
	 */
	public void undo() {
		this.movements.pop();
	}
	
	public void promote(int originX,int originY,int destX,int destY, String promotedPiece) {
		String originx=letterOf(originX);
		String originy=String.valueOf(originY);
		String destx=letterOf(destX);
		String desty=String.valueOf(destY);
		
		if(promotedPiece.equals("queen")) {
			promotedPiece="q";
		}else if(promotedPiece.equals("rook")) {
			promotedPiece="r";
		}else if(promotedPiece.equals("bishop")) {
			promotedPiece="b";
		}else if(promotedPiece.equals("knight")) {
			promotedPiece="k";
		}
		String movement=originx+originy+destx+desty+promotedPiece;
		this.movements.push(movement);
		this.sendCommand( "position startpos moves "+getMovements() );
		this.sendCommand("d");
		updateFen();
	}
	/**
	 * Updates the fen
	 * 
	 */
	public void updateFen() {
		String output=getOutput(200);
		String lines []=output.split("\n");
		this.fen=lines[lines.length-3].substring(5);
	}
	
	

	/**
	 * Converts a letter to a number
	 * @param num
	 * @return
	 */
	public String letterOf(int num) {
		String letter="";
		switch(num) {
		case 1:
			letter="a";
			break;
		case 2:
			letter="b";
			break;
		case 3:
			letter="c";
			break;
		case 4:
			letter="d";
			break;
		case 5:
			letter="e";
			break;
		case 6:
			letter="f";
			break;
		case 7:
			letter="g";
			break;
		case 8:
			letter="h";
			break;
		}
		return letter;
	}
	
	public String getMovements() {
		String movements="";
		Stack<?> copy=(Stack<?>)this.movements.clone();
		while(!copy.isEmpty()) {
			movements=copy.pop()+" "+movements;
		}
		return movements;
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
	public String getFen() {
		return this.fen;
	}
	
	
}