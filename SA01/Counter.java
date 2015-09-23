import java.text.DecimalFormat;

public class Counter {
	private int val;
	private int limit;

	public Counter(int v, int l) {
		limit = l;
		if (v < limit && v > 0) {
			val = v;
		}
	}

	public Counter(int l) {
		limit = l;
		val = 0;
	}

	public boolean tick() {
		val++;
		if (val >= limit) {
			val = 0;
			return true;
		}
		return false;
	}

	public int getVal() {
		return val;
	}

	public boolean setVal(int v) {
		if (v < limit && v > 0) {
			val = v;
			return true;
		}
		return false;
	}

	public String toString() {
		DecimalFormat f = new DecimalFormat("00");
		return f.format(this.val);
	}
}
