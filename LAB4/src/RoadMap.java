import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

import net.datastructures.AdjacencyMapGraph;
import net.datastructures.Edge;
import net.datastructures.Graph;
import net.datastructures.Vertex;

public class RoadMap {

	Graph<City, Highway> mapGraph = new AdjacencyMapGraph<City, Highway>(false);

	public RoadMap(String cities, String links) {
		Map<String, Vertex<City>> hashMap = new HashMap<String, Vertex<City>>();

		BufferedReader citiesFile = null;
		try {
			citiesFile = new BufferedReader(new FileReader(cities));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String line = "";
		try {
			while ((line = citiesFile.readLine()) != null) {
				String[] parts = line.split(",");
				City c = new City(parts[0], new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
				Vertex<City> v = mapGraph.insertVertex(c);
				hashMap.put(c.getName(), v);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BufferedReader linksFile = null;
		try {
			linksFile = new BufferedReader(new FileReader(links));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		line = "";
		try {
			while ((line = linksFile.readLine()) != null) {
				String[] parts = line.split(",");
				String cityAName = parts[0];
				String cityBName = parts[1];
				Highway h = new Highway(Double.parseDouble(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));

				Vertex<City> cityAVertex = hashMap.get(cityAName);
				Vertex<City> cityBVertex = hashMap.get(cityBName);

				mapGraph.insertEdge(cityAVertex, cityBVertex, h);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(mapGraph);
	}

	public City cityAt(Point p) {
		Iterator<Vertex<City>> iter = mapGraph.vertices().iterator();
		while (iter.hasNext()) {
			City c = iter.next().getElement();
			if (c.isNear(p)) {
				return c;
			}
		}
		return null;
	}

	public void dijkstra(City a, City b) {
		Iterator<Vertex<City>> iter = mapGraph.vertices().iterator();
		boolean found = false;
		while (iter.hasNext()) {
			Vertex<City> vA = iter.next();
			if (a == vA.getElement()) {
				found = true;
				dijkstra(vA);
			}
		}
		if (!found) {
			System.err.println("Couldn't find " + a);
			return;
		}
		System.out.println(shortestPathPred);
	}

	HashMap<Vertex<City>, Vertex<City>> shortestPathPred = new HashMap<Vertex<City>, Vertex<City>>();
	HashMap<Vertex<City>, Double> distances = new HashMap<Vertex<City>, Double>();

	private void dijkstra(Vertex<City> a) {
		shortestPathPred.clear();
		distances.clear();

		PriorityQueue<Vertex<City>> queue = new PriorityQueue<Vertex<City>>();
		Iterator<Vertex<City>> vertIter = mapGraph.vertices().iterator();
		while (vertIter.hasNext()) {
			Vertex<City> v = vertIter.next();
			queue.add(v);
		}

		distances.put(a, 0.0);

		while (!queue.isEmpty()) {
			Vertex<City> u = queue.remove();

			Iterator<Edge<Highway>> edgeIter = mapGraph.outgoingEdges(u).iterator();
			while (edgeIter.hasNext()) {
				Vertex<City> v = mapGraph.opposite(u, edgeIter.next());
				relax(u, v);
			}
		}
	}

	private void relax(Vertex<City> u, Vertex<City> v) {
		// TODO Auto-generated method stub
		double distance = mapGraph.getEdge(u, v).getElement().getDistance();
		if (distances.get(u) + distance < distances.get(v)) {
			distances.put(v, distances.get(u)+ distance);
			shortestPathPred.put(v, u);
		}
	}
}
