/**
* CreateRectCommand.java
*
* Subclass of Command for moving objets
*
* @author Ezekiel Elin
*/

import java.awt.*;
import java.util.ArrayList;

public class MoveCommand extends Command {
    private PointShape s = null; //Current object
    private Point pt = null; //Track the last point (to calculate DeltaX, DeltaY)

    public void executePress(Point p, Drawing dwg) {
        s = (PointShape)dwg.getFrontmostContainer(p); //Set s
        if (s == null) return;
        pt = p; //Set pt
        dwg.invalidateSelectCache();
        dwg.recordHistoryItem(new HistoryAction(s));
    }

    public void executeDrag(Point p, Drawing dwg) {
        if (s != null && pt != null) {
            if (s.isSelected()) {
                ArrayList<PointShape> selectedShapes = dwg.getSelected();
                if (selectedShapes == null) return;
                for (int i = 0; i < selectedShapes.size(); i++) {
                    PointShape s = selectedShapes.get(i);
                    s.move(p.x - pt.x, p.y - pt.y); //Move the current object s by DeltaX, DeltaY
                }
            } else {
                s.move(p.x - pt.x, p.y - pt.y); //Move the current object s by DeltaX, DeltaY
            }
        }
        pt = p; //Update pt
    }
}
