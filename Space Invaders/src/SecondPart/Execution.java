package SecondPart;

import java.util.Scanner;

public class Execution {
	/**
	 * We'll simply create an instance of class which will start the display and game
	 * loop.
	 * @param argv The arguments that are passed into our game
	 */
	public static void main(String argv[]) {
		/////////////////TO BE FILLED BY STUDENTS/////////////////////////////////////////////			
		//Create the game object and pass as constructor's parameters the 
		//size of the board X and Y, number of row/columns of invaders 
		Scanner sc= new Scanner(System.in);
    	System.out.println("Please introduce the number of rows of the game board");
    	int sizeBoardX= sc.nextInt();
    	System.out.println("Please introduce the number of columns of the game board");
    	int sizeBoardY=sc.nextInt();
    	System.out.println("Please introduce the number of rows of invaders:");
    	int nRowInvaders=sc.nextInt();
    	System.out.println("Please introduce the number of columns of invaders");
    	int nColInvaders=sc.nextInt();
    	new Game(sizeBoardX, sizeBoardY, nRowInvaders, nColInvaders);
		// Start the game loop
		gameLoop();
		///////////////////////////////////////////////////////////////////////////
	}
}
