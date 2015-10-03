import java.awt.*;

/**
 * PointShape.java
 * Class for a rectangle.
 *
 * Written by THC for CS 10 Lab Assignment 1.
 *
 * @author Tom Cormen
 * @author Ezekiel Elin
 * @see Shape
 */
public abstract class PointShape extends Shape {

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public PointShape(int x, int y, int width, int height, Color c) {
        super(c);
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        cleanup(new Point(x, y));
    }

    public Point getCenter() {
        Point p = new Point();
        p.x = x + (int)(width * .5);
        p.y = y + (int)(height * .5);
        return p;
    }

    public void move(int dX, int dY) {
        x += dX;
        y += dY;
    }

    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public void setWidth(int w) {
        width = w;
    }
    public void setHeight(int h) {
        height = h;
    }

    public void cleanup(Point origin) {
        if (this.width == 0) //don't allow zero-width
            this.width = 1;
        if (this.height == 0) //don't allow zero-height
            this.height = 1;

        if (this.width < 0) {
            this.x = origin.x + this.width;
            this.width *= -1;
        }
        if (this.height < 0) {
            this.y = origin.y + this.height;
            this.height *= -1;
        }
    }
}
