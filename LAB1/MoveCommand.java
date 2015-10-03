/**
* CreateRectCommand.java
*
* Subclass of Command for moving objets
*
* @author Ezekiel Elin
*/

import java.awt.*;

public class MoveCommand extends Command {
    private PointShape s = null; //Current object
    private Point pt = null; //Track the last point (to calculate DeltaX, DeltaY)

    public void executePress(Point p, Drawing dwg) {
        s = (PointShape)dwg.getFrontmostContainer(p); //Set s
        pt = p; //Set pt
    }

    public void executeDrag(Point p, Drawing dwg) {
        if (s != null && pt != null) {
            s.move(p.x - pt.x, p.y - pt.y); //Move the current object s by DeltaX, DeltaY
        }
        pt = p; //Update pt
    }
}
