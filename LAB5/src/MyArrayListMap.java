import java.util.ArrayList; //The only class I can think of that you will import
import java.util.Iterator; // from java is the ArrayList or the Iterator classes in java.util

/**
 * MyArrayListMap.java
 * @author Ezekiel Elin
 * Implements MyMapADT as an ArrayList
 */
public class MyArrayListMap implements MyMapADT {

	private ArrayList<ArrayListItem> data = new ArrayList<ArrayListItem>();

	/**
	 * See MyMapADT.java for details
	 */
	@Override
	public boolean insert(int k, int v) {
		//Try to delete the existing key
		RetVal deleteStatus = this.delete(k);

		//Add the key to the list
		ArrayListItem item = new ArrayListItem(k, v);
		data.add(item);
		
		//If the delete failed, then we've created a new entry, so return true
		//deleteStatus.found == true when there is an item already
		return !deleteStatus.found;
	}

	/**
	 * See MyMapADT.java for details
	 */
	@Override
	public RetVal find(int k) {
		//Get the iterator
		Iterator<ArrayListItem> iter = data.iterator();
		while (iter.hasNext()) {
			ArrayListItem next = iter.next();
			if (next.k == k)
				//If the keys match, then
				//return the value
				return new RetVal(true, next.v);	
		}

		//Return false, we didn't find anything
		return new RetVal(false, 0);
	}

	/**
	 * See MyMapADT.java for details
	 */
	@Override
	public RetVal delete(int k) {
		//Get the iterator
		Iterator<ArrayListItem> iter = data.iterator();
		while (iter.hasNext()) {
			ArrayListItem next = iter.next();
			if (next.k == k) {
				//If the keys match, remove it
				iter.remove();
				//And return true and the value we deleted
				return new RetVal(true, next.v);
			}
		}
		//We didn't delete anything, return false
		return new RetVal(false, 0);
	}


	/**
	 * See MyMapADT.java for details
	 */
	@Override
	public int size() {
		//Return the size of the ArrayList
		return data.size();
	}

	/**
	 * See MyMapADT.java for details
	 */
	@Override
	public String toString() {		
		String ret = "";
		Iterator<ArrayListItem> iter = data.iterator();
		while (iter.hasNext()) {
			ret += iter.next().k;
			if (iter.hasNext()) {
				ret += ", ";
			}
		}
		return ret;
	}

	/**
	 * Represents a key:value pair stored in the ArrayList
	 * @author Ezekiel Elin
	 */
	private class ArrayListItem {
		private int k;
		private int v;
		public ArrayListItem(int k, int v) {
			this.k = k;
			this.v = v;
		}
	}
}
