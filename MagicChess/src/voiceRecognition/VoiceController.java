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
	private Map<String,String> dictionary;
	private ChessController chessController;
	private int originX;
	private int originY;
	private int destX;
	private int destY;
	private boolean originSet;
	
	public VoiceController() {
		String letters[] = new String []{"alfa", "bravo", "charli","delta","eco","foxtrot","golf","hotel"};
		String numbers[] = new String [] {"uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho"};
		this.dictionary=new HashMap();
		this.chessController=ChessController.getInstance();
		this.originSet=false;
		
		for(int i=0;i<letters.length;i++) {
			for(int j=0;j<numbers.length;j++) {
				this.dictionary.put(letters[i]+" "+numbers[j], (i+1) +","+ (j+1) );
			}
		}
		
		/*this.dictionary.put("alfa", "1");
		this.dictionary.put("bravo", "2");
		this.dictionary.put("charli", "3");
		this.dictionary.put("delta", "4");
		this.dictionary.put("eco", "5");
		this.dictionary.put("foxtrot", "6");
		this.dictionary.put("golf", "7");
		this.dictionary.put("hotel", "8");
		
		this.dictionary.put("uno", "1");
		this.dictionary.put("dos", "2");
		this.dictionary.put("tres", "3");
		this.dictionary.put("cuatro", "4");
		this.dictionary.put("cinco", "5");
		this.dictionary.put("seis", "6");
		this.dictionary.put("siete", "7");
		this.dictionary.put("ocho", "8");*/
		
		
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
	public void parse(String possibleCommand) {
		possibleCommand=possibleCommand.toLowerCase();
		String translation="";
		if(this.dictionary.containsKey(possibleCommand)) {
			translation=this.dictionary.get(possibleCommand);
			if(!originSet) {
				this.originX=Integer.valueOf(translation.split(",")[0]);
				this.originY=Integer.valueOf(translation.split(",")[1]);
				this.originSet=true;
			}else {
				this.destX=Integer.valueOf(translation.split(",")[0]);
				this.destY=Integer.valueOf(translation.split(",")[1]);
				this.chessController.move(this.originX,this.originY,this.destX,this.destY);
				this.originSet=false;
			}
		}
	}
	
	
	/**
	 * Translates from spoken language to numeric language where boxes are identified by 
	 * coordinates.
	 * @param SpokenWord
	 * @return
	 */
	public String translate(String SpokenWord) {
		String translation=this.dictionary.get(SpokenWord);
		return translation;
	}
	
	
}
