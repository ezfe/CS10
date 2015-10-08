/**
* CreateEllipseCommand.java
*
* Subclass of Command for creating ellipses
* Creates an Ellipse when you click and drag
*
* @author Ezekiel Elin
*/

import java.awt.*;

public class CreateEllipseCommand extends Command {
    private Ellipse s; //New ellipse
    private Point oP; //Origin point

    public void executePress(Point p, Drawing dwg) {
        //Get the color we want the ellipse to have from the drawing
        Color c = dwg.getColor();
        //Create the ellipse and store it in the instance variable
        s = new Ellipse(p.x, p.y, 0, 0, c);
        //Store the starting click point oP
        oP = p;
        //Add the ellipse to the drawing
        dwg.add(s);
        //Record history
        dwg.recordHistoryItem(new HistoryAction(s, dwg, false));
    }

    public void executeDrag(Point p, Drawing dwg) {
        //Make sure we have a shape and point to work with
        if (s != null && oP != null) {
            s.setWidth(p.x - oP.x); //Set the new width
            s.setHeight(p.y - oP.y); //Set the new height
            s.cleanup(oP); //Cleanup s relative to oP
        }
    }
}
