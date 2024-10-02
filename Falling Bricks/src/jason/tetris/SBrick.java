package jason.tetris;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import jason.mycommonmethods.FileIO;

public class SBrick extends Brick {

	private static final String BRICK_FILE = "/redBrick.jpg";
	private static final boolean TILES[][] = {{false, true, true},{true, true, false}};
	
	private static BufferedImage image;
	
	public SBrick(int row, int col) {
		super(row, col);
		if (image == null) {
			image = FileIO.readImageFile(this, BRICK_FILE);
		}
	}
	
	public void draw(Graphics g) {
		int rows = TILES.length;
		int cols = TILES[0].length;
		
		for (int row = 0; row<rows; row++) {
			for (int col = 0; col<cols; col++) {
				if (TILES[row][col]) {
					int  tileX = x + (TILE_SIZE*col);
					int tileY = y + (TILE_SIZE *row);
					g.drawImage(image, tileX, tileY, null);
				}
			}
		}
	}
	
	public int getNumberOfRows() {
		return TILES.length;
	}
	
	public int getNumberOfColumns() {
		return TILES[0].length;
	}

}
