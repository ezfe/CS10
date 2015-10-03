/**
 * Rect.java
 *
 * Represents a rectangle and allows various operations, including drawing
 *
 * @author Scot Drysdale on 4/9/00.  Modified on 4/14/00 to add additional methods.
 */
import java.awt.*;

public class Rect {
	private int myX, myY;           // x and y coords of Rect's upper left corner
	private int myWidth, myHeight;  // Rect's width and height
	private Color myColor;          // Rect's color

	/**
	 * Constructor for Rect. Called to create a Rect object with upper left
	 * corner (x, y), width "width", height "height", and color rectColor.
	 * @param x the x coordinate of the upper left corner
	 * @param y the y coordinate of the upper left corner
	 * @param width the width of the rectangle
	 * @param height the height of the rectangle
	 * @param rectColor the color that the rectangle should be drawn
	 */
	public Rect(int x, int y, int width, int height, Color rectColor) {
		myX = x;
		myY = y;
		myWidth = width;
		myHeight = height;
		myColor = rectColor;
	}

	/**
	 * Have the Rect object draw itself on the page passed as a parameter.
	 * @param page graphics object to draw on
	 */
	public void draw(Graphics page) {
		// Set the color.
		page.setColor(myColor);

		// Draw the Rect.
		page.drawRect(myX, myY, myWidth, myHeight);
	}

	/**
	 * Have the Rect object fill itself on the page passed as a parameter.
	 * @param page graphics object to draw on
	 */
	public void fill(Graphics page) {
		// Set the color.
		page.setColor(myColor);

		// Draw the Rect.
		page.fillRect(myX, myY, myWidth, myHeight);
	}

	/**
	 * Have the Rect object move itself by deltaX, deltaY.
	 * @param deltaX amount to change x value
	 * @param deltaY amount to change y value
	 */
	public void move(int deltaX, int deltaY) {
		myX += deltaX;
		myY += deltaY;
	}

	/**
	 * Set the x value of the upper left corner of Rect to x
	 * @param x new x value
	 */
	public void setX(int x) {
		myX = x;
	}

	/**
	 * Set the y value of the upper left corner of Rect to y
	 * @param y new y value
	 */
	public void setY(int y) {
		myY = y;
	}

	/**
	 * @return x value of the upper left corner of Rect
	 */
	public int getX() {
		return myX;
	}

	/**
	 * @return the y value of the upper left corner of Rect
	 */
	public int getY() {
		return myY;
	}

	/**
	 * Set the width of the Rect to width
	 * @param width the new width
	 */
	public void setWidth(int width) {
		myWidth = width;
	}

	/**
	 * Set the height of the Rect to height
	 * @param height the new height
	 */
	public void setHeight(int height) {
		myHeight = height;
	}

	/**
	 * Set the color of the rect to clr
	 * @param clr the new color
	 */
	public void setColor(Color clr) {
		myColor = clr;
	}

	/**
	 * Shrink a rectangle by amount in both directions.
	 * @param amount additive amount by which to shrink the rectangle
	 */
	public void shrink(int amount) {
		myX += amount;
		myY += amount;
		myHeight -= 2 * amount;
		myWidth -= 2 * amount;
	}

	/**
	 * @return area of the rectangle
	 */
	public double areaOf() {
		return myWidth * myHeight;
	}

    /**
     * Check wether a point is within the rect
     * @param point to check
     * @return wether the rect contains the passed point
     */
    public boolean containsPoint(Point p) {
        if (myX < p.x && myWidth + myX > p.x) {
            return true;
        } else {
            return false;
        }
    }
}
