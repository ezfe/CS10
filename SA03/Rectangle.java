/**
 *  Rectangle.java
 *
 *  Simple rectangle object
 *  Based on a C++ demo by THC
 *
 *  @author Scot Drysdale on 4/5/00, modified 1/7/2012
 */
public class Rectangle implements GeomShape {
  private int myX, myY;             // x and y coords of Rect's upper left corner
  private double myWidth, myHeight; // Rect's width and height

  /**
   * Create a rectangle object with upper left corner (x, y) and the
   * given width and height
   * @param x the x coordinate of the upper left corner of the Rectangle
   * @param y the y coordinate of the upper left corner of the Rectangle
   * @param width the width of the rectangle
   * @param heigh the height of the rectangle
   */
  public Rectangle(int x, int y, double width, double height) {
    myX = x;
    myY = y;
    myWidth = width;
    myHeight = height;
  }

  /**
   * @return the area of the rectangle
   */
  public double areaOf() {
    return myWidth * myHeight;
  }

  /**
   * Moves Rectangle by deltaX, deltaY.
   *
   * @param deltaX the amount to move in the x direction
   * @param deltaY the amount to move in the y direction
   */
  public void move(int deltaX, int deltaY) {
    myX += deltaX;
    myY += deltaY;
  }

  /**
   * Scales the Rectangle by factor
   *
   * @param factor the scaling factor
   */
  public void scale(double factor) {
    myWidth *= factor;
    myHeight *= factor;
  }

  /**
   * Flips the Rectangle (swaps width and height).
   * Note that this is NOT required by the GeomShape interface.
   */
  public void flip() {
    double temp = myWidth;
    myWidth = myHeight;
    myHeight = temp;
  }

  /**
   * @return A string representation of the rectangle
   */
  public String toString() {
    return "Rectangle upper left corner: (" + myX + "," + myY + ")  width: " + myWidth
  			   + " height: " + myHeight;
  }

  public boolean containsPoint(int x, int y) {
      //Check if x is between the leftmost and rightmost side, and y is between the uppermost and lowermost side
      if (myX <= x && myY <= y && x < myX + myWidth && y < myY + myHeight) {
          return true; //Return true if it is within those bounds
      } else {
          return false; //Return false if not
      }
  }
}
