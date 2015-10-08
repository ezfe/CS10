import java.awt.*;

/**
* Shape.java
* Abstract class for geometric shapes.
* Provides three non-abstract methods: setColor, draw, and setCenter.
*
* Written by THC for CS 10 Lab Assignment 1.
*
* @author Tom Cormen
*/
public abstract class Shape {
    private Color color; // Shape's color
    private boolean selected = false;


    /**
    * Have the Shape draw itself.
    *
    * @param page the page you wish to draw on
    */
    public abstract void drawShape(Graphics page);

    /**
    * Return true if the Shape contains Point p, false otherwise.
    *
    * @param p point tested for containment
    */
    public abstract boolean containsPoint(Point p);

    /**
    * Have the Shape move itself.
    *
    * @param deltaX new x coordinate
    * @param deltaY new y coordinate
    */
    public abstract void move(int deltaX, int deltaY);

    /**
    * Return the Shape's center.
    * @return the center of the Shape
    */
    public abstract Point getCenter();

    /**
    * Create a Shape, setting its color.
    *
    * @param c the color you wish the shape to initially have
    */
    public Shape(Color c) {
        color = c;
    }

    /**
    * Set the Shape's color.
    *
    * @param newColor the new color of the shape
    */
    public void setColor(Color newColor) {
        color = newColor;
    }

    /**
    * Draw the Shape.
    *
    * @param page the page you wish to draw the shape on
    */
    public void draw(Graphics page) {
        Color savedColor = page.getColor();
        page.setColor(color);
        drawShape(page);
        page.setColor(savedColor);
    }

    /**
    * Move the Shape to be a given center.
    *
    * @param newCenter the new center of the shape
    */
    public void setCenter(Point newCenter) {
        Point oldCenter = getCenter();
        move(newCenter.x - oldCenter.x, newCenter.y - oldCenter.y);
    }

    public void select() {
        selected = true;
    }

    public boolean isSelected() {
        return selected;
    }

    public void toggleSelection() {
        selected = !selected;
    }

    public Color getColor() {
        return color;
    }
}
