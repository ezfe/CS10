import java.util.Random;

public class MyHashMap implements MyMapADT {

	private static final int INITIAL_LENGTH = 11;
	private ListItem[] data;

	private int HASH_A;
	private int HASH_B;
	private static final int HASH_P = 109345121;

	public MyHashMap() {
		super();

		//TODO: Make sure [0,a) not [.,.] or (.,.)
		Random rand = new Random();

		//We need [1,p-1] so I take [0, p-1], turn it to {[0, p-2] + 1}, or [1, p - 1]
		HASH_A = rand.nextInt(HASH_P - 1) + 1;
		//We need [0, p-1]
		HASH_B = rand.nextInt(HASH_P);

		data = new ListItem[INITIAL_LENGTH];
	}

	private int hash(int k) {
		//Converting to a long to prevent issues
		//Then convert back to an int, because we don't actually have a long after it finishes
		return (int) (( (long) HASH_A * k + HASH_B) % MyHashMap.HASH_P) % data.length;
	}

	private double load() {
		return (double)this.size() / data.length;
	}

	private void reroll() {
		//TODO: Make sure [0,a) not [.,.] or (.,.)
		Random rand = new Random();

		//We need [1,p-1] so I take [0, p-1], turn it to {[0, p-2] + 1}, or [1, p - 1]
		HASH_A = rand.nextInt(HASH_P - 1) + 1;
		//We need [0, p-1]
		HASH_B = rand.nextInt(HASH_P);
	}

	private void rehash() {		
		if (this.load() <= .5)
			return;

		reroll();

		ListItem[] current = data;
		data = new ListItem[current.length * 2];

		for (int i = 0; i < current.length; i++) {
			ListItem element = current[i];
			if (element == null)
				continue;
			while (true) {
				this.insert(element.k, element.v, true);
				if (element.hasNext()) {
					element = element.next;
				} else {
					/*
					 * Yes I know I'm not "supposed" to use while(true) however I believe
					 * it is the best impelemention. The issue is that in a do{}while loop
					 * the while condition must be true at the end, not the beginning. Because
					 * of this, setting element to element.next can make the second while condition false, causing issues.
					 * 
					 * In constrast, in a while{} loop, the first element wouldn't be properly scanned because the while loop
					 * checks before starting its work
					 */
					break;
				}
			}
		}
	}

	@Override
	public boolean insert(int k, int v) {
		return this.insert(k, v, false);
	}

	public boolean insert(int k, int v, boolean origin_hash) {
		if (!origin_hash)
			this.rehash();

		int hashedKey = hash(k);
		if (data[hashedKey] != null) {
			ListItem element = data[hashedKey];
			while (true) {
				if (element.k == k) {
					element.v = v;
					return false;
				} else if (element.hasNext()) {
					element = element.next;
				} else {
					/*
					 * See my comments in delete() about using while(true) loops
					 */
					break;
				}
			}
			ListItem insert = new ListItem(k, v);
			insert.next = data[hashedKey];
			data[hashedKey].previous = insert;

			data[hashedKey] = insert;
			return true;
		} else {
			data[hashedKey] = new ListItem(k, v);
			return true;
		}
	}

	@Override
	public RetVal find(int k) {
		int hashedKey = hash(k);
		if (data[hashedKey] != null) {
			ListItem element = data[hashedKey];
			while (true) {
				if (element.k == k) {
					return new RetVal(true, element.v);
				} else if (element.hasNext()) {
					element = element.next;
				} else {
					/*
					 * See my comments in delete() about using a while(true) loop
					 */
					break;
				}
			}
		}
		return new RetVal(false, 0);
	}

	@Override
	public RetVal delete(int k) {
		int hashedKey = hash(k);
		if (data[hashedKey] != null) {
			ListItem element = data[hashedKey];
			while (true) {
				if (element.k == k) {
					if (element.isFirst()) {
						//If the element is the first in the list, delete it and make the next element the first
						//Also make sure the next element points back to null
						if (element.hasNext()) {
							element.next.previous = null;
						}
						data[hashedKey] = element.next;
					} else {
						//Link the next one to the previous one
						if (element.hasNext()) {
							element.next.previous = element.previous;
						}
						//And link the previous one to the next one;
						element.previous = element.next;
					}
					break;
				} else if (element.hasNext()) {
					element = element.next;
				} else {
					/*
					 * Yes I know I'm not "supposed" to use while(true) however I believe
					 * it is the best impelemention. The issue is that in a do{}while loop
					 * the while condition must be true at the end, not the beginning. Because
					 * of this, setting element to element.next can make the second while condition false, causing issues.
					 * 
					 * In constrast, in a while{} loop, the first element wouldn't be properly scanned because the while loop
					 * checks before starting its work
					 */
					break;
				}
			}
		}
		return null;
	}

	@Override
	public int size() {
		int size = 0;
		if (data != null) {
			for (int i = 0; i < data.length; i++) {
				ListItem e = data[i];
				if (e != null) {
					size++;
					while (e.hasNext()) {
						e = e.next;
						size++;
					}
				}
			}
		}
		return size;
	}

	public String toString() {
		String out = "";
		if (data != null) {
			for (int i = 0; i < data.length; i++) {
				ListItem e = data[i];
				if (e != null) {
					out += e.k + ": " + e.v + ", ";
					while (e.hasNext()) {
						e = e.next;
						out += e.k + ": " + e.v + ", ";
					}
				}
			}
		}
		return out;
	}

	/**
	 * ListItem (class)
	 * @author Ezekiel Elin
	 * Used for Hash implementation
	 */
	private class ListItem {
		protected int k;
		protected int v;

		protected ListItem next;
		protected ListItem previous;

		public ListItem(int k, int v) {
			this.k = k;
			this.v = v;
			this.next = null;
			this.previous = null;
		}

		public boolean hasNext() {
			return this.next != null;
		}
		public boolean isFirst() {
			return this.previous == null;
		}
	}
}
