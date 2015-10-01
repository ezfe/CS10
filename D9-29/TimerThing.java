import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TimerThing extends JApplet implements ActionListener {
    private static final long serialVersionUID = 1L;

    private final int DIMENSION = 600;

    private final int TIME = 45;
    private int timeRemaining = TIME;

    private boolean flash = false;

    public void init() {
        setSize(DIMENSION, DIMENSION);

        Container cp = getContentPane();    // content pane holds components
        cp.add(new Canvas());               // the canvas is the only component

        setVisible(true);   // makes the applet (and its components) visible

        Timer t = new Timer(1000, this);
        t.start();

        repaint();
    }

    private class Canvas extends JPanel {
        private static final long serialVersionUID = 1L;

        private int colorer = 0;

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (!flash) {
                colorer++;
            }

            Color colorA = Color.white;
            Color colorB = Color.black;

            if (colorer % 5 == 0) {
                colorA = Color.red;
                colorB = Color.blue;
            } else  if (colorer % 5 == 1) {
                colorA = Color.green;
                colorB = Color.yellow;
            } else if (colorer % 5 == 2) {
                colorA = Color.yellow;
                colorB = Color.blue;
            } else if (colorer % 5 == 3) {
                colorA = Color.red;
                colorB = Color.green;
            } else if (colorer % 5 == 4) {
                colorA = Color.yellow;
                colorB = Color.blue;
            }

            g.setColor(colorA);
            g.fillRect(0, 0, DIMENSION, DIMENSION);

            g.setColor(colorB);

            if (!flash) {
                g.setFont(new Font("Helvetica", Font.PLAIN, 300));
                g.drawString("" + timeRemaining, DIMENSION/2-(75*(""+timeRemaining).length()), DIMENSION/2+110);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (timeRemaining > 0)
            timeRemaining--;
        else
            flash = !flash;

        repaint();
    }
}
