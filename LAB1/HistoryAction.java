/**
 * HistoryAction.java
 *
 * Class to represent a history event
 *
 * @author Ezekiel Elin
 */

import java.awt.*;

public class HistoryAction {
    private PointShape shape;
    private Color c;
    private int x, y, w, h;

    private boolean regular = true; //Just for lign segments

    private boolean deleted = false;

    public HistoryAction(PointShape s) {
        shape = s;
        x = shape.getX();
        y = shape.getY();
        h = shape.getHeight();
        w = shape.getWidth();
        c = shape.getColor();
    }

    public HistoryAction(PointShape s, boolean deleted) {
        shape = s;
        x = shape.getX();
        y = shape.getY();
        h = shape.getHeight();
        w = shape.getWidth();
        c = shape.getColor();

        this.deleted = deleted;
    }

    public void restore() {
        if (shape != null && c != null) {
            shape.moveTo(x, y);
            shape.setHeight(h);
            shape.setWidth(w);
            shape.setColor(c);
            if (shape instanceof Segment) {
                ((Segment)shape).setReversed(regular);
            }

            if (deleted) {
                System.out.println("Unable to revive objects right now!");
            }
        }
    }
}
