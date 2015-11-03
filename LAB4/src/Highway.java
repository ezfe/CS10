/**
 * Highway.java
 * @author Ezekiel Elin
 */
public class Highway {
	private Double distance;
	private int hours;
	private int minutes;
	
	public Highway(Double distance, int hours, int minutes) {
		this.setDistance(distance);
		this.setHours(hours);
		this.setMinutes(minutes);
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
}
