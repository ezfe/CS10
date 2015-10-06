/**
 * MoveBackCommand.java
 *
 * Subclass of Command for moving to back
 *
 * @author Ezekiel Elin
 */

import java.awt.*;

 public class MoveBackCommand extends Command {
    public void executeClick(Point p, Drawing dwg) {
        Shape s = dwg.getFrontmostContainer(p);
        dwg.moveToBack(s);
    }
 }
