package jason.tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;

public class BricksPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int ROWS = 15;
	private static final int COLS = 10;
	private static final int WIDTH = COLS * Brick.TILE_SIZE;
	private static final int HEIGHT = ROWS * Brick.TILE_SIZE;
	
	private static final int SHAPE_I = 0;
	private static final int SHAPE_J = 1;
	private static final int SHAPE_L = 2;
	private static final int SHAPE_O = 3;
	private static final int SHAPE_S = 4;
	private static final int SHAPE_T = 5;
	private static final int SHAPE_Z = 6;
	private static final int NUMBER_OF_SHAPES = 7;
	
	private Brick brick;
	private Random rand = new Random();
	
	public BricksPanel() {
		initGUI();
		
		start();
	}
	
	private void initGUI() {
		setFocusable(true);
		
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				switch (code) {
				case KeyEvent.VK_LEFT:
					moveLeft();
					break;
				case KeyEvent.VK_RIGHT:
					moveRight();
					break;
				case KeyEvent.VK_Z:
					rotateLeft();
					break;
				case KeyEvent.VK_X:
					rotateRight();
					break;
				}
			}
		});
	}
	
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
		if (brick!=null) {
			brick.draw(g);
		}
		
	}
	
	public void start() {
		pickABrick();
	}
	
	private void pickABrick() {
		int row = 0;
		int col = COLS/2;
		int pick = rand.nextInt(NUMBER_OF_SHAPES);
		switch(pick) {
		case SHAPE_I:
			brick = new IBrick(row,col);
			break;
		case SHAPE_J:
			brick = new JBrick(row,col);
			break;
		case SHAPE_L:
			brick = new LBrick(row,col);
			break;
		case SHAPE_O:
			brick = new OBrick(row,col);
			break;
		case SHAPE_S:
			brick = new SBrick(row,col);
			break;
		case SHAPE_T:
			brick = new TBrick(row,col);
			break;
		case SHAPE_Z:
			brick = new ZBrick(row,col);
			break;
		}
	}
	
	private void moveLeft() {
		brick.moveLeft();
		if (isLegal()) {
			repaint();
		}
		else {
			brick.moveRight();
		}
	}
	
	private void moveRight() {
		brick.moveRight();
		if (isLegal()) {
			repaint();
		}
		else {
			brick.moveLeft();
		}
	}
	
	private void rotateLeft() {
		brick.rotateLeft();
		if (isLegal()) {
			repaint();
		}
		else {
			brick.rotateRight();
		}
	}
	
	private void rotateRight() {
		brick.rotateRight();
		if (isLegal()) {
			repaint();
		}
		else {
			brick.rotateLeft();
		}
	}
	
	
	
	private boolean isLegal() {
		boolean legal = true;
		int row = brick.getRow();
		int col = brick.getColumn();
		int brickRows = brick.getNumberOfRows();
		int brickCols = brick.getNumberOfColumns();
		
		// if beyond right or left edge of panel
		if (col<0 || col+brickCols > COLS) {
			legal = false;
		}
		
		// if beyond top or bottom edge of panel
		if (row<0 || row+brickRows > ROWS) {
			legal = false;
		}
		
		return legal;
	}

}
