import java.awt.Graphics;

/**
 * Highway.java
 * @author Ezekiel Elin
 */
public class Highway {
	private Double distance;
	private int hours;
	private int minutes;
	private City a;
	private City b;
	
	public Highway(Double distance, int hours, int minutes, City a, City b) {
		this.setDistance(distance);
		this.setHours(hours);
		this.setMinutes(minutes);
		this.a = a;
		this.b = b;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
	public int getTotalMinutes() {
		return (hours*60)+minutes;
	}
	
	public void draw(Graphics page) {
		page.drawLine(a.getLocation().x, a.getLocation().y, b.getLocation().x, b.getLocation().y);
	}
}
