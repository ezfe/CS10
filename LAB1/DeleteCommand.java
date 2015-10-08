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
        if (s == null) return;
        dwg.recordHistoryItem(new HistoryAction((PointShape)s, true));
        dwg.remove(s);
    }
 }
