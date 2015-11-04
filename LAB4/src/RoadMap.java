import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import net.datastructures.AdjacencyMapGraph;
import net.datastructures.Edge;
import net.datastructures.Graph;
import net.datastructures.Vertex;

public class RoadMap {

	private Graph<City, Highway> mapGraph = new AdjacencyMapGraph<City, Highway>(false);
	private boolean useDistance = true;

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
				Highway h = new Highway(Double.parseDouble(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), hashMap.get(cityAName).getElement(), hashMap.get(cityBName).getElement());

				Vertex<City> cityAVertex = hashMap.get(cityAName);
				Vertex<City> cityBVertex = hashMap.get(cityBName);

				mapGraph.insertEdge(cityAVertex, cityBVertex, h);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public BoxOfDijkstraStuff dijkstra(City a, City b) {
		Iterator<Vertex<City>> iter = mapGraph.vertices().iterator();
		Vertex<City> vA = null;
		Vertex<City> vB = null;
		boolean found = false;
		while (iter.hasNext()) {
			Vertex<City> temp = iter.next();
			if (a == temp.getElement()) {
				vA = temp;
			}
			if (b == temp.getElement()) {
				vB = temp;
			}
		}

		dijkstra(vA);

		BoxOfDijkstraStuff box = new BoxOfDijkstraStuff();
		box.cityA = vA;
		box.cityB = vB;
		box.predecessors = shortestPathPred;
		box.shortest = distances.get(vB);

		return box;
	}

	HashMap<Vertex<City>, Vertex<City>> shortestPathPred = new HashMap<Vertex<City>, Vertex<City>>();
	HashMap<Vertex<City>, Double> distances = new HashMap<Vertex<City>, Double>();

	private void dijkstra(Vertex<City> a) {
		shortestPathPred.clear();
		distances.clear();

		PriorityQueue<Vertex<City>> queue = new PriorityQueue<Vertex<City>>(new VertexComparator());
		Iterator<Vertex<City>> vertIter = mapGraph.vertices().iterator();
		while (vertIter.hasNext()) {
			Vertex<City> v = vertIter.next();
			distances.put(v, Double.POSITIVE_INFINITY);
			queue.add(v);
		}		

		distances.put(a, 0.0);
//		int c = 0;
		
		while (!queue.isEmpty()) {
			Vertex<City> u = queue.remove();

			Iterator<Edge<Highway>> edgeIter = mapGraph.outgoingEdges(u).iterator();
			while (edgeIter.hasNext()) {
				Vertex<City> v = mapGraph.opposite(u, edgeIter.next());
				relax(u, v, queue);
			}
		}
//		System.out.println(c);
	}

	private void relax(Vertex<City> u, Vertex<City> v, PriorityQueue<Vertex<City>> queue) {
		// TODO Auto-generated method stub
//		System.out.println(u.getElement().getName() + " to " + v.getElement().getName());
		
		double distance;
		if (useDistance) {
			distance = mapGraph.getEdge(u, v).getElement().getDistance();
		} else {
			distance = mapGraph.getEdge(u, v).getElement().getTotalMinutes();
		}


		if (distances.get(u) + distance < distances.get(v)) {
			distances.put(v, distances.get(u) + distance);
			queue.remove(v);
			queue.add(v);
			shortestPathPred.put(v, u);
		}
	}

	public class VertexComparator implements Comparator<Vertex<City>> {
		public int compare(Vertex<City> o1, Vertex<City> o2) {
			if (distances.get(o1) == distances.get(o2)) return 0;
			if (distances.get(o1) > distances.get(o2)) {
				return 1;
			} else if (distances.get(o1) < distances.get(o2)) {
				return -1;
			}
			return 0;
		}

	}

	public void useDistance() {
		useDistance = true;
	}
	public void useTime() {
		useDistance = false;
	}
	public boolean isUsingDistance() {
		return useDistance;
	}

	public Edge<Highway> highwayBetweenCities(Vertex<City> a, Vertex<City> b) {
		return mapGraph.getEdge(a, b);
	}
}
