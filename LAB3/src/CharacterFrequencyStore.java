
public class CharacterFrequencyStore {
	public int frequency;
	public Character character; //May be null
	
	public CharacterFrequencyStore(int f, char c) {
		frequency = f;
		character = c;
	}
	public CharacterFrequencyStore(int f) {
		frequency = f;
		character = null;
	}
	public String toString() {
		if (character == null) {
			return "" + frequency;
		} else {
			return "" + character + ":" + frequency;
		}
	}
}
