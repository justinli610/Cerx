package field;

public class CharacterConverter {
	/**
	 * No need to create objects of this class.
	 */
	private CharacterConverter() {}
	
	public static CharacterModel getCharacter(String name) {
		CharacterModel character;
		
		character = new CharacterModel("Characters\\" + name);
		
		return character;
	}
}
