package sa02;

/**
 *  Circle.java
 *  
 *  Simple circle object
 *  Based on a C++ demo by THC
 *  
 *  @author Scot Drysdale on 4/5/00, modified 1/7/2012
 */

public class Circle implements GeomShape {
  private int myX, myY;      // x and y coordinates of Circle center
  private double myRadius;   // Circle radius

  /**
   * Create a Circle object with center (x, y) and radius r
   * @param x the x coordinate of the center of the circle
   * @param y the y coordinate of the center of the circle
   * @param r the radius of the circle
   */
  public Circle(int x, int y, double r) {
    myX = x;
    myY = y;
    myRadius = r;
  }

  /**
   * Moves Circle by deltaX, deltaY.
   * 
   * @param deltaX the amount to move in the x direction
   * @param deltaY the amount to move in the y direction
   */
  public void move(int deltaX, int deltaY) {
    myX += deltaX;
    myY += deltaY;
  }

  /**
   * Scales the circle by factor
   * 
   * @param factor the scaling factor
   */
  public void scale(double factor) {
    myRadius = factor * myRadius;
  }

  /**
   * @return the area of the circle
   */
  public double areaOf() {
    return Math.PI * myRadius * myRadius;
  }
  
  /**
   * @return A string representation of the circle
   */
  public String toString() {
    return "Circle center: (" + myX + "," + myY + ")  radius: " + myRadius;
  }
}
