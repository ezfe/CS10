/**
 * DeleteCommand.java
 *
 * Subclass of Command for deleting
 *
 * @author Ezekiel Elin
 */

import java.awt.*;

 public class DeleteCommand extends Command {
    public void executeClick(Point p, Drawing dwg) {
        Shape s = dwg.getFrontmostContainer(p);
        dwg.remove(s);
    }
 }
