/**
 * City.java
 * Manages a City (usually contained in a Vertex)
 * @author Ezekiel Elin
 */

import java.awt.Point;

public class City {
	private String name; //Name of the city
	private Point location; //Location of the city
	private final static int DISTANCE_TOLERANCE = 10; //Tolerance for clicks
	
	public City (String name, Point location) {
		this.name = name;
		this.location = location;
	}
	
	/**
	 * Get the name of the city
	 * @return city's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the city
	 * @param name to set the city
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the location of the city
	 * @return Point location
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * Set the loaction of the city
	 * @param location Point for the city
	 */
	public void setLocation(Point location) {
		this.location = location;
	}
		
	/**
	 * Get the city as a string
	 */
	public String toString() {
		return this.name;
	}
	
	/**
	 * Check if the city is near a point
	 * @param p Point to check
	 * @return boolean if it's near enough
	 */
	public boolean isNear(Point p) {
		return p.distance(location) < DISTANCE_TOLERANCE;
	}
}
