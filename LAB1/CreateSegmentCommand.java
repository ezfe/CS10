/**
 * CreateSegmentCommand.java
 *
 * Subclass of Command for creating segments
 *
 * @author Ezekiel Elin
 */

import java.awt.*;

 public class CreateSegmentCommand extends Command {
    private Segment s;
    private Point oP;

    public void executePress(Point p, Drawing dwg) {
        Color c = dwg.getColor();
        s = new Segment(p.x, p.y, 0, 0, c);
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
            if (p.x > oP.x && p.y > oP.y) {
                s.setReversed(false);
            } else if (p.x < oP.x && p.y < oP.y) {
                s.setReversed(false);
            } else {
                s.setReversed(true);
            }
            s.setWidth(p.x - oP.x);
            s.setHeight(p.y - oP.y);
            s.cleanup(oP);
        }
    }
 }
