package chess;
import java.util.concurrent.TimeUnit;

import arduino.*;
import voiceRecognition.VoiceController;

public class Main {
	public static void main (String args[]) {
		/*String arduinoPort = "ttyUSB0"; //Your port name here
		int BAUD_RATE = 115200;
		Arduino arduino = new Arduino(arduinoPort, BAUD_RATE);
		System.out.println(arduino.openConnection()); 
		
		//arduino.serialWrite("G28 Y\n");
		arduino.serialWrite("G1 Y200\n");
		
		//System.out.println(arduino.serialRead());
		//serialWrite is an overridden method, allowing both characters and strings.
		//arduino.serialWrite('1', 20); //its second parameter even allows delays. more deta*/
		ArduinoController.getInstance().capturePiece("b", 3, 4, 2, 3);
		VoiceController.getInstance().parse("hola echo 5 entonces hotel 6 vale");
		
	}
	
}
