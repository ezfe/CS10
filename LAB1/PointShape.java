import java.awt.*;

/**
 * PointShape.java
 *
 * Represents a Shape which is defined by x, y, width, height
 *
 * @author Ezekiel Elin
 * @see Shape
 */

public abstract class PointShape extends Shape {

    //These items are protected to allow easy access by subclasses
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public PointShape(int x, int y, int width, int height, Color c) {
        super(c);

        //Store necessary instance variables
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        //Cleanup the shape relative to it's x, y location
        cleanup(new Point(x, y));
    }

    /**
     * Get the center of the shape
     * @return Point of the center
     */
    public Point getCenter() {
        Point p = new Point(); //Make a new point
        p.x = x + (int)(width * .5); //Add half the width to x
        p.y = y + (int)(height * .5); //Add half the height to y
        return p; //Return the point
    }

    /**
     * Move the object
     * @param  delta x
     * @param  delta y
     */
    public void move(int dX, int dY) {
        x += dX;
        y += dY;
    }

    /**
     * Move the object to a location
     * @param  destination x
     * @param  destination y
     */
    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Cleanup the object
     * This prevents the object from having unexpected coordinates
     * @param origin point of origin for the object, usually upper left hand corner
     */
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

    /**
     * Get X
     * @return x coordinate
     */
	public int getX() {
		return x;
	}

    /**
     * Get Y
     * @return y coordinate
     */
	public int getY() {
		return y;
	}

    /**
     * Get width
     * @return width
     */
	public int getWidth() {
		return width;
	}

    /**
     * Get height
     * @return height
     */
	public int getHeight() {
		return height;
	}

    /**
     * Set width
     * @param width new width
     */
	public void setWidth(int width) {
		this.width = width;
	}

    /**
     * Set height
     * @param height new height
     */
	public void setHeight(int height) {
		this.height = height;
	}
}
