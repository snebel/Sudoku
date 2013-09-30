//Final Project Comp112: Sudoku, Sam Nebel & Nick Kokkinis, 5/8/11
/*This program solves a default sudoku board by filling each cell depending
  on the available row, cell and box information. It prints each solution iteration
  to the terminal, and a final solved board when complete.*/ 

import java.util.ArrayList;

/**
   SudokuGamePrint solves a default sudoku board and prints it.
*/
public class SudokuGamePrint
{
    //Constants to define a sudoku board
    final static int ROWS = 9;
    final static int COLUMNS = 9;
    final static int[][] board = new int[ROWS][COLUMNS]; 
   
    public static void main (String[] args)
    {
	//Hints, defined and added to an ArrayList	
	ArrayList<SudokuCell> hints = new ArrayList<SudokuCell>();
	SudokuCell c1 = new SudokuCell(0,0,9); hints.add(c1);	
	SudokuCell c2 = new SudokuCell(0,1,5); hints.add(c2);
	SudokuCell c3 = new SudokuCell(0,3,7); hints.add(c3);	
	SudokuCell c4 = new SudokuCell(0,4,1); hints.add(c4);
	SudokuCell c5 = new SudokuCell(0,6,3); hints.add(c5);	
	SudokuCell c6 = new SudokuCell(0,8,8); hints.add(c6);
	SudokuCell c7 = new SudokuCell(1,2,4); hints.add(c7);	
	SudokuCell c8 = new SudokuCell(1,7,6); hints.add(c8);
	SudokuCell c9 = new SudokuCell(1,8,5); hints.add(c9);	
	SudokuCell c10 = new SudokuCell(2,0,3); hints.add(c10);
	SudokuCell c11 = new SudokuCell(2,6,2); hints.add(c11);	
	SudokuCell c12 = new SudokuCell(3,4,2); hints.add(c12);
	SudokuCell c13 = new SudokuCell(3,5,8); hints.add(c13);	
	SudokuCell c14 = new SudokuCell(3,7,7); hints.add(c14);	
	SudokuCell c15 = new SudokuCell(4,1,3); hints.add(c15);
	SudokuCell c16 = new SudokuCell(4,3,5); hints.add(c16);
	SudokuCell c17 = new SudokuCell(4,4,4); hints.add(c17);
	SudokuCell c18 = new SudokuCell(4,6,1); hints.add(c18);	
	SudokuCell c19 = new SudokuCell(5,0,7); hints.add(c19);
	SudokuCell c20 = new SudokuCell(5,3,6); hints.add(c20);	
	SudokuCell c21 = new SudokuCell(5,4,9); hints.add(c21);
	SudokuCell c22 = new SudokuCell(6,4,8); hints.add(c22);	
	SudokuCell c23 = new SudokuCell(7,1,4); hints.add(c23);
	SudokuCell c24 = new SudokuCell(7,2,3); hints.add(c24);
	SudokuCell c25 = new SudokuCell(7,7,1); hints.add(c25);	
	SudokuCell c26 = new SudokuCell(8,1,7); hints.add(c26);
	SudokuCell c27 = new SudokuCell(8,2,2); hints.add(c27);	
	SudokuCell c28 = new SudokuCell(8,3,9); hints.add(c28);
	SudokuCell c29 = new SudokuCell(8,5,1); hints.add(c29);	
	SudokuCell c30 = new SudokuCell(8,6,5); hints.add(c30);	

	fillBoard(hints);
	System.out.println("\nOriginal Board:\n");
	printBoard();
	
	boolean solved = solved();

	while(!solved){
	    solveBoard();
	    printBoard();
	    solved = solved();
	}
	
	System.out.println("\nSolved Board:\n");
	printBoard();
    }

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

	
    
    
	    
	
