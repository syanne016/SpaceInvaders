package FirstPart;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	static char INVADER_SHOT='*';
	static char SHIP_SHOT= '|';
	static char INVADER_SYMBOL='E';
	static char SHIP_SYMBOL='_';
    static int BOARD_COLS;
    public static void generateInvaders( Invader invaders[],char board[]){
    	for(int a=0;a<invaders.length;a++){
    		invaders[a]=new Invader(a,a);
    		board[a]=INVADER_SYMBOL;
    		if(a>invaders.length-1-BOARD_COLS && a<invaders.length-1){
    			invaders[a].canShoot=true;
    		}
    	}
		}

    public static void printBoard(char board[], Ship myShip){
    	for(int a=0; a<board.length;a++){
    		if(((a % BOARD_COLS)==0) && a!=0){
    			System.out.println();
    		}
    		if(a==(myShip.position)){
    			System.out.print(SHIP_SYMBOL);
    		}
    		else{
    		System.out.print(board[a]);
    		}
    	}
    	System.out.println();
    	    	
    }
    public static boolean  moveAndShoot(char board[], Ship myShip){
    	@SuppressWarnings("resource")
		Scanner sc= new Scanner(System.in);
    	if(myShip.lives==0){
			return true;
		}
    		System.out.println("Where do you want to move to?");
        	String movement= sc.next();
    		if(movement.equalsIgnoreCase("a")){
    			if(board[myShip.position-1]==INVADER_SHOT){
    				myShip.lives=myShip.lives-1;
    			}
    			myShip.position=myShip.position-1;    			
    			while((myShip.position<=myShip.minPosition-1)||(myShip.position>=myShip.maxPosition)){
    				System.out.println("That position is not valid");
    				System.out.println("Introduce another movement");
    				movement=sc.next();
    				myShip.position=myShip.position+1;
    				
    			}
    			int a =myShip.position;
    			 board[a]=SHIP_SHOT;
    		}
    		else if(movement.equalsIgnoreCase("d")){
    			if(myShip.position+1>myShip.maxPosition){
    				
    			}
    			else if(board[myShip.position+1]==INVADER_SHOT){
    				myShip.lives=myShip.lives-1;
    			}
    			myShip.position=myShip.position+1;
    			while(myShip.position>=myShip.maxPosition+1 || myShip.position<=myShip.minPosition){
    				System.out.println("That position is not valid");
    				System.out.println("Introduce another movement");
    				movement=sc.next();  
    				myShip.position=myShip.position-1;  				
    			}  
    			
        		board[myShip.position]=SHIP_SHOT;
    			}
    		
    		else if(movement.equalsIgnoreCase("s")){
    			if(board[myShip.position-1]==INVADER_SHOT){
    				myShip.lives=myShip.lives-1;
    			}
    			int a =myShip.position;
   			 	board[a]=SHIP_SHOT;
    		}
    		else{
    			System.out.println("That movement does not exists, please introduce another movement");
    			movement=sc.next();
    		}
    	return false;    	
    }
    public static ArrayList<Invader> canShoot(Invader [] invaders){
    	ArrayList<Invader> shoot= new ArrayList<Invader>();
    	for(int a=0;a<invaders.length;a++){
    		if(invaders[a].canShoot==true){
    			shoot.add(invaders[a]);
    		}
    	}
    	
    	return shoot;
    }
    public static void shootTheShip(char board[], ArrayList<Invader> shoot){
    	int randomI= (int) (Math.random()*shoot.size());
    	Invader randomIn=shoot.get(randomI);
    	board[randomIn.position+ BOARD_COLS] =INVADER_SHOT;
    	

    }
    public static boolean updateInvaders(Invader [] invaders){
    	boolean h=true;
       	for(int a=0;a<invaders.length;a++){
    		if(invaders[a].isAlive==true){
    			h=false;
    		}
//    		if(invaders[a+BOARD_COLS].isAlive==false && a<invaders.length-1-BOARD_COLS && invaders[a].isAlive==true){
//    			invaders[a].canShoot=true;
//    		}
    	}
       	return h;
    }
    public static boolean updateShooting(char board[], Ship myShip, Invader [] invaders){
    	for(int a=0;a<board.length;a++){
    		if(board[a]==SHIP_SHOT){
    			board[a]=' ';
    			if(a>=0 && a<BOARD_COLS){
    				board[a]=' ';
    			}
    			else if(board[a-BOARD_COLS]==INVADER_SYMBOL){
    				invaders[a-BOARD_COLS].isAlive=false;
    				invaders[a-BOARD_COLS].canShoot=false;
    				if(a-2*BOARD_COLS>=0){
    					invaders[a-2*BOARD_COLS].canShoot=true;
    				}
    				board[a-BOARD_COLS]=' ';
    				myShip.score+=1;
    			}
    			else if(board[a-BOARD_COLS]==INVADER_SHOT){
    				board[a-BOARD_COLS]=' ';
    			}
    			else{
    			board[a-BOARD_COLS]=SHIP_SHOT;
    			}
    		}
    	}
    	for(int b=board.length-1;b>=0;b--){
    		if(board[b]==INVADER_SHOT){
    			board[b]=' ';
    			if(myShip.lives==0){
					return true;
				}
    			if(b<=board.length-1 && b>board.length-1-BOARD_COLS){
    				board[b]=' ';
    			}
    					
    				
    			else if(board[b+BOARD_COLS]==SHIP_SHOT){
        				board[b+BOARD_COLS]=' ';
    				}
    			else {
    					board[b+BOARD_COLS]=INVADER_SHOT;
    				}
    			}
    		}
    	
    	return false;
    }
    public static void main(String[] args){
    	Scanner sc= new Scanner(System.in);
    	System.out.println("Please introduce the number of rows of the game board");
    	int rows= sc.nextInt();
    	System.out.println("Please introduce the number of columns of the game board");
    	BOARD_COLS=sc.nextInt();
    	System.out.println("Please introduce the number of rows of invaders:");
    	int rowsI=sc.nextInt();
    	System.out.println("Please introduce the number of lives of the ship");
    	int shiplives=sc.nextInt();
    	System.out.println("=== GAME STARTS ===");
    	System.out.println(" ");
    	
    	char board[]= new char [rows*BOARD_COLS];
    	Invader boardI[]=new Invader [rowsI*BOARD_COLS];
    	int b= BOARD_COLS/2;
    	int c= board.length-b;
    	int d= (board.length- BOARD_COLS);
    	Ship myShip= new Ship(c, shiplives,c,board.length-1,d);
    	generateInvaders(boardI, board);
    	printBoard( board ,myShip);
    	
		boolean method1=moveAndShoot(board,myShip);
		boolean method3=updateShooting(board, myShip, boardI);
		printBoard(board,myShip);
		System.out.println("Your score is: "+myShip.score);
		System.out.println("You have "+myShip.lives+" lives left.");
		boolean method2= updateInvaders(boardI);
		
		
        int turn=1;
    	while(method1==false && method2==false && method3==false){
        	    method1=moveAndShoot(board, myShip);
        	    method3=updateShooting(board,myShip,boardI);
        	    if (canShoot(boardI).size()==1){
					turn=1;
        	    }
        	    else if(canShoot(boardI).size()==0){
        	    	
        	    }
        	    else if(turn%2==0){
    				canShoot(boardI);
					shootTheShip(board, canShoot(boardI));
    			}
    			printBoard(board,myShip);
    			System.out.println("Your score is: "+myShip.score);
    			System.out.println("You have "+myShip.lives+" lives left.");
    			method2=updateInvaders(boardI);
    			turn++;
    		}
        if(method2==true){
        	System.out.println("==== GAME OVER ====");
			System.out.println("Ship wins. Congratulations!");
		}
    	if (method1==true || method3==true){
    		System.out.println("==== GAME OVER ====");
			System.out.println("Invaders win. Try again.");
    		
    	}
    }
}