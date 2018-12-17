package chess;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import com.fazecast.jSerialComm.SerialPort;

import arduino.*;
import voiceRecognition.VoiceController;

public class Main {
	
	public static void main (String args[]) {

		/*arduino.serialWrite("G28 Y\n");
		arduino.serialWrite("G1 Y200\n");
		arduino.serialWrite("M400\n");
		arduino.serialWrite("M280 P0 S160\n");
		arduino.serialWrite("G1 Y0\n""G4 P1000\n");
		arduino.serialWrite("M400\n");
		arduino.serialWrite("M280 P0 S0\n");*/

		ArduinoController controller=new ArduinoController();
		controller.move(3, 4, 2, 1);
		
		
		//System.out.println(arduino.serialRead());
		//serialWrite is an overridden method, allowing both characters and strings.
		//arduino.serialWrite('1', 20); //its second parameter even allows delays. more deta*/
		/*ArduinoController.getInstance().capturePiece("b", 3, 4, 2, 3);
		VoiceController.getInstance().parse("hola echo 5 entonces hotel 6 vale");*/
		
	}
	
	
}
