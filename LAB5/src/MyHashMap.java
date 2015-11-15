import java.util.Random;

public class MyHashMap implements MyMapADT {

	//Initial data length of 11 is used
	//Use `data.length` to get current length
	private static final int INITIAL_LENGTH = 11;
	
	
	private ListItem[] data;

	//Store the a, b, and p values of the hash functions
	private int HASH_A;
	private int HASH_B;
	private static final int HASH_P = 109345121;

	public MyHashMap() {
		Random rand = new Random();
		
		//We need [1,p-1] so I take [0, p-1], turn it to {[0, p-2] + 1}, or [1, p - 1]
		HASH_A = rand.nextInt(HASH_P - 1) + 1;
		//We need [0, p-1]
		HASH_B = rand.nextInt(HASH_P);

		data = new ListItem[INITIAL_LENGTH];
	}

	/**
	 * Get the index for the key
	 * @param k key to hash
	 * @return hash of key (index)
	 */
	private int hash(int k) {
		//Converting to a long to prevent issues
		//Then convert back to an int, because we don't actually have a long after it finishes
		return (int) (( (long) HASH_A * k + HASH_B) % MyHashMap.HASH_P) % data.length;
	}

	/**
	 * Get the load of the tree. This is the number of nodes divided by the length of the data list
	 * @return load of the tree
	 */
	private double load() {
		return (double)this.size() / data.length;
	}

	/**
	 * Re-randomize the hash numbers
	 * 
	 * *WARNING*
	 * If this method is called and the tree is not reconstructed immediately thereafter,
	 * the tree will become inaccessible except by manual traversal
	 */
	private void reroll() {
		//TODO: Make sure [0,a) not [.,.] or (.,.)
		Random rand = new Random();

		//We need [1,p-1] so I take [0, p-1], turn it to {[0, p-2] + 1}, or [1, p - 1]
		HASH_A = rand.nextInt(HASH_P - 1) + 1;
		//We need [0, p-1]
		HASH_B = rand.nextInt(HASH_P);
	}

	/**
	 * Reconstruct the seed and create new hash numbers
	 */
	private void rehash() {
		System.out.println(this.load());
		
		//Check the tree load is more than .5
		if (this.load() <= .5)
			return;
		
		//Create new hash numbers
		reroll();

		//Store the current data
		ListItem[] current = data;
		//Recreate data as a list twice as long
		data = new ListItem[current.length * 2];

		//Loop through the old data
		for (int i = 0; i < current.length; i++) {
			ListItem element = current[i];
			if (element == null)
				//If the entry is null, it has no information
				continue;
			
			do {
				//Reinsert the element
				this.insert(element.k, element.v, true);
				
				if (element.hasNext()) {
					element = element.next;
				}
			} while (element.hasNext());
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
			
			do {
				if (element.k == k) {
					element.v = v;
					return false;
				} else if (element.hasNext()) {
					element = element.next;
				}
			} while (element.hasNext());
			
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
			do {
				if (element.k == k) {
					return new RetVal(true, element.v);
				} else if (element.hasNext()) {
					element = element.next;
				}
			} while (element.hasNext());
		}
		return new RetVal(false, 0);
	}

	@Override
	public RetVal delete(int k) {
		int hashedKey = hash(k);
		if (data[hashedKey] != null) {
			ListItem element = data[hashedKey];
			do {
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
				}
			} while (element.hasNext());
		}
		return null;
	}

	@Override
	public int size() {
		//Start with a size of zero
		int size = 0;
		
		if (data != null) {
			//Loop through the items in data
			for (int i = 0; i < data.length; i++) {
				//Grab the item
				ListItem e = data[i];
				if (e != null) {
					do {
						size++;
						e = e.next;
					} while (e != null);
				}
			}
		}
		return size;
	}

	/**
	 * the first line displays the hash function (for example, as "h(k) = ((5k+3) mod 13) mod 11"),
	 * and each subsequent line is of the form "i: k1 k2 k3" where i is a slot number and k1, k2, k3
	 * are the keys in that slot. For compactness of display, include a slot only if it is nonempty.
	 */
	public String toString() {
		String out = this.size() + "\nh(k) = ((" + HASH_A + " * k + " + HASH_B + ") % " + HASH_P + ") % " + data.length + "\n";
		if (data != null) {
			for (int i = 0; i < data.length; i++) {
				ListItem e = data[i];
				if (e != null) {
					out += i + ":\t";
					out += "(" + e.k + ", " + e.v + "), ";
					while (e.hasNext()) {
						e = e.next;
						out += "(" + e.k + ", " + e.v + "), ";
					}
					out += "\n";
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
