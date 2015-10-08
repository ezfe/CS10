/**
 * HistoryAction.java
 *
 * Class to represent a history event
 *
 * @author Ezekiel Elin
 */

import java.awt.*;

public class HistoryAction {
    //Store the shape, the drawing, the color, and the location/width/height
    private PointShape shape;
    private Drawing dwg;
    private Color c;
    private int x, y, w, h;

    private boolean regular = true; //Just for line segments

    private boolean deleted = false; //Wether this history item represents the deletion of an object
    private boolean created = false; //Wether this history item represents the creation of an object

    public HistoryAction(PointShape s, Drawing dwg) {
        //Init as a regular history item
        create(s, dwg, false, false);
    }

    public HistoryAction(PointShape s, Drawing dwg, boolean type) {
        //Init as a special history item. type=true, deleted. type=false, created
        create(s, dwg, type, !type);
    }

    /**
     * Common init method
     * @param  Shape reference
     * @param  Drawing reference
     * @param  Deleted?
     * @param  Created?
     */
    private void create(PointShape s, Drawing dwg, boolean deleted, boolean created) {
        //First make sure we don't have conflicting statements
        if (deleted && created) created = false;

        //Store shape, drawing, x, y, h, w, and c
        shape = s;
        this.dwg = dwg;
        x = shape.getX();
        y = shape.getY();
        h = shape.getHeight();
        w = shape.getWidth();
        c = shape.getColor();

        //Store deleted/created
        this.deleted = deleted;
        this.created = created;
    }

    /**
     * Restore to this point in history
     */
    public void restore() {
        if (shape != null && c != null && dwg != null) { //Make sure we have a shape, drawing, and color
            shape.moveTo(x, y); //Move the shape to the old x, y
            shape.setHeight(h); //Set it's height to the old h
            shape.setWidth(w); //Set it's width to the old w
            shape.setColor(c); //Set the color to the old c
            if (shape instanceof Segment) { //If it's a line segment,
                ((Segment)shape).setReversed(regular); //Set reversed
            }

            if (deleted) { //If it was deleted,
                dwg.add(shape); //Re-add it
            } else if (created) { //And if it was created,
                dwg.remove(shape); //Delete it
            }
        }
    }
}
