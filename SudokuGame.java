//Final Project Comp112: Sudoku, Sam Nebel & Nick Kokkinis, 5/8/11
/*This program creates a sudoku board, and definies the appropriate
  methods to be used by SudokuGraphics, to fill the board, solve, and print it.*/ 

import java.util.ArrayList;

/**
   SudokuGame creates a sudoku board and methods to be called
   in the SudokuGraphics program to solve it.
*/
public class SudokuGame
{
    //Constants to define a sudoku board
    final static int ROWS = 9;
    final static int COLUMNS = 9;
    final static int[][] board = new int[ROWS][COLUMNS]; 
   
    /**
       Fills the board with the play of an input cell.
       @param c the SudokuCell to fill the board with
    */
    public static void fillCell(SudokuCell c)
    {
	board[c.getRow()][c.getColumn()] = c.getPlay();
    }

    /**
       Fills the board with the plays of each SudokuCell in
       the input ArrayList of SudokuCells.
       @param al the ArrayList of SudokuCells to fill the board with
    */
    public static void fillBoard(ArrayList<SudokuCell> al)
    {
    	for(SudokuCell e: al){
	    fillCell(e);}
    }

    /**
       Prints the sudoku board to the console.
    */
    public static void printBoard()
    {
	System.out.println("");
	for(int i=0; i<board.length; i++){
	    for(int j=0; j<board.length; j++){
		//Add vertical board dividers
		if (j==3 || j==6) {System.out.print("| ");}
		//Print 0 as a blank space
		if (board[i][j]==0) System.out.print("  ");
		else System.out.print(board[i][j]+" "); 
	    }
	    //Add horizontal board dividers
	    if (i==2 || i==5) {System.out.print
		    ("\n----------------------");}
	    System.out.println("");
	}
	System.out.println("");
    }

    /**
       Narrows down the list of possible plays by removing the intersection
       of the list of row elements, column elements, and box elements, from
       the list of possible plays.
       @parm c the SudokuCell to solve
    */
    public static void solveCell(SudokuCell c)
    {
	//Construct an ArrayList of all plays in the same row as
	//the input SudokuCell
	ArrayList<Integer> row = new ArrayList<Integer>();
	for(int j=0; j<board.length; j++){
	    int x = board[c.getRow()][j];
	    row.add(x);
	}

	//Construct an ArrayList of all plays in the same column as
	//the input SudokuCell
	ArrayList<Integer> column = new ArrayList<Integer>();
	for(int i=0; i<board.length; i++){
	    int x = board[i][c.getColumn()];
	    column.add(x);
	}

	//Construct an ArrayList of all plays in the same box as
	//the input SudokuCell
	ArrayList<Integer> box = new ArrayList<Integer>();
	for(int i=0; i<board.length; i++){
	    for(int j=0; j<board.length; j++){
		SudokuCell cell = new SudokuCell(i,j,0);
		if (cell.getBox() == c.getBox()){
		    box.add(board[i][j]);}
	    }
	}

	//Create an ArrayList with all possible plays
	ArrayList<Integer> possibles = new ArrayList<Integer>();
	for(int i=1; i<10; i++)
	    possibles.add(i);
	
	//3 new Arraylists that will be the intersection of
	//the row, box, and column ArrayLists and the ArrayList of
	//possible plays.
	ArrayList<Integer> introw = new ArrayList<Integer>();
	ArrayList<Integer> intcolumn = new ArrayList<Integer>();
	ArrayList<Integer> intbox = new ArrayList<Integer>();

	//finds intersection of row and possibles
	introw = intersection(row, possibles);
	//removes plays in the same row from list of possibles
	possibles = subtract(possibles, introw);

	//finds intersection of column and possibles
	intcolumn = intersection(column, possibles);
	//removes plays in the same column from list of possibles
	possibles = subtract(possibles, intcolumn);

	//finds intersection of box and possibles
	intbox = intersection(box, possibles);
	//removes plays in the same box from list of possibles
	possibles = subtract(possibles, intbox);

	//adds a play to the board if the arraylist of possibles
	//is narrowed down to only one choice
	if (possibles.size()==1){
	    board[c.getRow()][c.getColumn()] = possibles.get(0);
	}
    }

    /**
       Exectues solveCell for each space on the sudoku board that
       hasn't yet been filled.
     */
    public static void solveBoard()
    {
	//defines an ArrayList of all possible plays for
	//unfilled sudoku cells containing the integers 1-9
	ArrayList<Integer> possibilities = new ArrayList<Integer>();
	for(int i=1; i<10; i++)
	    possibilities.add(i);

	//checks each element of the board, and calls "solveCell()"
	//for all unsolved cells
	    for(int i=0; i<board.length; i++){
		for(int j=0; j<board.length; j++){
		    if(board[i][j]==0){
			SudokuCell c = new SudokuCell
			    (i,j, possibilities);
			solveCell(c);
		    }}}
    }    

    /**
       Checks whether the sudoku board has been completely solved.
       @return true if there are no empty spaces, false if there are
     */
    public static boolean solved()
    {
	boolean solved = true;

	//checks whether each element of the board has been
	//filled or is empty
	for(int i=0; i<board.length; i++){
	    for(int j=0; j<board.length; j++){
		if(board[i][j]==0) solved=false;
	    }
	}
	return solved;
    }

    /**
       Finds the ArrayList representing the intersection of 2 ArrayLists.
       @param al1 the first ArrayList
       @param al2 the second ArrayList
       @return the ArrayList containing all common elements
     */
    public static ArrayList<Integer> intersection
	(ArrayList<Integer> al1, ArrayList<Integer> al2)
    {
	ArrayList<Integer> intersection = new ArrayList<Integer>();

	for (int e : al1){
	    //member method defined below
	    if (member(e, al2)) intersection.add(e);
	}
	return intersection;
    }

    /**
       Determines whether an integer is an element in an ArrayList.
       @param x the int to search for
       @param al the ArrayList to search in
       @return true if x is found in al, false if not
     */
    public static boolean member(int x, ArrayList<Integer> al)
    {
	boolean mem = false;

	for (int e : al){
	    if (e == x) mem = true;
	}
	return mem;	   
    }

    /**
       Removes all elements of an ArrayList al2 from an ArrayList al1
       @param al1 the ArrayList to remove elements from
       @param al2 the ArrayList to be removed
       @return al1 with the elements of al2 removed
     */
    public static ArrayList<Integer> subtract
	(ArrayList<Integer> al1, ArrayList<Integer> al2)
    {
	//subtract al2 from al1
	for(int e : al2){
	    al1.remove(al1.indexOf(e));
	}
	return al1;
    }

    /**
       Returns the sudoku board in its current state.
       @return the 2D array of ints that represents the board
     */
    public static int[][] getBoard()
    {
	return board;
    }
     
}

	
    
    
	    
	
