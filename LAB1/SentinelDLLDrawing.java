import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

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
    private SentinelDLL<Shape> shapes; // the ordered list of Shape objects

    private ArrayList<PointShape> selectedCache; //Cache of selected items
    private ArrayList<HistoryAction> history = new ArrayList<HistoryAction>(); //History records!

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
        selectedCache = null;
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
        for (Shape s = shapes.getFirst(); s != null; s = shapes.next()) {
            //Draw each shape on the page
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
        for (Shape s = shapes.getLast(); s != null; s = shapes.previous()) {
            //Loop through, back to front, and return the first shape that contains p
            if (s.containsPoint(p)) return s;
        }
        return null;
    }

    /**
    * Given a reference to a Shape, remove it from the drawing if it's there.
    *
    * @params s the which you wish to remove from the drawing
    */
    public void remove(Shape rS) {
        selectedCache = null;
        if (rS == null) return; //If we don't have a shape to remove, don't do anything
        for (Shape s = shapes.getFirst(); s != null; s = shapes.next()) {
            if (s == rS) {
                //Remove the shape if it's equal
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
        //Remove rS
        remove(rS);
        //And add it at the end
        shapes.addLast(rS);
    }

    /**
    * Given a reference to a Shape, move it to the back of the drawing
    * if it's actually in the drawing.
    *
    * @param s the shape which you wish to move to the back
    */
    public void moveToBack(Shape rS) {
        //Remove rS
        remove(rS);
        //And add it at the beginning
        shapes.addFirst(rS);
    }


    /**
    * Make a Shape replace the Shape currently at the front of the drawing.
    *
    * @param s the shape you wish to replace the frontmost shape with
    */
    public void replaceFront(Shape s) {
        //Grab the first shape
        shapes.getFirst();
        //Remove it
        shapes.remove();
        //And add s at the beginning
        shapes.addFirst(s);
    }

    /**
    * Get a list of selected items
    * @return ArrayList<PointShape> of selected items
    */
    public ArrayList<PointShape> getSelected() {
        //If the cache is set, return it instead of calculating
        if (selectedCache != null) return selectedCache;

        //New empty list
        ArrayList<PointShape> sShapes = new ArrayList<PointShape>();

        //Loop through, check if its selected, and add it to sShapes
        for (Shape s = shapes.getFirst(); s != null; s = shapes.next()) {
            if (s.isSelected()) {
                sShapes.add((PointShape)s);
            }
        }
        //Set the cache
        selectedCache = sShapes;
        //Return the list
        return sShapes;
    }

    /**
    * Notify the drawing that it should refresh the list of selected items
    */
    public void invalidateSelectCache() {
        selectedCache = null; //Set the cache to null
    }

    /**
    * Record history, for future undoing
    * @param h Instance of HistoryAction
    */
    public void recordHistoryItem(HistoryAction a) {
        history.add(a); //Add the history action
    }

    /**
     * Request that the drawing revert one step in history. May not cause anything to occur if history is empty
     */
    public void undo() {
        if (history.size() == 0) return; //Nothing to undo
        HistoryAction a = history.get(history.size() - 1); //Grab the last history action
        history.remove(history.size() - 1); //Remove it from the list
        a.restore(); //And restore it
    }

}
