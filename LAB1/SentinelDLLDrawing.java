import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
* SentinelDLLDrawing.java
*
* Subclass of the abstract Drawing class.
* Stores a drawing as an ordered list of Shape objects.
*
* Written by THC for CS 10 Lab Assignment 1.
*
* @author Tom Cormen
* @author Ezekiel Elin
* @see Drawing
*/
public class SentinelDLLDrawing extends Drawing {
    private SentinelDLL<Shape> shapes;    // the ordered list of Shape objects

    /**
    * Constructor creates an empty list of Shape objects and
    * saves the default color.
    *
    * @params initialColor the initial drawing color
    */
    public SentinelDLLDrawing(Color initialColor) {
        super(initialColor);
        shapes = new SentinelDLL<Shape>();
    }

    /**
    * Add a Shape to the front of the list.
    *
    * @params c a shape you wish to add to the drawing
    */
    public void add(Shape s) {
        shapes.addLast(s);
    }

    /**
    * Have each Shape in the list draw itself.
    * Draws from back to front, so that Shapes in the front overlay Shapes
    * in the back.
    *
    * @params page the page you wish to draw the shapes on
    */
    public void draw(Graphics page) {
        for (Shape s = shapes.getFirst(); shapes.hasCurrent(); s = shapes.next()) {
            s.draw(page);
        }
    }

    /**
    * Return a reference to the first Shape in the drawing (from front to back)
    * that contains Point p. If no Shape contains p, return null.
    *
    * @params p the point under which you wish to find the frontmost shape
    * @return the front most shape or null if no shape is found
    */
    public Shape getFrontmostContainer(Point p) {
        return new Rect(0, 0, 10, 10, Color.red);
    }

    /**
    * Given a reference to a Shape, remove it from the drawing if it's there.
    *
    * @params s the which you wish to remove from the drawing
    */
    public void remove(Shape rS) {
        for (Shape s = shapes.getFirst(); shapes.hasCurrent(); s = shapes.next()) {
            if (s == rS) {
                shapes.remove();
            }
        }
    }

    /**
    * Given a reference to a Shape, move it to the front of the drawing
    * if it's actually in the drawing.
    *
    * @param s the shape which you wish to move to the front
    */
    public void moveToFront(Shape rS) {
        for (Shape s = shapes.getFirst(); shapes.hasCurrent(); s = shapes.next()) {
            if (s == rS) {
                shapes.remove();
            }
        }
        shapes.addFirst(rS);
    }

    /**
    * Given a reference to a Shape, move it to the back of the drawing
    * if it's actually in the drawing.
    *
    * @param s the shape which you wish to move to the back
    */
    public void moveToBack(Shape rS) {
        for (Shape s = shapes.getFirst(); shapes.hasCurrent(); s = shapes.next()) {
            if (s == rS) {
                shapes.remove();
            }
        }
        shapes.addLast(rS);
    }


    /**
    * Make a Shape replace the Shape currently at the front of the drawing.
    *
    * @param s the shape you wish to replace the frontmost shape with
    */
    public void replaceFront(Shape s) {
        // YOU FILL THIS IN.
    }
}
