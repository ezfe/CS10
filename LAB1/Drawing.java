import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

/**
* Drawing.java
*
* An abstract class for storing the Shape objects in a
* drawing. This class provides a constructor to store
* the initial color, along with methods setColor and
* getColor. All other methods are abstract and must be
* defined in a subclass.
*
* Written by THC for CS 10 Lab Assignment 1.
*
* @author Tom Cormen
*/

public abstract class Drawing {
    private Color currentColor;         // current default color

    /**
    * Constructor, sets the current color.
    */
    public Drawing(Color initialColor) {
        currentColor = initialColor;
    }

    /**
    * Set the default color.
    *
    * @params c a color for drawing
    */
    public void setColor(Color c) {
        currentColor = c;
    }

    /**
    * Return default color.
    *
    * @return the current drawing color
    */
    public Color getColor() {
        return currentColor;
    }

    /**
    * Add a Shape to the front of the list.
    *
    * @params c a shape you wish to add to the drawing
    */
    public abstract void add(Shape s);


    /**
    * Have each Shape in the list draw itself.
    * Draws from back to front, so that Shapes in the front overlay Shapes
    * in the back.
    *
    * @params page the page you wish to draw the shapes on
    */
    public abstract void draw(Graphics page);

    /**
    * Return a reference to the first Shape in the drawing (from front to back)
    * that contains Point p. If no Shape contains p, return null.
    *
    * @params p the point under which you wish to find the frontmost shape
    * @return the front most shape or null if no shape is found
    */
    public abstract Shape getFrontmostContainer(Point p);

    /**
    * Given a reference to a Shape, remove it from the drawing if it's there.
    *
    * @params s the which you wish to remove from the drawing
    */
    public abstract void remove(Shape s);

    /**
    * Given a reference to a Shape, move it to the front of the drawing
    * if it's actually in the drawing.
    *
    * @param s the shape which you wish to move to the front
    */
    public abstract void moveToFront(Shape s);

    /**
    * Given a reference to a Shape, move it to the back of the drawing
    * if it's actually in the drawing.
    *
    * @param s the shape which you wish to move to the back
    */
    public abstract void moveToBack(Shape s);


    /**
    * Make a Shape replace the Shape currently at the front of the drawing.
    *
    * @param s the shape you wish to replace the frontmost shape with
    */
    public abstract void replaceFront(Shape s);

    public abstract ArrayList<PointShape> getSelected();
    public abstract void invalidateSelectCache();

    public abstract void recordHistoryItem(HistoryAction h);
    public abstract void undo();
}
