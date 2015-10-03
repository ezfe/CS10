import java.util.ArrayList;
import java.awt.*;

/**
 * ArrayListDrawing.java
 * 
 * Subclass of the abstract Drawing class.
 * Stores a drawing as an ordered list of Shape objects.
 * 
 * Written by THC for CS 10 Lab Assignment 1.
 * Modified by Scot Drysdale to use set on replaceFront.
 * 
 * @author Tom Cormen
 * @see Drawing
 */
public class ArrayListDrawing extends Drawing {
  private ArrayList<Shape> shapes;    // the ordered list of Shape objects

  /**
   * Constructor creates an empty list of Shape objects and 
   * saves the default color.
   *
   * @params initialColor the initial drawing color
   */
  public ArrayListDrawing(Color initialColor) {
  	super(initialColor);
    shapes = new ArrayList<Shape>();
  }

  /**
   * Add a Shape to the front of the list.
   * 
   * @params c a shape you wish to add to the drawing
   */
  public void add(Shape s) {
    shapes.add(0, s);
  }


  /**
   * Have each Shape in the list draw itself.
   * Draws from back to front, so that Shapes in the front overlay Shapes
   * in the back.
   * 
   * @params page the page you wish to draw the shapes on
   */
  public void draw(Graphics page) {
    for (int i = shapes.size() - 1; i >= 0; i--)
      shapes.get(i).draw(page);
  }

  /**
   * Return a reference to the first Shape in the drawing (from front to back)
   * that contains Point p. If no Shape contains p, return null.
   * 
   * @params p the point under which you wish to find the frontmost shape
   * @return the front most shape or null if no shape is found
   */
  public Shape getFrontmostContainer(Point p) {
    for (int i = 0; i < shapes.size(); i++)
      if (shapes.get(i).containsPoint(p))
        return shapes.get(i);

    return null;
  }
  
  /**
   * Given a reference to a Shape, remove it from the drawing if it's there. 
   * 
   * @params s the which you wish to remove from the drawing
   */
  public void remove(Shape s) {
  	shapes.remove(s);
  }
  
  /**
   * Given a reference to a Shape, move it to the front of the drawing
   * if it's actually in the drawing.
   * 
   * @param s the shape which you wish to move to the front
   */
  public void moveToFront(Shape s) {
    int index = shapes.indexOf(s);	// where in the ArrayList it is
    if (index >= 0) {								// is it in the ArrayList?
      shapes.remove(index);					// yes, remove it from where it is...
      shapes.add(0, s);							// ...and add it to the front
    }
  }

  /**
   * Given a reference to a Shape, move it to the back of the drawing 
   * if it's actually in the drawing.
   * 
   * @param s the shape which you wish to move to the back
   */
  public void moveToBack(Shape s) {
    int index = shapes.indexOf(s);	// where in the ArrayList it is
    if (index >= 0) {								// is it in the ArrayList?
      shapes.remove(index);					// yes, remove it from where it is...
      shapes.add(s);								// ...and add it to the back
    }
  }

  /**
   * Make a Shape replace the Shape currently at the front of the drawing.
   * 
   * @param s the shape you wish to replace the frontmost shape with
   */
  public void replaceFront(Shape s) {
    if (shapes.size() > 0) {		// is the drawing nonempty?
      shapes.set(0, s);					// yes, put in the replacement
    }
  }
}