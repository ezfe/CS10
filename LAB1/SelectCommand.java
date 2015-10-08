/**
* SelectCommand.java
*
* Subclass of Command for toggling selection
* Toggles selection of clicked object
*
* @author Ezekiel Elin
*/

import java.awt.*;

public class SelectCommand extends Command {
    public void executeClick(Point p, Drawing dwg) {
        //Get the shape that the user clicked
        Shape s = dwg.getFrontmostContainer(p);
        //If it's null, abort the action
        if (s == null) return;
        //Toggle the selection
        s.toggleSelection();
    }
}
