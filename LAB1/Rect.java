import java.awt.*;

/**
 * Rect.java
 * Class for a rectangle.
 *
 * Written by THC for CS 10 Lab Assignment 1.
 *
 * @author Tom Cormen
 * @author Ezekiel Elin
 * @see Shape
 */
public class Rect extends PointShape {
    public Rect(int x, int y, int width, int height, Color c) {
        super(x, y, width, height, c);
    }

    public boolean containsPoint(Point p) {
        if (p.x > x && p.x < x + width && p.y > y && p.y < y + height)
            return true;
        return false;
    }

    public void drawShape(Graphics g) {
        g.fillRect(x, y, width, height);
    }
}
