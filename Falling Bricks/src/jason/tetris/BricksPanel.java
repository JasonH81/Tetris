package jason.tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class BricksPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int ROWS = 15;
	private static final int COLS = 10;
	private static final int WIDTH = COLS * Brick.TILE_SIZE;
	private static final int HEIGHT = ROWS * Brick.TILE_SIZE;
	
	public Dimension getPreferredSize() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		return size;
	}
	
	public void paintComponent(Graphics g) {
		// background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		// fallen bricks
		
		
		// falling brick
		
		
	}

}
