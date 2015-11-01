import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import net.datastructures.AdjacencyMapGraph;
import net.datastructures.Graph;

public class RoadMap {

	public RoadMap(String cities, String links) {
		Graph<City, Highway> mapGraph = new AdjacencyMapGraph<City, Highway>(false);
		
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
				System.out.println(c);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
