package modeller;
import display.Frame;
//import controller.Player;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Point;

public class Board {
	public static final int NONE = 0;

	public static final int BLACK = 1;
	public static final int WHITE = 2;

	public static final int BOARD_SIZE = 14; //changing this value changes the squares each row/column has
	public static final int DEFAULT_RESOLUTION = 630;//optimal value (no black background) is 673
	public static final int START_CORNER = 4;

	// public static final Color BOARD_COLOR = new Color(255,255,153);
	public static final Color BOARD_COLOR = new Color(153 + 51 + 20, 102 + 51 + 20, 153);// rose pink
	// color of board
	public static final Color GRID_LINE_COLOR = Color.BLACK;
	// public static final Color GRID_LINE_COLOR = new Color(153,102,0);
	// color of board's grid line

	public static final String OFF_BOARD_ERROR = "Piece must be placed entirely on the board.";
	public static final String ADJACENCY_ERROR = "Pieces of the same color cannot share edges with one another.";
	public static final String OVERLAP_ERROR = "Pieces cannot overlap.";
	public static final String START_ERROR = "Starting piece must occupy a starting point.";
	public static final String CORNER_ERROR = "Pieces must be connected to at least one other piece of the the same color by the corner.";

	public int[][] grid; //2D array that tracks board size
	private int[][] overlay;
	
	

	public Board() {
		grid = new int[BOARD_SIZE][BOARD_SIZE]; //initializes size of array
		overlay = new int[BOARD_SIZE][BOARD_SIZE];
		//reset(grid);
		//reset(overlay);
		/*
		 * The two lines above are unnecessary for operation. 
		 * 
		 */
	}

	//xOffset and yOffset are first declared here.
	public boolean isValidMove(Piece bp, int xOffset, int yOffset, boolean firstMove) throws IllegalMoveException {
		boolean corner = false;
		for (int x = 0; x < Piece.SHAPE_SIZE; x++) {
			for (int y = 0; y < Piece.SHAPE_SIZE; y++) {
				int value = bp.getValue(x, y);				
				boolean inBounds = isInBounds(x + xOffset, y + yOffset);

				if (inBounds) {
					//int gridValue = grid[x][y];
					int gridValue = grid[x + xOffset][y + yOffset];
					if (gridValue != NONE) {
						if (value == Piece.PIECE)
							throw new IllegalMoveException(OVERLAP_ERROR);
						if (gridValue == bp.getColor()) {
							if (value == Piece.ADJACENT)
								throw new IllegalMoveException(ADJACENCY_ERROR);
							if (value == Piece.CORNER)
								corner = true;
						}
					} else {
						// below is the detection for if a piece has been placed on a starting position
						// if (firstMove && value == Piece.PIECE && (new Point(x + xOffset, y +
						// yOffset).equals(getCorner(BLACK)))||new Point(x + xOffset, y +
						// yOffset).equals(getCorner(WHITE)))
						
						
						if (firstMove && value == Piece.PIECE
								&& (new Point(x + xOffset, y + yOffset).equals(getCorner(BLACK)))
								|| new Point(x + xOffset, y + yOffset).equals(getCorner(WHITE)))

						{
							corner = true;
						}											
						
						/*
						if (firstMove && value == Piece.PIECE
								&& new Point(x + xOffset, y + yOffset).equals(getCorner(BLACK))
								|| new Point(x+4, y+4).equals(getCorner(WHITE)))

						{
							corner = true;
						}
						*/
						
						//From Frame.java, xOff = selected.x - Piece.SHAPE_SIZE / 2;
						
					}
				} 
				else {
					//the if statement prevents some false preventions
					if (value == Piece.PIECE)
						throw new IllegalMoveException(OFF_BOARD_ERROR);
				}
			}
		}
		if (!corner) {
			throw new IllegalMoveException(firstMove ? START_ERROR : CORNER_ERROR);
		}
		// The move is valid

		return true;
	}

	public boolean isValidMove(Piece bp, int xOffset, int yOffset) throws IllegalMoveException {
		return isValidMove(bp, xOffset, yOffset, false);
	}

	/*
	The method below, placePiece is first called in Frame.java. 
	xOff becomes xOffset.
	xOff = selected.x - Piece.SHAPE_SIZE / 2;
	but Piece.SHAPE_SIZE / 2 is 3
	so
	xOff = selected.x - 3;
	*/
	public void placePiece(Piece bp, int xOff, int yOff, boolean firstMove) throws IllegalMoveException {
		
		isValidMove(bp, xOff, yOff, firstMove);		
		
		for (int x = 0; x < Piece.SHAPE_SIZE; x++) {
			for (int y = 0; y < Piece.SHAPE_SIZE; y++) {
				if (bp.getValue(x, y) == Piece.PIECE)
					grid[x + xOff][y + yOff] = bp.getColor();
			}
		}
				
	}

	
	public void placePiece(Piece bp, int xOff, int yOff) throws IllegalMoveException {
		placePiece(bp, xOff, yOff, false);
	}
	
	//method above might be removed as it does not appear to do anything useful 
	
	//displays location of selected hovering piece
	public Point getCoordinates(Point pixel, int res) {
		return new Point(pixel.x / (res / BOARD_SIZE), pixel.y / (res / BOARD_SIZE));
	}
	

