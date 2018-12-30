package chess;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import com.fazecast.jSerialComm.SerialPort;

import arduino.*;
import voiceRecognition.VoiceController;

public class Main {
	
	public static void main (String args[]) {
/****************************************   Testing stepper motor movements ***********************/
		/*arduino.serialWrite("G28 Y\n");
		arduino.serialWrite("G1 Y200\n");
		arduino.serialWrite("M400\n");
		arduino.serialWrite("M280 P0 S160\n");
		arduino.serialWrite("G1 Y0\n""G4 P1000\n");
		arduino.serialWrite("M400\n");
		arduino.serialWrite("M280 P0 S0\n");*/
		
		
/****************************************  Testing undo in arduino level  ***************************/
		ArduinoController controller=new ArduinoController();
		controller.moveWithoutPiece(3, 4);
		System.out.println("log");
		controller.undoCapture("w", 1, 3, 8, 3);

		
		
/****************************************  Testing voice recognition *******************************/
		
		
		/*ArduinoController.getInstance().capturePiece("b", 3, 4, 2, 3);
		VoiceController.getInstance().parse("hola echo 5 entonces hotel 6 vale");*/
		
		
/**************************************** Testing  stockfish cpu (chess engine) *****************************/
		
		/*Stockfish sc=new Stockfish();
		sc.move(5,2,5,4);
		Movement move=sc.calculateMove();
		System.out.println(move.getOriginX()+" "+move.getOriginY()+" "+move.getDestX()+" "+move.getDestY());
		
		sc.move(7,1,6,3);
		move=sc.calculateMove();
		System.out.println(move.getOriginX()+" "+move.getOriginY()+" "+move.getDestX()+" "+move.getDestY());
		
		sc.move(6,1,1,6);
		move=sc.calculateMove();
		System.out.println(move.getOriginX()+" "+move.getOriginY()+" "+move.getDestX()+" "+move.getDestY());
		*/
	
/************************************************* Testing undo and casteling raspPi level ******************/	
		
		
		/*ChessController controller = new ChessController();
		controller.setGameMode("2player");
		Board board=controller.getBoard();
		
		controller.move(5,2,5,4);//w
		controller.move(5,7,5,5);//b
		controller.move(6,1,4,3);//w
		controller.move(2,8,1,6);//b
		controller.move(7,1,6,3);//w
		controller.move(5,8,5,7);//b
		controller.move(1,2,1,4);//w
		controller.move(5,7,5,8);//b
		//controller.undo();
		controller.move("shortW");//w
		//controller.undo();
		controller.move(4,7,4,5);//b
		controller.move(5,4,4,5);//w
		controller.undo();
		/*controller.move(5,5,5,4);//b
		controller.move(4,3,5,4);//w
		controller.move(6,7,6,5);//b
		controller.move(5,4,6,5);//w
		controller.move(4,8,4,5);//b
		System.out.println(board.toString());*/
		
	}
	
	
}
