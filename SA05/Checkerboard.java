/**
 * Checkerboard.java
 *
 * Starting point for a Short Assignment.
 * Draws a checkerboard with black and red squares. 
 * @author Tom Cormen.  Converted to JApplet by Scot Drysdale
 */

import javax.swing.*;
import java.awt.*;

public class Checkerboard extends JApplet {
	private static final long serialVersionUID = 1L;
	private final int DIMENSION = 8;		// want 8 x 8 board
  private final int SQUARE_SIZE = 30; // each square is 30 x 30 pixels

	/**
	 * Set up the canvas.
	 */
	public void init() {
		// Set the size of the applet to be exactly the checkerboard size.
		setSize(DIMENSION * SQUARE_SIZE, DIMENSION * SQUARE_SIZE);

		Container cp = getContentPane();    // content pane holds components
		cp.add(new Canvas());               // the canvas is the only component

		setVisible(true);   // makes the applet (and its components) visible
	}

	/**
	 * Serves as the drawing canvas for the checkerboard.
	 */
	private class Canvas extends JPanel {
		private static final long serialVersionUID = 1L;

		/**
		 * Paints the checkerboard.
		 * 
		 * @param page the Graphics object that we draw on.
		 */
		public void paintComponent(Graphics page) {
			super.paintComponent(page);

			// Draw all the black and red squares.
			drawCheckerboard(page);
		}

		/**
		 * Draw the entire checkerboard in black and red.
		 * 
		 * @param page the graphics object to draw on
		 */
		public void drawCheckerboard(Graphics page) {
			Color squareColor; // Color of the current square

			for (int row = 0; row < DIMENSION; row++) {
				for (int column = 0; column < DIMENSION; column++) {
					// A square is black if the row and column numbers are both even
					// or both odd. Otherwise, the square is red.
					if (row % 2 == column % 2)
						squareColor = Color.black;
					else
						squareColor = Color.red;

					drawSquare(page, row, column, squareColor);
				}
			}
		}

		/**
		 * Draw a given square of the checkerboard. Row and column numbers start
		 * from 0.
		 * 
		 * @param page graphics object to draw on
		 * @param row the row number of the square
		 * @param column the column number of the square
		 * @param color the color of the square
		 */
		public void drawSquare(Graphics page, int row, int column, Color color) {
			page.setColor(color);
			page.fillRect(column * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE,
					SQUARE_SIZE);
		}
	}
}