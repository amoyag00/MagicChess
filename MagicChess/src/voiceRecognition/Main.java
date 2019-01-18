package voiceRecognition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import arduino.Arduino;
import chess.ChessController;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Main {
	private VoiceController controller;
	private ChessController chess;
	
	public Main() {
		this.controller=VoiceController.getInstance();
		chess=ChessController.getInstance();
		chess.setGameMode("2player");
		/*try {
			FileInputStream fileInputStream= new FileInputStream("modoJuego.mp3");
			Player player = new Player(fileInputStream);
			player.play();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}
	public void executePythonVoiceRecognition() {
		while(true) {
			ProcessBuilder ps=new ProcessBuilder("python","/home/pi/python-docs-samples/speech/cloud-client/transcribe_streaming_mic.py");

			//From the DOC:  Initially, this property is false, meaning that the 
			//standard output and error output of a subprocess are sent to two 
			//separate streams
			try {
				ps.redirectErrorStream(true);
		
				Process pr = ps.start();  
		
				BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
				String line;
				
				while ((line = in.readLine()) != null) {
					controller.parse(line);
					System.out.println(line +" "+LocalDateTime.now().getHour() + LocalDateTime.now().getMinute() + LocalDateTime.now().getSecond());	
				}
				
				pr.waitFor();
				System.out.println("ok!");
		
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		}
		
	
	public static void main(String args [] ){
		new Main().executePythonVoiceRecognition();
		/*Arduino ard = new Arduino("/dev/ttyUSB0",115200);
		ard.openConnection();
		ard.serialWrite("G28 X\n");
		ard.serialWrite("G1 X100\n");*/
		System.out.println("log");
	}
	
	
}
