import java.text.DecimalFormat;

public class DigitalClock {
	Counter hour = new Counter(0, 24);
	Counter minute = new Counter(0, 60);
	Counter second = new Counter(0, 60);

	public DigitalClock(int h, int m, int s) {
		set(h, m, s);

	}

	public void set(int h, int m, int s) {
		hour.setVal(h);
		minute.setVal(m);
		second.setVal(s);
	}

	public void tick() {
		if (second.tick()) {
			if (minute.tick()) {
				hour.tick();
			}
		}
	}

	public void run(int h, int m, int s) {
		do {
			this.tick();
			System.out.println(this);
		} while (!(hour.getVal() == h && minute.getVal() == m && second.getVal() == s));
	}

	public void run() {
		this.run(0, 0, 0);
	}

	public String toString() {
		return  hour + ":" + minute + ":" + second;
	}
}
