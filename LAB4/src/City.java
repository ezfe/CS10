/**
 * City.java
 */

import java.awt.Point;

public class City {
	String name;
	Point location;
	
	public City (String name, Point location) {
		this.name = name;
		this.location = location;
	}
	
	public String toString() {
		return this.name;
	}
}
