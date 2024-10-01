package jason.tetris;

public class Brick {
	public static final int TILE_SIZE = 30;
	
	protected int x;
	protected int y;
	
	public Brick(int row, int col) {
		x = col * TILE_SIZE;
		y = row * TILE_SIZE;
	}
}
