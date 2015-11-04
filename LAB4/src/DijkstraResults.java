import java.util.HashMap;

import net.datastructures.Vertex;

/**
 * DijkstraResults.java
 * Holds the results of the disjkstra algorithm
 * @author Ezekiel Elin
 */
public class DijkstraResults {
	//Origin city
	public Vertex<City> cityA = null;
	//Destination city (may be == to origin)
	public Vertex<City> cityB = null;
	//Map of predecessors
	public HashMap<Vertex<City>, Vertex<City>> predecessors = null;
	//Shortest path
	public double shortest = Double.POSITIVE_INFINITY;
}
