/**
 * MoveFrontCommand.java
 *
 * Subclass of Command for moving to front
 *
 * @author Ezekiel Elin
 */

import java.awt.*;

 public class MoveFrontCommand extends Command {
    public void executeClick(Point p, Drawing dwg) {
        Shape s = dwg.getFrontmostContainer(p);
        if (s == null) return;
        dwg.moveToFront(s);
    }
 }
