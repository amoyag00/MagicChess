package arduino;
import java.util.concurrent.TimeUnit;

import arduino.*;

public class Prueba {
	public static void main (String args[]) {
		String arduinoPort = "ttyUSB0"; //Your port name here
		int BAUD_RATE = 115200;
		/*Arduino arduino = new Arduino(arduinoPort, BAUD_RATE);
		System.out.println(arduino.openConnection()); 
		
		//arduino.serialWrite("G28 Y\n");
		arduino.serialWrite("G1 Y200\n");*/
		
		//System.out.println(arduino.serialRead());
		//serialWrite is an overridden method, allowing both characters and strings.
		//arduino.serialWrite('1', 20); //its second parameter even allows delays. more deta
	}
	
}
