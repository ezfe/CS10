/**
 * RoadMap.java
 * Represents the map, data-wise
 * @author Ezekiel Elin
 */

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

import net.datastructures.AdjacencyMapGraph;
import net.datastructures.Edge;
import net.datastructures.Graph;
import net.datastructures.Vertex;

public class RoadMap {
	//Store the graph of the city in an adjacency map graph
	private Graph<City, Highway> mapGraph = new AdjacencyMapGraph<City, Highway>(false);
	//Boolean to track the measurement metric
	private boolean useDistance = true;

	public RoadMap(String cities, String links) throws Exception {
		createGraph(cities, links);
	}
	
	/**
	 * Create a graph from csv files 
	 * @param cities path to cities file
	 * @param links path to links file
	 * @throws Exception when stuff breaks
	 */
	private void createGraph(String cities, String links) throws Exception {
		//Make a new map to track names to vertices
		Map<String, Vertex<City>> vertexMap = new HashMap<String, Vertex<City>>();

		//Read the cities file
		BufferedReader citiesFile = null;
		try {
			citiesFile = new BufferedReader(new FileReader(cities));
		} catch (FileNotFoundException e1) {
			throw new Exception("Unable to find file " + cities);
		} catch (Exception e) {
			throw new Exception("An unexpected error occured processing the input");
		}
		String line = "";
		try {
			while ((line = citiesFile.readLine()) != null) {
				//Split the file by comma
				String[] parts = line.split(",");
				//And create a new city from thos eparts
				City c = new City(parts[0], new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
				//Insert it into the graph
				Vertex<City> v = mapGraph.insertVertex(c);
				//And insert it into the map
				vertexMap.put(c.getName(), v);
			}
			citiesFile.close();
		} catch (Exception e) {
			throw new Exception("An error occured reading the file");
		}

		//Read the links file
		BufferedReader linksFile = null;
		try {
			linksFile = new BufferedReader(new FileReader(links));
		} catch (FileNotFoundException e1) {
			throw new Exception("Unable to find file " + cities);
		} catch (Exception e) {
			throw new Exception("An unexpected error occured processing the input");
		}
		line = "";
		try {
			while ((line = linksFile.readLine()) != null) {
				//Split by comma
				String[] parts = line.split(",");
				//Grab the city names
				String cityAName = parts[0];
				String cityBName = parts[1];
				//Create a new highway
				Highway h = new Highway(Double.parseDouble(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), vertexMap.get(cityAName).getElement(), vertexMap.get(cityBName).getElement());

				//Link the two cities using the highway
				Vertex<City> cityAVertex = vertexMap.get(cityAName);
				Vertex<City> cityBVertex = vertexMap.get(cityBName);
				mapGraph.insertEdge(cityAVertex, cityBVertex, h);
			}
			linksFile.close();
		} catch (Exception e) {
			throw new Exception("An error occured reading the file");
		}
	}
	
	/**
	 * Get the city at a point
	 * @param p Point of interest
	 * @return City at the point
	 */
	public City cityAt(Point p) {
		//Iterate through the vertices
		Iterator<Vertex<City>> iter = mapGraph.vertices().iterator();
		while (iter.hasNext()) {
			City c = iter.next().getElement();
			//Ask each vertex if they're at the point
			//And return the vertex if it is
			if (c.isNear(p)) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Perform Dijkstra's algorithm (public facing)
	 * @param a Origin city
	 * @param b Destination city (can be equal to origin)
	 * @return Information gathered from the algorthm
	 */
	public DijkstraResults dijkstra(City a, City b) {
		//To store the vertices for a and b in
		Vertex<City> vA = null;
		Vertex<City> vB = null;
		
		//Iterate through the vertices
		Iterator<Vertex<City>> iter = mapGraph.vertices().iterator();
		while (iter.hasNext()) {
			//Grab the Vertex
			Vertex<City> temp = iter.next();
			//Check if it's == to a
			if (a == temp.getElement()) {
				//If so, store it in vA
				vA = temp;
			}
			//Check if it's == to b
			if (b == temp.getElement()) {
				//If so, store it in vB
				vB = temp;
			}
		}

		//Perform dijkstra (private)
		dijkstra(vA);

		//Make a new results box
		DijkstraResults box = new DijkstraResults();
		//Store the cities
		box.cityA = vA;
		box.cityB = vB;
		//Store the backpath
		box.predecessors = shortestPathPred;
		//Store the shortest path from vB to vA
		box.shortest = distances.get(vB);

		//Return the box
		return box;
	}

	//Instance variables to store data from dijkstra shortest path algorithm
	//Used in multiple methods, cleared() when algorithm is run 
	HashMap<Vertex<City>, Vertex<City>> shortestPathPred = new HashMap<Vertex<City>, Vertex<City>>();
	HashMap<Vertex<City>, Double> distances = new HashMap<Vertex<City>, Double>();

	/**
	 * Perform dijkstra algorithm
	 * @param a origin Vertex<City>
	 */
	private void dijkstra(Vertex<City> a) {
		//Reset our output maps
		shortestPathPred.clear();
		distances.clear();

		//Create a queue for the algortihm
		PriorityQueue<Vertex<City>> queue = new PriorityQueue<Vertex<City>>(new VertexComparator());

		//Iterate through the vertices
		Iterator<Vertex<City>> vertIter = mapGraph.vertices().iterator();
		while (vertIter.hasNext()) {
			Vertex<City> v = vertIter.next();
			//Add the vertice to the distances map, with ∞[Infinity] being the distance
			distances.put(v, Double.POSITIVE_INFINITY);
			//And add it to the queue
			queue.add(v);
		}		

		//We know the origin is at a distance of 0 from the origin, so we set it thusly
		distances.put(a, 0.0);

		//While the queue isn't empty, loop through
		while (!queue.isEmpty()) {
			//Grab the smallest item
			Vertex<City> u = queue.remove();

			//Iterate through the outgoing edges
			Iterator<Edge<Highway>> edgeIter = mapGraph.outgoingEdges(u).iterator();
			while (edgeIter.hasNext()) {
				//Grab the other end of the edge
				Vertex<City> v = mapGraph.opposite(u, edgeIter.next());
				//And relax it
				relax(u, v, queue);
			}
		}
	}

	/**
	 * Relax method of Dijkstra's shortest path algortihm
	 * @param u vertex u, smallest item in the queue
	 * @param v opposite of u, some othe item connected to u
	 * @param queue the queue
	 */
	private void relax(Vertex<City> u, Vertex<City> v, PriorityQueue<Vertex<City>> queue) {
		//Get the distance between u and v
		double distance;
		if (useDistance) {
			//If we're using distance, use the distance
			distance = mapGraph.getEdge(u, v).getElement().getDistance();
		} else {
			//Otherwise, the "distance" is actually minutes
			distance = mapGraph.getEdge(u, v).getElement().getTotalMinutes();
		}

		//If the distance of u + (u,v) is smaller than the distance of v
		if (distances.get(u) + distance < distances.get(v)) {
			//Update the distance of v to this new shorter distance
			distances.put(v, distances.get(u) + distance);
			//Shuffle the queue
			queue.remove(v);
			queue.add(v);
			//And update the predecssor to u, instead of whatever it was (or was't)
			shortestPathPred.put(v, u);
		}
	}

	/**
	 * Compares two Vertex<City> objects
	 * @author ezekielelin
	 */
	public class VertexComparator implements Comparator<Vertex<City>> {
		public int compare(Vertex<City> o1, Vertex<City> o2) {
			if (distances.get(o1) == distances.get(o2)) {
				//If the distanes are equal, return 0
				return 0;
			} else if (distances.get(o1) > distances.get(o2)) {
				//If #1 > #2, return 1
				return 1;
			} else {
				//Otherwise, return -1
				return -1;
			}
		}

	}

	/**
	 * Switch to distance measurements (miles)
	 */
	public void useDistance() {
		useDistance = true;
	}
	/**
	 * Switch to time measurements (minutes)
	 */
	public void useTime() {
		useDistance = false;
	}
	/**
	 * Check if we're using distance or time
	 * @return boolean, true: distance, false: time
	 */
	public boolean isUsingDistance() {
		return useDistance;
	}

	/**
	 * Get the highway that connects two vertices
	 * @param a first Vertex<City>
	 * @param b second Vertex<City>
	 * @return the connecting Edge<Highway>
	 */
	public Edge<Highway> highwayBetweenCities(Vertex<City> a, Vertex<City> b) {
		return mapGraph.getEdge(a, b);
	}
}
