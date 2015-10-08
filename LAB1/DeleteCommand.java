/**
 * DeleteCommand.java
 *
 * Subclass of Command for deleting
 * Deletes any object when you click it
 *
 * @author Ezekiel Elin
 */

import java.awt.*;

public class DeleteCommand extends Command {
    public void executeClick(Point p, Drawing dwg) {
        //Get the shape that the user clicked
        Shape s = dwg.getFrontmostContainer(p);
        //If it's null, abort the action
        if (s == null) return;
        //Record history
        dwg.recordHistoryItem(new HistoryAction((PointShape)s, dwg, true));
        //Remove the shape from the drawing
        dwg.remove(s);
    }
 }
