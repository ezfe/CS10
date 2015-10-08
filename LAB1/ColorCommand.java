/**
* ColorCommand.java
*
* Subclass of Command for coloring objects
* Colors any object the designated color when you click it
*
* @author Ezekiel Elin
*/

import java.awt.*;

public class ColorCommand extends Command {
    private Color color = Color.blue;

    /**
     * Initilize object with a color
     * @param  c Color to be applied to clicked objects
     */
    public ColorCommand(Color c) {
        //Set the instance variable
        this.color = c;
    }

    public void executeClick(Point p, Drawing dwg) {
        //Get the shape that the user clicked
        Shape s = dwg.getFrontmostContainer(p);
        //If it's null, abort the action
        if (s == null) return;
        //Record history
        dwg.recordHistoryItem(new HistoryAction((PointShape)s, dwg));
        //Set the color to the stored color
        s.setColor(color);
    }
}
