package chess;

/**
 * This class receives orders 
 * @author alex
 *
 */
public class ChessController {
	
	private static ChessController instance;
	
	/**
	 * Singleton
	 * @return a unique instance of ChessController
	 */
	public static  ChessController getController() {
		if(instance==null) {
			instance=new ChessController();
		}
		
		return instance;
	}
	
	/**
	 * Moves a piece from (fromX, fromY) to (toX, toY)
	 * @param fromX x origin coordinate
	 * @param fromY y origin coordinate
	 * @param toX x destination coordinate
	 * @param toY y destination coordinate
	 */
	public void move(int fromX, int fromY, int toX, int toY) {
		
	}
}
