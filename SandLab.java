import java.awt.*;
import java.util.*;

public class SandLab
{
	
  public static void main(String[] args)
  {
    SandLab lab = new SandLab(120, 80);
    lab.run();
  }
  
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  public static final int WATER = 3;
  public static final int ACID = 4;
  
  //do not add any more fields
  private int[][] grid;
  private SandDisplay display;
  
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    grid = new int[numRows][numCols];
    names = new String[5];
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    names[ACID] = "Acid";
    display = new SandDisplay("Falling Sand", numRows, numCols, names);    
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
	  grid[row][col] = tool;
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {
	  for (int row = 0; row < grid.length; row++)
	  {
		  for (int col = 0; col < grid[0].length; col++)
		  {
			  
			 if(grid[row][col] == EMPTY)
			 {
				 display.setColor(row,col,Color.BLACK);
			 }
			 else if(grid[row][col] == METAL)
			 {
				 display.setColor(row,col,Color.GRAY); 
			 }
			 else if(grid[row][col] == SAND)
			 {
				 display.setColor(row,col,Color.YELLOW); 
			 }
			 else if(grid[row][col] == WATER)
			 {
				 display.setColor(row,col,Color.BLUE);
			 }
			 else if(grid[row][col] == ACID)
			 {
				 display.setColor(row, col, Color.GREEN);
			 }
			 
		  }
	  }
  }



  //called repeatedly.
  //causes one random particle to maybe do something.
  public void step()
  {
	  int randomRow = (int) (Math.random() * grid.length);
	  int randomCol = (int) (Math.random() * grid[0].length);
	 
	  //used for the random direction of water
	  int randDirection = (int) (Math.random() * 3);
	  
	  if(grid[randomRow][randomCol] == SAND && randomRow != grid.length - 1)
	  {
		  if(grid[randomRow + 1][randomCol] == EMPTY)
		  {
		  grid[randomRow][randomCol] = EMPTY;
		  grid[randomRow + 1] [randomCol] = SAND;	  
		  }
		  else if (grid[randomRow + 1][randomCol] == WATER)
		  {
			  grid[randomRow][randomCol] = WATER;
			  grid[randomRow + 1][randomCol] = SAND;
			  
		  }
	  }
	  
	  else if(grid[randomRow][randomCol] == WATER) // uses randDirection for the random direction of water
	  {
		  if(randDirection == 0 && randomCol != grid[0].length - 1 && grid[randomRow][randomCol + 1] == EMPTY)
		  {
			  grid[randomRow][randomCol] = EMPTY;
			  grid[randomRow] [randomCol + 1] = WATER;	  
		  }
		  else if (randDirection == 1 && randomCol != 0 && grid[randomRow] [randomCol - 1] == EMPTY) 
		  {
			  grid[randomRow][randomCol] = EMPTY;
			  grid[randomRow] [randomCol - 1] = WATER;	  
		  }
		  else if(randDirection == 2 && randomRow != grid.length - 1 && grid[randomRow + 1] [randomCol] == EMPTY)
		  {
			  grid[randomRow][randomCol] = EMPTY;
			  grid[randomRow + 1][randomCol] = WATER;
		  }
	  }
	  
	  else if(grid[randomRow][randomCol] == ACID && randomRow != grid.length - 1 
		     && randomCol != grid[0].length - 1)
	  {
		  if(grid[randomRow + 1][randomCol] == EMPTY)
		  {
			  grid[randomRow][randomCol] = EMPTY;
			  grid[randomRow + 1] [randomCol] = ACID;	  
		  }
		  else if (grid[randomRow + 1][randomCol] == SAND || grid[randomRow + 1][randomCol] == METAL)
		  {
			  grid[randomRow + 1][randomCol] = ACID;	  
		  }
		  else if (randomRow != 0 && (grid[randomRow - 1][randomCol] == SAND || grid[randomRow - 1][randomCol] == METAL))
		  {
			  grid[randomRow - 1][randomCol] = ACID;
		  }
		  else if (grid[randomRow][randomCol + 1] == SAND || grid[randomRow][randomCol + 1] == METAL)
		  {
			  grid[randomRow][randomCol + 1] = ACID;
		  }
		  else if(randomCol != 0 && (grid[randomRow][randomCol - 1] == SAND || grid[randomRow][randomCol - 1] == METAL))
		  {
			  grid[randomRow][randomCol - 1] = ACID;
		  }
		  
	  }
  }
  
  //do not modify
  public void run()
  {
    while (true)
    {
      for (int i = 0; i < display.getSpeed(); i++)
        step();
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
    }
  }
}
