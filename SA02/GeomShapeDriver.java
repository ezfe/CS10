/**
 * GeomShapeDriver.java
 *
 * Demonstrates how to create and use objects that implement
 * the GeomShape interface.
 *
 * @author Scot Drysdale on 4/9/00.  Modified 1/7/2012
 */

public class GeomShapeDriver {

  public static void main(String [] args) {
    GeomShape [] shapes = new GeomShape[3];
    shapes[0] = new Circle(300, 200, 75);
    shapes[1] = new Rectangle(100, 200, 50.0, 75.0);
    shapes[2] = new Triangle(25, 50, 100, 45, 70);

    // Notice the polymorphism.  The first time through the loop,
    // shapes[i] is a Circle, and the second time shapes[i] is a Rectangle.
    for (int i = 0; i < shapes.length; i++) {
      System.out.println("Original shape: " + shapes[i] + " has area: "
                          + shapes[i].areaOf());
      shapes[i].move(-5, -35);
      System.out.println("Shape after move: " + shapes[i]);
      shapes[i].scale(2);
      System.out.println("Shape after scale: " + shapes[i]);
      if (shapes[i] instanceof Rectangle) {
      	((Rectangle) shapes[i]).flip();
      	System.out.println("Rectangle after flip: " + shapes[i]);
      }
    }
  }
}
