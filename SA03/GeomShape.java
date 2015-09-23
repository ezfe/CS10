/**
 * GeomShape.java
 * 
 * Declares an interface for a geometric shape.
 * 
 * @author Scot Drysdale on 4/9/00. Modified 1/7/2012
 */

public interface GeomShape {
  
  /**
   * Moves object by deltaX, deltaY.
   * 
   * @param deltaX the amount to move in the x direction
   * @param deltaY the amount to move in the y direction
   */
  public void move(int deltaX, int deltaY);
  
  /**
   * Scales the object by factor
   * 
   * @param factor the scaling factor
   */
  public void scale(double factor);
  
  /**
   * @return the area of the object
   */
  public double areaOf();
  
  /**
   * @return whether the object contains a point
   */
  public boolean containsPoint(int x, int y);
}
  