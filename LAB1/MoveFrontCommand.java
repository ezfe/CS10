/**
 * MoveFrontCommand.java
 *
 * Subclass of Command for moving to front
 * Moves the selected item to the front of the canvas
 * This Command does NOT record history
 *
 * @author Ezekiel Elin
 */

import java.awt.*;

 public class MoveFrontCommand extends Command {
    public void executeClick(Point p, Drawing dwg) {
        //Get the shape that the user clicked
        Shape s = dwg.getFrontmostContainer(p);
        //If it's null, abort the action
        if (s == null) return;
        //Move the shape
        dwg.moveToFront(s);
    }
 }
