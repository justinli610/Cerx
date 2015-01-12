package fieldComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import field.GameState;

/**
 * 
 * Handles saving and loading of games.
 *
 */
public class GameIO {
	/**
	 * Cannot be instantiated.
	 */
	private GameIO() {}
	
	public static void save() {
		try {
	        File saveFile = new File("Save\\SaveGame.sav");
			
			FileOutputStream fileOut = new FileOutputStream(saveFile);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(GameState.getGameState());
			out.close();
			fileOut.close();
		} catch(IOException e) {
	          e.printStackTrace();
	      }
	}
	
	public static GameState load() {
		GameState state;
		
		try {
	         FileInputStream fileIn = new FileInputStream("Save\\SaveGame.sav");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         state = (GameState) in.readObject();
	         in.close();
	         fileIn.close();
	         
	         return state;
	      } catch(IOException e) {
	          e.printStackTrace();
	      } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
