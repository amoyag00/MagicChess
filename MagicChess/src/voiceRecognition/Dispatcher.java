package voiceRecognition;

import chess.ChessController;

public class Dispatcher extends Thread {
	private VoiceController controller;
	private String possibleCommand="";
	
	public Dispatcher(String possibleCommand) {
		controller=VoiceController.getInstance();
	}
	
	public synchronized void dispatch() {
		controller.parse(this.possibleCommand);
	}
	
	public void run() {
		dispatch();
	}
}
