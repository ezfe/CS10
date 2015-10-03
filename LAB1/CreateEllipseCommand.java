/**
 * CreateEllipseCommand.java
 *
 * Subclass of Command for creating ellipses
 *
 * @author Ezekiel Elin
 */

import java.awt.*;

 public class CreateEllipseCommand extends Command {
    private Ellipse s;
    private Point oP;

    public void executePress(Point p, Drawing dwg) {
        Color c = dwg.getColor();
        s = new Ellipse(p.x, p.y, 0, 0, c);
        oP = p;
        dwg.add(s);
    }

    public void executeClick(Point p, Drawing dwg) {
        if (s != null) {
            dwg.remove(s);
        }
    }

    public void executeDrag(Point p, Drawing dwg) {
        if (s != null && oP != null) {
            s.setWidth(p.x - oP.x);
            s.setHeight(p.y - oP.y);
            s.cleanup(oP);
        }
    }
 }
