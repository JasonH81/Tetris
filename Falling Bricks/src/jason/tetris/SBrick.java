package jason.tetris;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import jason.mycommonmethods.FileIO;

public class SBrick extends Brick {

	private static final String BRICK_FILE = "/redBrick.jpg";
	private static final boolean TILES[][][] = {{{false, true, true},{true, true, false}}, {{true, false}, {true, true}, {false, true}}};
	
	private static BufferedImage image;
	
	private int state = 0;
	
	public SBrick(int row, int col) {
		super(row, col);
		if (image == null) {
			image = FileIO.readImageFile(this, BRICK_FILE);
		}
	}
	
	public void draw(Graphics g) {
		int rows = TILES[state].length;
		int cols = TILES[state][0].length;
		
		for (int row = 0; row<rows; row++) {
			for (int col = 0; col<cols; col++) {
				if (TILES[state][row][col]) {
					int  tileX = x + (TILE_SIZE*col);
					int tileY = y + (TILE_SIZE *row);
					g.drawImage(image, tileX, tileY, null);
				}
			}
		}
	}
	
	public int getNumberOfRows() {
		return TILES[state].length;
	}
	
	public int getNumberOfColumns() {
		return TILES[state][0].length;
	}
	
	public void rotateLeft() {
		state--;
		if (state<0) {
			state = TILES.length -1;
		}
	}
	
	public void rotateRight() {
		state++;
		if (state>=TILES.length) {
			state = 0;
		}
	}
	
	public boolean hasTileAt(int row, int col) {
		return TILES[state][row][col];
	}
	
	public BufferedImage getTileImage() {
		return image;
	}

}
