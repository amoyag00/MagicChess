package voiceRecognition;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import chess.ChessController;

public class Main {
	private VoiceController controller;
	private ChessController chess;
	
	public Main() {
		this.controller=VoiceController.getInstance();
		chess=ChessController.getInstance();
		chess.setGameMode("2player");
	}
	public void executePythonVoiceRecognition() {
		while(true) {
			ProcessBuilder ps=new ProcessBuilder("python","/home/jack/python-docs-samples/speech/cloud-client/transcribe_streaming_mic.py");

			//From the DOC:  Initially, this property is false, meaning that the 
			//standard output and error output of a subprocess are sent to two 
			//separate streams
			try {
				ps.redirectErrorStream(true);
		
				Process pr = ps.start();  
		
				BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
				String line;
				
				while ((line = in.readLine()) != null) {
					System.out.println(line);
					controller.parse(line);
					
				}
				
				pr.waitFor();
				System.out.println("ok!");
		
				in.close();
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		}
		
	
	public static void main(String args [] ){
		new Main().executePythonVoiceRecognition();
	}
	
	
}
