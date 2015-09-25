/**
 * ContainsPointDriver.java
 *
 * For CS 10 Short Assignment 3.
 *
 * @author Ezekiel Elin
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ContainsPointDriver {
  private static final int MAX_X = 200, MAX_Y = 150;  // max coordinate values
  private static final double MAX_RADIUS = 100.0;     // size of largest circle

  public static void main(String[] args) {
    ArrayList<GeomShape> shapes = new ArrayList<GeomShape>();  // a polymorphic ArrayList
    Random generator = new Random();  // random number generator

    final int INIT_LIST_SIZE = 20;
    // Randomly add some shapes to the ArrayList.
    for (int i = 0; i < INIT_LIST_SIZE; i++) {
      shapes.add(getRandomShape(generator));
    }

    // See what we've got.
    System.out.println("After adding " + INIT_LIST_SIZE + " shapes:");
    for (int i = 0; i < shapes.size(); i++)
      System.out.println(shapes.get(i));

    // Look for objects containing points input from the console.
    // For each point, remove the first object containing it.
    // YOU FILL THIS IN.

    Scanner s = new Scanner(System.in);

    while (true) {
        System.out.print("Enter x y coordinates, seperated by a space: "); //Prompt user
        int x = s.nextInt(); //Get x
        int y = s.nextInt(); //Get y

        int length = shapes.size(); //Length of array list
        boolean found = false; //So we know we found something

        for (int i = 0; i < length; i++) {
            GeomShape item = shapes.get(i);
            if (item.containsPoint(x, y)) {
                System.out.println("Found object at index " + i + ": " + item);

                shapes.remove(i);

                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No object contains point: (" + x + "," + y + ")");
        }
    }
   }

  /**
   * Create a randomly generated shape object.
   *
   * @param generator
   * @return an object that implements the GeomShape interface
   */
  public static GeomShape getRandomShape(Random generator) {
    // Randomly pick a Circle or Rectangle to create.
    if (generator.nextInt(2) == 0)
      // Chose Circle.  Randomly choose a center and radius.
      return new Circle(generator.nextInt(MAX_X), generator.nextInt(MAX_Y),
          generator.nextDouble() * MAX_RADIUS);
    else {
      // Chose Rectangle.  Randomly choose its corners.
      int x1 = generator.nextInt(MAX_X);
      int x2 = generator.nextInt(MAX_X);
      int y1 = generator.nextInt(MAX_Y);
      int y2 = generator.nextInt(MAX_Y);
      return new Rectangle(Math.min(x1, x2), Math.min(y1, y2),
          Math.abs(x1 - x2), Math.abs(y1 - y2));
    }
  }
}
