//Final Project Comp112: Sudoku, Sam Nebel & Nick Kokkinis, 5/8/11
/*This program defines a SudokuCell, a space on a sudoku board,
  with appropriate methods to be used later in our SudokuGame and 
  SudokuGraphics programs.*/

import java.util.ArrayList;

/**
   SudokuCell has a row, column, box, and play that can be manipulated.
*/
public class SudokuCell
{
    //Fields
    private int row;
    private int column;
    private int box;
    //An ArrayList containing all remaining possible plays for the cell
    private ArrayList<Integer> play;
    
    //Main Constructor
    public SudokuCell(int r, int c, int b, ArrayList<Integer> p)
    {
	row = r;
	column = c;
	box = b;
	play = p;
    }

    //Constructor for hints
    public SudokuCell(int r, int c, int p)
    {
	row = r;
	column = c;
	
	//Determine box location based on row and column
	boolean top = (row >=0 && row < 3);
	boolean mid = (row > 2 && row < 6);
	boolean bot = (row > 5 && row < 9);
	boolean left = (column >= 0 && column < 3);
	boolean cent = (column > 2 && column < 6);
	boolean right = (column > 5 && column < 9);

	//Assign appropriate box
	if (top && left) box = 0;
	if (top && cent) box = 1;
	if (top && right) box =2;
	if (mid && left) box = 3;
	if (mid && cent) box = 4;
	if (mid && right) box = 5;
	if (bot && left) box = 6;
	if (bot && cent) box = 7;
	if (bot && right) box = 8;


	play = new ArrayList<Integer>();
	play.add(p);
    }

    //Alternate constructor
    public SudokuCell(int r, int c, ArrayList<Integer> p)
    {
	row = r;
	column = c;
	
	//Determine box location based on row and column
	boolean top = (row >=0 && row < 3);
	boolean mid = (row > 2 && row < 6);
	boolean bot = (row > 5 && row < 9);
	boolean left = (column >= 0 && column < 3);
	boolean cent = (column > 2 && column < 6);
	boolean right = (column > 5 && column < 9);

	//Assign appropriate box
	if (r>=0 && r<3 && c>=0 && c<3) box = 0;
	if (top && cent) box = 1;
	if (top && right) box =2;
	if (mid && left) box = 3;
	if (mid && cent) box = 4;
	if (mid && right) box = 5;
	if (bot && left) box = 6;
	if (bot && cent) box = 7;
	if (bot && right) box = 8;

	play = p;
    }
    
    //Default constructor
     public SudokuCell()
    {
	row = 0;
	column = 0;
	box = 0;
	
	//Fill play with all possible plays
	play = new ArrayList<Integer>();
	for(int i = 0; i < 9; i++){
		play.add(i+1);
	}
    } 
    
    /**
       Returns the row of a SudokuCell.
       @return row in int form
    */
    public int getRow()
    {
	return row;
    }

    /**
       Returns the column of a SudokuCell.
       @return column in int form
    */
    public int getColumn()
    {
	return column;
    }
    
    /**
       Returns the box of a SudokuCell.
       @return box in int form
    */
    public int getBox()
    {
	return box;
    }
    
    /**
       Returns the play of a SudokuCell.
       @return play in int form
    */
    public int getPlay()
    {
	if (play.size() > 1) return 0;
	else return play.get(0);
    }
    
    /**
       Returns the list of possible plays of a SudokuCell.
       @return possible plays as an ArrayList
    */
    public ArrayList<Integer> getPlayList()
    {
	return play;
    }
    
    /**
       Returns the SudokuCell as a String.
       @return SudokuCell in String form
    */
    public String toString()
    {
	return "SudokuCell( "+row+", "+column+", "+box+", "+play+")";
    }
    
}




    
    
	   