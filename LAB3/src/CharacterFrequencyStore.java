
public class CharacterFrequencyStore implements Comparable<Object> {
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

	public int compareTo(Object o) {
		CharacterFrequencyStore other = (CharacterFrequencyStore)o;

		if (other.frequency == this.frequency) {
			if		(other.character == null && this.character == null)	return 0;
			else if (other.character == this.character)					return 0;
			else if (other.character > this.character)					return 1;
			else														return -1;
		} else {
			return -1;
		}
	}

}
