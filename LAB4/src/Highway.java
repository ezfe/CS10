import java.awt.Graphics;

/**
 * Highway.java
 * Manages a highway between two cities (usually contained in an Edge)
 * @author Ezekiel Elin
 */
public class Highway {
	private Double distance; //Highway distance
	private int hours; //Highway time hours
	private int minutes; //Highway time minutes
	private City a; //One city
	private City b; //The other city
	
	public Highway(Double distance, int hours, int minutes, City a, City b) {
		this.distance = distance;
		this.hours = hours;
		this.minutes = minutes;
		this.a = a;
		this.b = b;
	}

	/**
	 * Get the distance
	 * @return distance of the highway
	 */
	public Double getDistance() {
		return distance;
	}

	/**
	 * Set the distance
	 * @param distance new distance of the highway
	 */
	public void setDistance(Double distance) {
		this.distance = distance;
	}

	/**
	 * Get the time (hours)
	 * @return time in hours
	 */
	public int getHours() {
		return hours;
	}

	/**
	 * Set the time (hours)
	 * @param hours new hours
	 */
	public void setHours(int hours) {
		this.hours = hours;
	}

	/**
	 * Get the time (minutes)
	 * @return time in minutes
	 */
	public int getMinutes() {
		return minutes;
	}

	/**
	 * Set the time (minutes)
	 * @param minutes new minutes
	 */
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
	/**
	 * Get the total minutes
	 * @return returns the sum of hours * 60 + minutes
	 */
	public int getTotalMinutes() {
		return (hours*60)+minutes;
	}
	
	/**
	 * Draw this highway on a page
	 * @param page on which to draw
	 */
	public void draw(Graphics page) {
		page.drawLine(a.getLocation().x, a.getLocation().y, b.getLocation().x, b.getLocation().y);
	}
}
