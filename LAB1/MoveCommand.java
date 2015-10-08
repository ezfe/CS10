/**
* CreateRectCommand.java
*
* Subclass of Command for moving objets
* Allows dragging of an object or selection of objects
*
* @author Ezekiel Elin
*/

import java.awt.*;
import java.util.ArrayList;

public class MoveCommand extends Command {
    private PointShape s = null; //Current object
    private Point pt = null; //Track the last point (to calculate DeltaX, DeltaY)

    public void executePress(Point p, Drawing dwg) {
        //Get the shape that the user clicked
        s = (PointShape)dwg.getFrontmostContainer(p); //Set s
        //If it's null, abort the action
        if (s == null) return;
        //Set pt
        pt = p;
        //We need selection stuff, so invalidate the cache
        dwg.invalidateSelectCache();

        if (s.isSelected()) { //If s is selected, use selection mode instead
            //Get the selected shapes
            ArrayList<PointShape> selectedShapes = dwg.getSelected();
            //If selectedShapes is null, abort!
            if (selectedShapes == null) return;

            //Loop through shapes
            for (int i = 0; i < selectedShapes.size(); i++) {
                PointShape shape = selectedShapes.get(i); //Save the shape
                dwg.recordHistoryItem(new HistoryAction(shape, dwg)); //Record history on it
            }
        } else {
            dwg.recordHistoryItem(new HistoryAction(s, dwg)); //Record history on just s
        }
    }

    public void executeDrag(Point p, Drawing dwg) {
        if (s != null && pt != null) {
            if (s.isSelected()) {
                //Get the selected shapes
                ArrayList<PointShape> selectedShapes = dwg.getSelected();
                //If selectedShapes is null, abort!
                if (selectedShapes == null) return;

                //Loop through shapes
                for (int i = 0; i < selectedShapes.size(); i++) {
                    PointShape shape = selectedShapes.get(i); //Save the shape
                    shape.move(p.x - pt.x, p.y - pt.y); //Move the current object shape by DeltaX, DeltaY
                }
            } else {
                s.move(p.x - pt.x, p.y - pt.y); //Move s by DeltaX, DeltaY
            }
        }
        pt = p; //Update pt
    }
}
