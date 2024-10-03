package jason.tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import jason.mycommonmethods.FileIO;

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
	private static final String SNAP_SOUND = "/snap.wav";
	
	private Brick brick;
	private Random rand = new Random();
	private BufferedImage[][] board;
	private Tetris tetris;
	private Timer timer;
	
	public BricksPanel(Tetris tetris) {
		this.tetris = tetris;
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
				case KeyEvent.VK_SPACE:
					drop();
					break;
				}
			}
		});
		
		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timedAction();
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
		for (int row = 0; row<ROWS; row++) {
			for (int col = 0; col<COLS; col++) {
				int x = col * Brick.TILE_SIZE;
				int y = row * Brick.TILE_SIZE;
				if (board[row][col]!=null) {
					g.drawImage(board[row][col], x, y, null);
				}
			}
		}
		
		// falling brick
		if (brick!=null) {
			brick.draw(g);
		}
		
	}
	
	public void start() {
		timer.start();
		board = new BufferedImage[ROWS][COLS];
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
		
		if (!isLegal()) {
			brick = null;
			timer.stop();
			String message = "Would you like to play again?";
			int option = JOptionPane.showConfirmDialog(this, message);
			if (option == JOptionPane.YES_OPTION) {
				tetris.restart();
			}
			else {
				System.exit(0);
			}
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
		
		// is the required space empty?
		else {
			for (int r = 0; r<brickRows; r++) {
				for (int c = 0; c<brickCols; c++) {
					if (brick.hasTileAt(r, c) && board[row + r][col + c]!=null) {
						legal = false;
					}
				}
			}
		}
		
		return legal;
	}
	
	private void drop() {
		// drop brick to lowest legal position
		boolean legal = true;
		while (legal) {
			brick.drop1Row();
			if (!isLegal()) {
				legal = false;
				brick.rise1Row();
			}
		}
		insertBrick();
		
		repaint();
	}
	
	private void insertBrick() {
		int brickRow = brick.getRow();
		int brickCol = brick.getColumn();
		int brickRows = brick.getNumberOfRows();
		int brickCols = brick.getNumberOfColumns();
		
		for (int r =0; r<brickRows; r++) {
			for (int c = 0; c<brickCols; c++) {
				if (brick.hasTileAt(r, c)) {
					int row = r + brickRow;
					int col = c + brickCol;
					board[row][col] = brick.getTileImage();
				}
			}
		}
		FileIO.playClip(this, SNAP_SOUND);
		removeFilledRows();
		pickABrick();
	}
	
	private void removeFilledRows() {
		int count = 0;
		for (int row = ROWS -1; row>=0; row--) {
			boolean filled = true;
			for (int col = 0; col<COLS && filled; col++) {
				if (board[row][col]==null) {
					filled =false;
				}
			}
			if (filled) {
				for (int r = row; r>1; r--) {
					for (int c = 0; c<COLS; c++) {
						board[r][c] = board[r-1][c];
					}
				}
				row++;
				count++;
			}
		}
		calculateScore(count);
	}
	
	private void calculateScore(int count) {
		switch (count) {
		case 1:
			tetris.addToScore(1);
			break;
		case 2:
			tetris.addToScore(2);
			break;
		case 3:
			tetris.addToScore(3);
			break;
		case 4:
			tetris.addToScore(4);
			break;
		}
	}
	
	private void timedAction() {
		brick.fall(Brick.TILE_SIZE);
		
		// if can't fall further- set it into place
		if (!isLegal()) {
			brick.fall(-Brick.TILE_SIZE);
			drop();
		}
		else {
			repaint();
		}
	}

}