	public void overlay(Piece bp, int xOff, int yOff) {
		reset(overlay);

		for (int x = 0; x < Piece.SHAPE_SIZE; x++) {
			for (int y = 0; y < Piece.SHAPE_SIZE; y++) {
				if (isInBounds(x + xOff - Piece.SHAPE_SIZE / 2, y + yOff - Piece.SHAPE_SIZE / 2)
						&& bp.getValue(x, y) == Piece.PIECE) {
					overlay[x + xOff - Piece.SHAPE_SIZE / 2][y + yOff - Piece.SHAPE_SIZE / 2] = bp.getColor();
				}
			}
		}
	}

	//method below is mandatory (needed for compilation, otherwise cannot use render(int)
	public BufferedImage render() {
		return render(DEFAULT_RESOLUTION); //draws board at specified resolution
	}
	

	public BufferedImage render(int size) {
		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		int cellSize = size / (BOARD_SIZE);
		Graphics2D g = (Graphics2D) image.getGraphics();

		for (int x = 0; x < BOARD_SIZE; x++) {
			for (int y = 0; y < BOARD_SIZE; y++) {
				g.setColor(getColor(grid[x][y]));
				if (overlay[x][y] != NONE) {
					g.setColor(blend(g.getColor(), getColor(overlay[x][y]), 0.1f));
				}
				g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
				g.setColor(GRID_LINE_COLOR);
				g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);

				if (grid[x][y] == NONE) {
					boolean corner = false;
					Point p = new Point(x, y);
					//Point q = new Point(x+xOffset, y+yOffset);

					/*
					 * For the rest of the code, note that "Corner" means starting point. The
					 * following if else blocks modify the color of the circles placed on the
					 * starting points.
					 */

					// "Black corner's" color
					if (getCorner(BLACK).equals(p)) {
						g.setColor(Color.BLACK);
						corner = true;
					}

					// white corner's color
					else if (getCorner(WHITE).equals(p)) {
						g.setColor(Color.BLACK);
						corner = true;
					}

					/*
					 * The reason why corners were previously differentiated was for purposes of
					 * customization. Previously, player 1 and player 2 had designated corners.
					 */

					/*
					 * else if (getCorner(GREEN).equals(p)) { g.setColor(getColor(GREEN)); corner =
					 * true; }
					 */

					/*
					 * else if (getCorner(YELLOW).equals(p)) { g.setColor(getColor(YELLOW)); corner
					 * = true; }
					 */
					if (corner) {
						g.fillOval(x * cellSize + cellSize / 2 - cellSize / 6,
								y * cellSize + cellSize / 2 - cellSize / 6, cellSize / 3, cellSize / 3);
					}
				}
			}
		}
		return image;
	}

	//critical method for color change and turn change
	private Color blend(Color c1, Color c2, float balance) {
		int r = (int) (c1.getRed() * balance + c2.getRed() * (1 - balance));
		int g = (int) (c1.getGreen() * balance + c2.getGreen() * (1 - balance));
		int b = (int) (c1.getBlue() * balance + c2.getBlue() * (1 - balance));
		return new Color(r, g, b);
	}

	
	public void resetOverlay() {
		reset(overlay);
	}
	//method above needed for when piece moves off board and you want to still display it

	//method below is called in the public Board constructor.
	
	private void reset(int[][] array) {
		for (int x = 0; x < BOARD_SIZE; x++)
			for (int y = 0; y < BOARD_SIZE; y++)
				array[x][y] = NONE;
	}

	private boolean isInBounds(int x, int y) {
		return (x >= 0 && y >= 0 && x < BOARD_SIZE && y < BOARD_SIZE);
	}

	private Point getCorner(int color) {
		switch (color) {
		case BLACK:
			return new Point(START_CORNER, START_CORNER);
		// point is actually (5,5) in the blokus coordinate system
		// (4,4) is only for the coordinate system

		case WHITE:
			return new Point(BOARD_SIZE - START_CORNER - 1, BOARD_SIZE - START_CORNER - 1);

		// case GREEN: return new Point(0, BOARD_SIZE - 1);
		// case YELLOW: return new Point(BOARD_SIZE - 1, BOARD_SIZE - 1);
		default:
			throw new IllegalArgumentException();
		}
	}

	// the method below controls the color of a piece
	public static Color getColor(int color) {
		switch (color) {
		case BLACK:
			return new Color(255, 0, 0);
		// case BLACK: new Color (51,51,51);
		// case WHITE: new Color (204,204,204);

		case WHITE:
			return new Color(0, 102, 0);
		// case WHITE: return Color.WHITE;
		// case YELLOW: return Color.YELLOW;
		// case GREEN: return new Color(0, 128, 0);
		default:
			return BOARD_COLOR;
		}
	}

	public static String getColorName(int color) {
		switch (color) {
		case BLACK:
			return "Black";
		case WHITE:
			return "White";
		// case YELLOW: return "Yellow";
		// case GREEN: return "Green";
		default:
			return "Unknown";
		}
	}

	public static class IllegalMoveException extends Exception {
		public IllegalMoveException() {
			super();
		}

		public IllegalMoveException(String message) {
			super(message);
		}
	}
}
