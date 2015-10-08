/**
* CreateSegmentCommand.java
*
* Subclass of Command for creating segments
* Creates a Line Segment when you click and drag
*
* @author Ezekiel Elin
*/

import java.awt.*;

public class CreateSegmentCommand extends Command {
    private Segment s; //New line
    private Point oP; //Origin point

    public void executePress(Point p, Drawing dwg) {
        //Get the color we want the line to have from the drawing
        Color c = dwg.getColor();
        //Create the line and store it in the instance variable
        s = new Segment(p.x, p.y, 0, 0, c);
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

        //Make sure we have a shape and point to work with
        if (s != null && oP != null) {
            //Figure out wether it's reversed or not, and set that
            if (p.x > oP.x && p.y > oP.y) {
                s.setReversed(false);
            } else if (p.x < oP.x && p.y < oP.y) {
                s.setReversed(false);
            } else {
                s.setReversed(true);
            }

            s.setWidth(p.x - oP.x); //Set the new width
            s.setHeight(p.y - oP.y); //Set the new height
            s.cleanup(oP); //Cleanup s relative to oP
        }
    }
}
