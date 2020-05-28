package modeller;

//import controller.Player;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Piece
{
   public static final int SHAPE_SIZE = 7; //size of shape array
   //public static final int SHAPE_SIZE = 7;
   public static final int PIECE = 3;
   public static final int ADJACENT = 2;
   public static final int CORNER = 1;
   public static final int BLANK = 0;
   /*
    * Each piece is represented with a 7x7 grid. Squares of a piece
    * are denoted with the number 3. The area adjacent to a piece
    * (where same color pieces cannot be placed) is denoted with 2. 
    * Corner squares are denoted with 1. Blank squares are just 0.
    * 
    */
   public static int[][][] shapes;
   public static int i;
   
   public static final int DEFAULT_RESOLUTION = 120;
   
   private int[][] grid;
   private int color;
   
   
   public Piece(int[][] shape, int color)
   {
	  /*
      if (shape.length != SHAPE_SIZE || shape[0].length != SHAPE_SIZE)
      {
         throw new IllegalArgumentException("Shape array must be 7x7.");
      }
      */
      grid = (int[][]) shape.clone();
      this.color = color;
      //absences of this line makes the pieces the same color as the board.
      //Game still works fine though.
   }
   
   public void rotateClockwise()
   {
      int[][] temp = new int[SHAPE_SIZE][SHAPE_SIZE];
      
      for (int x = 0; x < SHAPE_SIZE; x++)
         for (int y = 0; y < SHAPE_SIZE; y++)
            temp[SHAPE_SIZE - y - 1][x] = grid[x][y];
            
      grid = temp;
   }
   
   public void rotateCounterClockwise()
   {
      int[][] temp = new int[SHAPE_SIZE][SHAPE_SIZE];
      
      for (int x = 0; x < SHAPE_SIZE; x++)
         for (int y = 0; y < SHAPE_SIZE; y++)
            temp[y][SHAPE_SIZE - x - 1] = grid[x][y];
            
      grid = temp;
   }
   
   public void flipOver()
   {
      int[][] temp = new int[SHAPE_SIZE][SHAPE_SIZE];
      
      for (int x = 0; x < SHAPE_SIZE; x++)
         for (int y = 0; y < SHAPE_SIZE; y++)
            temp[SHAPE_SIZE - x - 1][y] = grid[x][y];
            
      grid = temp;
   }
   
   public int getValue(int x, int y)
   {
      return grid[x][y];
   }
   
   public int getColor()
   {
      return color;
   }
   
   //Get points counts the number of squares on a piece.
   public int getPoints()
   {
      int points = 0;
      for (int y = 0; y < SHAPE_SIZE; y++)
         for (int x = 0; x < SHAPE_SIZE; x++)
            if (grid[x][y] == PIECE) points++;
      return points;
   }
   
   public BufferedImage render(int size)
   {
      BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
      int cellSize = size / (SHAPE_SIZE);
      Graphics2D g = (Graphics2D) image.getGraphics();
      
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, size, size);
      
      for (int x = 0; x < SHAPE_SIZE; x++)
      {
         for (int y = 0; y < SHAPE_SIZE; y++)
         {
            if (grid[x][y] == PIECE)
            {
               g.setColor(Board.getColor(color));
               g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
               g.setColor(Color.BLACK);
               g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
         }
      }
      return image;
   }
   
   public String toString()
   {
      StringBuffer sb = new StringBuffer();
      for (int y = 0; y < SHAPE_SIZE; y++)
      {
         for (int x = 0; x < SHAPE_SIZE; x++)
         {
            sb.append(grid[x][y]);
            sb.append(" ");
         }
         sb.append("\n");
      }
      return sb.toString();
   }
   
   public static int[][][] getAllShapes()
   {
      shapes = new int[21][SHAPE_SIZE][SHAPE_SIZE];
      i = 0;
      /*
       * Below I have coded each blokus duo piece so I will not have to use
       * sprites. Each piece is a 2-d array where a 3 is part of the piece,
       * a 2 is a same-color exclusion zone, and a 1 is a corner.
       * 
       * Piece names can be found here:
       * http://blokusstrategy.com/piece-names/
       */
      
      
      //Piece I5
      shapes[i++] = new int[][] { // * * * * *
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0},
         {1, 2, 2, 2, 2, 2, 1},
         {2, 3, 3, 3, 3, 3, 2},
         {1, 2, 2, 2, 2, 2, 1},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      //Piece L5
      shapes[i++] = new int[][] { // * * * *
         {0, 0, 0, 0, 0, 0, 0}, //   *
         {0, 1, 2, 1, 0, 0, 0},
         {0, 2, 3, 2, 2, 2, 1},
         {0, 2, 3, 3, 3, 3, 2},
         {0, 1, 2, 2, 2, 2, 1},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      //Piece N
      shapes[i++] = new int[][] { //   * * *
         {0, 0, 1, 2, 1, 0, 0},   // * *
         {0, 0, 2, 3, 2, 0, 0},
         {0, 0, 2, 3, 2, 1, 0},
         {0, 0, 2, 3, 3, 2, 0},
         {0, 0, 1, 2, 3, 2, 0},
         {0, 0, 0, 1, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      //Piece Y
      shapes[i++] = new int[][] { //   *
         {0, 0, 0, 0, 0, 0, 0}, // * * * *
         {0, 0, 1, 2, 1, 0, 0},
         {0, 1, 2, 3, 2, 2, 1},
         {0, 2, 3, 3, 3, 3, 2},
         {0, 1, 2, 2, 2, 2, 1},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      //Piece F
      shapes[i++] = new int[][] { //   *
         {0, 0, 0, 0, 0, 0, 0}, // * * *
         {0, 0, 1, 2, 1, 0, 0}, //   *
         {0, 1, 2, 3, 2, 1, 0},
         {0, 2, 3, 3, 3, 2, 0},
         {0, 1, 2, 2, 3, 2, 0},
         {0, 0, 0, 1, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      //Piece X
      shapes[i++] = new int[][] { // *
         {0, 0, 0, 0, 0, 0, 0}, // * * *
         {0, 0, 1, 2, 1, 0, 0}, //   *
         {0, 1, 2, 3, 2, 1, 0},
         {0, 2, 3, 3, 3, 2, 0},
         {0, 1, 2, 3, 2, 1, 0},
         {0, 0, 1, 2, 1, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      //Piece U
      shapes[i++] = new int[][] { // * * *
         {0, 0, 0, 0, 0, 0, 0},   // *   *
         {0, 0, 0, 0, 0, 0, 0},
         {0, 1, 2, 2, 2, 1, 0},
         {0, 2, 3, 3, 3, 2, 0},
         {0, 2, 3, 2, 3, 2, 0},
         {0, 1, 2, 1, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      //Piece P
      shapes[i++] = new int[][] { // * *
         {0, 0, 0, 0, 0, 0, 0}, // * * *
         {0, 0, 0, 0, 0, 0, 0},
         {0, 1, 2, 2, 2, 1, 0},
         {0, 2, 3, 3, 3, 2, 0},
         {0, 1, 2, 3, 3, 2, 0},
         {0, 0, 1, 2, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      //Piece W
      shapes[i++] = new int[][] { //   *
         {0, 0, 0, 0, 0, 0, 0}, //   * *
         {0, 0, 0, 1, 2, 1, 0}, // * *
         {0, 0, 1, 2, 3, 2, 0},
         {0, 1, 2, 3, 3, 2, 0},
         {0, 2, 3, 3, 2, 1, 0},
         {0, 1, 2, 2, 1, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      //Piece T5
      shapes[i++] = new int[][] { // *
         {0, 0, 0, 0, 0, 0, 0},   // * * *
         {0, 0, 1, 2, 1, 0, 0},   // *
         {0, 0, 2, 3, 2, 0, 0},
         {0, 1, 2, 3, 2, 1, 0},
         {0, 2, 3, 3, 3, 2, 0},
         {0, 1, 2, 2, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      //Piece V5
      shapes[i++] = new int[][] { // *
         {0, 0, 1, 2, 1, 0, 0},   // *
         {0, 0, 2, 3, 2, 0, 0},   // * * *
         {0, 0, 2, 3, 2, 2, 1},
         {0, 0, 2, 3, 3, 3, 2},
         {0, 0, 1, 2, 2, 2, 1},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      //Piece Z5
      shapes[i++] = new int[][] { // *
         {0, 0, 0, 0, 0, 0, 0},   // * * *
         {0, 0, 1, 2, 2, 1, 0},   //     *
         {0, 0, 2, 3, 3, 2, 0},
         {0, 1, 2, 3, 2, 1, 0},
         {0, 2, 3, 3, 2, 0, 0},
         {0, 1, 2, 2, 1, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      //Piece I4
      shapes[i++] = new int[][] { // * * * *
         {0, 0, 1, 2, 1, 0, 0},   // 
         {0, 0, 2, 3, 2, 0, 0},
         {0, 0, 2, 3, 2, 0, 0},
         {0, 0, 2, 3, 2, 0, 0},
         {0, 0, 2, 3, 2, 0, 0},
         {0, 0, 1, 2, 1, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      //Piece Z4
      shapes[i++] = new int[][] { // * *
         {0, 0, 0, 0, 0, 0, 0},   //   * *
         {0, 0, 1, 2, 2, 1, 0},
         {0, 1, 2, 3, 3, 2, 0},
         {0, 2, 3, 3, 2, 1, 0},
         {0, 1, 2, 2, 1, 0, 0},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      //Piece O
      shapes[i++] = new int[][] { // * *
         {0, 0, 0, 0, 0, 0, 0}, //   * *
         {0, 1, 2, 2, 1, 0, 0},
         {0, 2, 3, 3, 2, 0, 0},
         {0, 2, 3, 3, 2, 0, 0},
         {0, 1, 2, 2, 1, 0, 0},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      //Piece T4
      shapes[i++] = new int[][] { // *
         {0, 0, 0, 0, 0, 0, 0}, // * * *
         {0, 0, 1, 2, 1, 0, 0},
         {0, 1, 2, 3, 2, 1, 0},
         {0, 2, 3, 3, 3, 2, 0},
         {0, 1, 2, 2, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      //Piece L4
      shapes[i++] = new int[][] { //   *
         {0, 0, 0, 0, 0, 0, 0}, // * * *
         {0, 0, 0, 0, 0, 0, 0},
         {0, 1, 2, 2, 2, 1, 0},
         {0, 2, 3, 3, 3, 2, 0},
         {0, 1, 2, 2, 3, 2, 0},
         {0, 0, 0, 1, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      //Piece I3
      shapes[i++] = new int[][] { // 
         {0, 0, 0, 0, 0, 0, 0},   // * * *
         {0, 0, 0, 0, 0, 0, 0},
         {0, 1, 2, 2, 2, 1, 0},
         {0, 2, 3, 3, 3, 2, 0},
         {0, 1, 2, 2, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      //Piece V3
      shapes[i++] = new int[][] { // *
         {0, 0, 0, 0, 0, 0, 0},   // * *
         {0, 0, 1, 2, 1, 0, 0},
         {0, 0, 2, 3, 2, 1, 0},
         {0, 0, 2, 3, 3, 2, 0},
         {0, 0, 1, 2, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      //Piece 2
      shapes[i++] = new int[][] { // * *
         {0, 0, 0, 0, 0, 0, 0},   // 
         {0, 0, 1, 2, 1, 0, 0},
         {0, 0, 2, 3, 2, 0, 0},
         {0, 0, 2, 3, 2, 0, 0},
         {0, 0, 1, 2, 1, 0, 0},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      //Piece 1
      shapes[i++] = new int[][] { // *
         {0, 0, 0, 0, 0, 0, 0},   // 
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 1, 2, 1, 0, 0},
         {0, 0, 2, 3, 2, 0, 0},
         {0, 0, 1, 2, 1, 0, 0},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      return shapes;
   }
}
