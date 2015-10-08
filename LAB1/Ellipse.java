import java.awt.*;

/**
* Ellipse.java
* Class for an ellipse.
*
* @author Tom Cormen
* @author Ezekiel Elin
* @see PointShape
*/

public class Ellipse extends PointShape {
    public Ellipse(int x, int y, int w, int h, Color c) {
        super(x, y, w, h, c);
    }

    // Helper method that returns whether Point p is in an Ellipse with the given
    // top left corner and size.
    private static boolean pointInEllipse(Point p, int left, int top, int width, int height) {
        double a = width / 2.0;			// half of the width
        double b = height / 2.0;		// half of the height
        double centerx = left + a;	// x-coord of the center
        double centery = top + b;		// y-coord of the center
        double x = p.x - centerx;		// horizontal distance between p and center
        double y = p.y - centery;		// vertical distance between p and center

        // Now we just apply the standard geometry formula.
        // (See CRC, 29th edition, p. 178.)
        return Math.pow(x / a, 2) + Math.pow(y / b, 2) <= 1;
    }

    public boolean containsPoint(Point p) {
        return pointInEllipse(p, x, y, width, height);
    }

    public void drawShape(Graphics g) {
        g.fillOval(x, y, width, height); //Fill the oval
        if (this.isSelected()) { //If it's selected
            g.setColor(Color.black); //Outline it black as well
            g.drawOval(x, y, width, height); //Like this!
        }
    }
}
