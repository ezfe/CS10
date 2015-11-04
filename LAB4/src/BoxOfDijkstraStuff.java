import java.util.HashMap;

import net.datastructures.Vertex;

/**
 * @author ezekielelin
 *
 */
public class BoxOfDijkstraStuff {
	public Vertex<City> cityA = null;
	public Vertex<City> cityB = null;
	public HashMap<Vertex<City>, Vertex<City>> predecessors = null;
	public double shortest = Double.POSITIVE_INFINITY;
}
