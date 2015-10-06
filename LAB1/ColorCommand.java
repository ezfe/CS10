/**
* ColorCommand.java
*
* Subclass of Command for coloring objects
*
* @author Ezekiel Elin
*/

import java.awt.*;

public class ColorCommand extends Command {
    private Color color = Color.blue;

    public ColorCommand(Color c) {
        this.color = c;
    }

    public void executeClick(Point p, Drawing dwg) {
        Shape s = dwg.getFrontmostContainer(p);
        if (s == null) return;
        s.setColor(color);
    }
}
