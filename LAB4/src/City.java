/**
 * City.java
 */

import java.awt.Point;

public class City {
	private String name;
	private Point location;
	private final static int DISTANCE_TOLERANCE = 10;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}
	
	public City (String name, Point location) {
		this.name = name;
		this.location = location;
	}
	
	public String toString() {
		return this.name;
	}
	
	public boolean isNear(Point p) {
		return p.distance(location) < DISTANCE_TOLERANCE;
	}
}
