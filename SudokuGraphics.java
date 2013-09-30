//Final Project Comp112: Sudoku, Sam Nebel & Nick Kokkinis, 5/8/11
/*SudokuGraphics opens a Jpanel sudoku board that can be filled with inputs
  and then solved using two different ActionListeners. It solves the board 
  by adding the inputs to a sudoku board and using methods from the
  SudokuGame class to solve the remaining cells.*/

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
   SudokuGraphics opens a Jpanel sudoku board that can be filled with inputs
   and then solved.
*/
public class SudokuGraphics
{    
    //Constants
    private static final int WIDTH=700;
    private static final int HEIGHT=700;
    private static final int NUM=9;

    public static void main(String[] args)
    {
	//Create and add a JFrame
	JFrame frame = new JFrame();
	frame.setSize(WIDTH,HEIGHT);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	//Create a two dimensional array of buttons
	final JButton[][] buttons = new JButton[NUM][NUM];
       
	//Create a JPanel
	final JPanel panel = new JPanel();
	panel.setLayout(new GridLayout(NUM+1,NUM));
	
	//Create JButtons
	for(int i=0;i<NUM;i++){
	    for(int j=0;j<NUM;j++){
		buttons[i][j] = new JButton("");
	    }}	

	//Create a solver JButton
	final JButton solveButton = new JButton("Solve");

	//Add JButtons to panel
	for(int i=0;i<NUM;i++){
	    for(int j=0;j<NUM;j++){
		panel.add(buttons[i][j]);
	    }}
	panel.add(solveButton);
	
	//Create a ClickListener class
	class ClickListener implements ActionListener
	{
	    //Fields
	    int x,y;

	    public ClickListener(int xval, int yval)
	    {
		x=xval;
		y=yval;
	    }

	    /**
	       actionPerformed recieves a click and opens a JOptionPane
	       that prompts a user for sudoku cell input, then sets the input
	       as the button text if it is valid.
	       @param event click opens a pane and recieves input 
	    */
	    public void actionPerformed(ActionEvent event)
	    {	
		String hint = JOptionPane.showInputDialog("Enter an integer 1-9:");
		int num = Integer.parseInt(hint);
		if(num>0 && num<10){
		    buttons[x][y].setText(hint);
		    buttons[x][y].setForeground(Color.RED);
		    buttons[x][y].setOpaque(true);}
		else JOptionPane.showMessageDialog
			 (null,hint+" is not a valid sudoku input.");
	    }
	}
	
	//Add new ClickListener for every button
	for(int i=0;i<NUM;i++){
	    for(int j=0;j<NUM;j++){
		buttons[i][j].addActionListener(new ClickListener(i,j));
	    }}

	//Create a ClickListenerSolve class
	class ClickListenerSolve implements ActionListener
	{
	   
	    /**
	       actionPerformed recieves a click and solves the sudoku board
	       using methods in the SudokuGame class. 
	       @param event click solves the sudoku board
	    */
	    public void actionPerformed(ActionEvent event)
	    {
		solveButton.setText("Solved!");	
		
		//Construct an ArrayList to store all hints previously
		//entered using the other buttons
		ArrayList<SudokuCell> hints = new ArrayList<SudokuCell>();
	
		for(int i=0;i<NUM;i++){
		    for(int j=0;j<NUM;j++){
			String text = buttons[i][j].getText();
			buttons[i][j].setText(text);
			buttons[i][j].setForeground(Color.RED);
			buttons[i][j].setOpaque(true);
			
			if (text=="") text = "0"; 
			int x = Integer.parseInt(text);
			
			SudokuCell hint = new SudokuCell(i,j,x);
			hints.add(hint);	
		    }}
		

		SudokuGame.fillBoard(hints);
		System.out.println("\nOriginal Board:\n");
		SudokuGame.printBoard();
	
		boolean solved = SudokuGame.solved();

		while(!solved){
		    SudokuGame.solveBoard();
		    //SudokuGame.printBoard();
		    solved = SudokuGame.solved();
		}
	
		System.out.println("\nSolved Board:\n");
		SudokuGame.printBoard();

		//Get solved board from SudokuGame
		int [][] board = SudokuGame.getBoard();

		//Fill all buttons with the solved values
		for(int i=0;i<NUM;i++){
		    for(int j=0;j<NUM;j++){
			buttons[i][j].setText(""+board[i][j]);
			buttons[i][j].setForeground(Color.BLACK);
			buttons[i][j].setOpaque(true);
		    }
		}
		
	    }
	}
	
	//add new ClickListenerSolve for solve button
      	solveButton.addActionListener(new ClickListenerSolve());
	
	//add panel to the frame and make it visible
	frame.add(panel);
	frame.setVisible(true);
    }
}	