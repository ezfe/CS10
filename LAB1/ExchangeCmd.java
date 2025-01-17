import java.awt.*;

/**
* ExchangeCmd.java
* Command class to perform an exchange command.
*
* Written by THC for CS 10 Lab Assignment 1.
*
* @author Tom Cormen
* @see Command
*/
public class ExchangeCmd extends Command {
    private Shape firstShape; // the first Shape clicked

    /**
    * When the mouse is clicked, find the frontmost Shape in the drawing
    * that contains the mouse position. If there is such a Shape, then
    * if this is the first click in the pair of clicks (indicated by
    * firstShape being null), save it in firstShape. Otherwise, exchange
    * the centers of this newly clicked Shape and firstShape.
    *
    * @param p the coordinates of the click
    * @param dwg the drawing being clicked
    */
    public void executeClick(Point p, Drawing dwg) {
        // Find the frontmost shape containing p.
        Shape s = dwg.getFrontmostContainer(p);

        if (s != null) { 		// was there a Shape containing p?
            if (firstShape == null)
            firstShape = s; // save this Shape for when there's another click
            else {
                // We have two Shapes to exchange. Get their centers.
                Point firstCenter = firstShape.getCenter();
                Point secondCenter = s.getCenter();

                dwg.recordHistoryItem(new HistoryAction((PointShape)firstShape, dwg));
                dwg.recordHistoryItem(new HistoryAction((PointShape)s, dwg));


                // Exchange their centers.
                firstShape.setCenter(secondCenter);
                s.setCenter(firstCenter);

                // Now we get to start all over.
                firstShape = null;
            }
        }
    }
}
