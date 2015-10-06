/**
 * SelectCommand.java
 *
 * Subclass of Command for toggling selection
 *
 * @author Ezekiel Elin
 */

import java.awt.*;

 public class SelectCommand extends Command {
    public void executeClick(Point p, Drawing dwg) {
        Shape s = dwg.getFrontmostContainer(p);
        if (s == null) return;
        s.toggleSelection();
    }
 }
