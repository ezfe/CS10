import java.util.ArrayList; //The only class I can think of that you will import
import java.util.Iterator; // from java is the ArrayList or the Iterator classes in java.util

/**
 * MyArrayListMap.java
 * @author Ezekiel Elin
 * Implements MyMapADT as an ArrayList
 */
public class MyArrayListMap implements MyMapADT {

	private ArrayList<ArrayListItem> data = new ArrayList<ArrayListItem>();

	@Override
	public boolean insert(int k, int v) {
		RetVal deleteStatus = this.delete(k);

		ArrayListItem item = new ArrayListItem(k, v);
		data.add(item);

		return !deleteStatus.found; //deleteStatus.found == true when there is an item already
	}

	@Override
	public RetVal find(int k) {
		Iterator<ArrayListItem> iter = data.iterator();
		while (iter.hasNext()) {
			ArrayListItem next = iter.next();
			if (next.k == k)
				return new RetVal(true, next.v);	
		}

		return new RetVal(false, 0);
	}

	@Override
	public RetVal delete(int k) {
		Iterator<ArrayListItem> iter = data.iterator();
		while (iter.hasNext()) {
			ArrayListItem next = iter.next();
			if (next.k == k) {
				iter.remove();
				return new RetVal(true, next.v);
			}
		}
		return new RetVal(false, 0);
	}

	@Override
	public int size() {
		return data.size();
	}

	@Override
	public String toString() {
		//TODO get rid of this crap
//		String ret = "{\n    ";
//		Iterator<ArrayListItem> iter = data.iterator();
//		while (iter.hasNext()) {
//			ArrayListItem next = iter.next();
//			ret += next.k + ": " + next.v;
//			if (iter.hasNext())
//				ret += ",\n    ";
//		}
//		ret += "\n}";
//		return ret;
		
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
	 * ArrayListItem (class)
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
