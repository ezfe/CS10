import java.awt.*;

/**
* Segment.java
* Class for a line segment.
*
* Written by THC for CS 10 Lab Assignment 1.
*
* @author Tom Cormen
* @author YOU
* @see Shape
*/
public class Segment extends PointShape {

    private static final int TOLERANCE = 3;
    private boolean reversed = false;

    public Segment(int x, int y, int height, int width, Color c) {
        super(x, y, height, width, c);
    }

    // Helper method that returns true if Point p is within a tolerance of a
    // given bounding box. Here, the bounding box is given by the coordinates of
    // its left, top, right, and bottom.
    private static boolean almostContainsPoint(Point p, int left, int top,
    int right, int bottom, double tolerance) {
        return p.x >= left - tolerance && p.y >= top - tolerance
        && p.x <= right + tolerance && p.y <= bottom + tolerance;
    }

    // Helper method that returns the distance from Point p to the line
    // containing a line segment whose endpoints are given.
    private static double distanceToPoint(Point p, int x1, int y1, int x2, int y2) {
        if (x1 == x2) // vertical segment?
        return (double) (Math.abs(p.x - x1)); // yes, use horizontal distance
        else if (y1 == y2) // horizontal segment?
        return (double) (Math.abs(p.y - y1)); // yes, use vertical distance
        else {
            // Here, we know that the segment is neither vertical nor
            // horizontal.
            // Compute m, the slope of the line containing the segment.
            double m = ((double) (y1 - y2)) / ((double) (x1 - x2));

            // Compute mperp, the slope of the line perpendicular to the
            // segment.
            double mperp = -1.0 / m;

            // Compute the (x, y) intersection of the line containing the
            // segment and the line that is perpendicular to the segment and that
            // contains Point p.
            double x = (((double) y1) - ((double) p.y) - (m * x1) + (mperp * p.x))
            / (mperp - m);
            double y = m * (x - x1) + y1;

            // Return the distance between Point p and (x, y).
            return Math.sqrt(Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2));
        }
    }

    public boolean containsPoint(Point p) {
        //Check if this line segment is reversed, because it's hitbox will be different if it is
        if (isReversed()) {
            //Check if we're in the rect
            if (almostContainsPoint(p, x, y, (x + width), (y + height), TOLERANCE)) {
                //Check if we're close enough to the reversed line
                if (distanceToPoint(p, (x + width), y, x, (y + height)) < TOLERANCE) {
                    return true;
                }
            }
        } else {
            if (almostContainsPoint(p, x, y, (x + width), (y + height), TOLERANCE)) {
                if (distanceToPoint(p, x, y, (x + width), (y + height)) < TOLERANCE) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isReversed() {
        return reversed;
    }

    public void setReversed(boolean r) {
        reversed = r;
    }

    public void drawShape(Graphics g) {
        if (this.isSelected()) {
            g.setColor(Color.black);
        }
        if (isReversed()) {
            //Draw the reversed line
            g.drawLine((x + width), y, x, (y + height));
        } else {
            //Draw the regular line
            g.drawLine(x, y, (x + width), (y + height));
        }
    }
}
