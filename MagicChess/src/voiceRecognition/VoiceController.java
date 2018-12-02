package voiceRecognition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import chess.ChessController;

/**
 * This class translates the voice commands
 * @author alex
 *
 */
public class VoiceController {
	private static VoiceController instance;
	private ChessController chessController;
	private Map<String,String> radioAlphabet;
	private final int SIZE=8;
	private int originX;
	private int originY;
	private int destX;
	private int destY;
	private int index;
	
	public VoiceController() {
		String letters[] = new String []{"alfa","alpha", "bravo", "charlie","delta","eco","echo","foxtrot","golf","hotel"};
		String radioWordTranslated="";
		
		this.index=this.originX=this.originY=this.destX=this.destY=-1;
		this.radioAlphabet=new HashMap();
		this.chessController=ChessController.getInstance();

		this.radioAlphabet.put("alfa", "1");
		this.radioAlphabet.put("alpha", "1");
		this.radioAlphabet.put("bravo", "2");
		this.radioAlphabet.put("charlie", "3");
		this.radioAlphabet.put("delta", "4");
		this.radioAlphabet.put("eco", "5");
		this.radioAlphabet.put("echo", "5");
		this.radioAlphabet.put("foxtrot", "6");
		this.radioAlphabet.put("golf", "7");
		this.radioAlphabet.put("hotel", "8");	
	}
	
	/**
	 * Singleton pattern
	 * @return
	 */
	public static VoiceController getInstance() {
		if(instance==null) {
			instance=new VoiceController();
		}
		return instance;
	}
	
	/**
	 * Parses strings which can contain a command
	 * @param word
	 */
	public String parse(String possibleCommand) {
		possibleCommand=possibleCommand.toLowerCase();
		
		String translation="";
		String words []=possibleCommand.split(" ");
		String command="notACommand";//Used only for testing
		if(findOriginX(words)) {
			if(findOriginY(words)) {
				if(findDestX(words)) {
					if(findDestY(words)) {
						this.chessController.move(this.originX, this.originY, this.destX, this.destY);
						command=this.originX+","+this.originY+","+ this.destX+","+ this.destY;
					}
				}
			}
		}
		return command;
	}
	
	/**
	 * Finds a word from the radio alphabet (alpha,bravo,charlie...) and translates it to
	 * a numeric coordinate
	 * @param words
	 * @return true if found, false otherwise
	 */
	public boolean findOriginX(String [] words) {
		boolean found=false;
		for(int i=0;i<words.length;i++){
			if(this.radioAlphabet.containsKey(words[i])) {
				this.originX=Integer.valueOf(this.radioAlphabet.get(words[i]));
				this.index= i+1;
				found=true;
				break;
			}
		}
		return found;
	}
	
	
	/**
	 * Checks if the next word is a number from 1 to 8, both included.
	 * a numeric coordinate
	 * @param words
	 * @return true if it is, false otherwise
	 */
	public boolean findOriginY(String [] words) {
		boolean found=false;
		if(words[this.index].matches("^([1-8])$")) {
			this.originY=Integer.valueOf(words[this.index]);
			found=true;
			this.index++;
		}else {
			found=false;
			this.originX=-1;
		}
		return found;
	}
	
	
	/**
	 * Finds a word from the radio alphabet (alpha,bravo,charlie...) and translates it to
	 * a numeric coordinate
	 * @param words
	 * @return true if found, false otherwise
	 */
	public boolean findDestX(String [] words) {
		boolean found=false;
		for(int i=this.index;i<words.length;i++){
			if(this.radioAlphabet.containsKey(words[i])) {
				this.destX=Integer.valueOf(this.radioAlphabet.get(words[i]));
				this.index= i+1;
				found=true;
				break;
			}
		}
		if(!found) {
			this.originX=-1;
			this.originY=-1;
		}
		return found;
	}
	
	/**
	 * Checks if the next word is a number from 1 to 8, both included.
	 * a numeric coordinate
	 * @param words
	 * @return true if it is, false otherwise
	 */
	public boolean findDestY(String [] words) {
		boolean found=false;
		if(words[this.index].matches("^([1-8])$")) {
			this.destY=Integer.valueOf(words[this.index]);
			found=true;
			this.index=-1;
		}else {
			found=false;
			this.originX=-1;
			this.originY=-1;
			this.destX=-1;
		}
		return found;
	}
	
	
	
	
	
	
	
}
