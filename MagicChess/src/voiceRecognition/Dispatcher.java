package voiceRecognition;

import chess.ChessController;

public class Dispatcher extends Thread {
	private VoiceController controller;
	private String possibleCommand="";
	
	public Dispatcher(String possibleCommand) {
		this.possibleCommand=possibleCommand;
		controller=VoiceController.getInstance();
	}
	
	/**
	 * Dispatches a request which may contain a command
	 */
	public synchronized void dispatch() {
		controller.parse(this.possibleCommand);
	}
	
	public void run() {
		dispatch();
	}
}
