package SecondPart;

import java.util.ArrayList;
	
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Programming 13-14
 * @author Nerea
 */
public class Game extends Canvas {
	/** The stragey that allows us to use accelerate page flipping */
	private BufferStrategy strategy;
	/** True if the game is currently "running", i.e. the game loop is looping */
	private boolean gameRunning = true;
	/** The message to display which waiting for a key press */
	private String message = "";
	/** True if we're holding up game play until a key has been pressed */
	private boolean waitingForKeyPress = true;
	/** True if the left cursor key is currently pressed */
	private boolean leftPressed = false;
	/** True if the right cursor key is currently pressed */
	private boolean rightPressed = false;
	/** True if we are firing */
	private boolean firePressed = false;
	/** True if game logic needs to be applied this loop, normally as a result of a game event */
	private boolean logicRequiredThisLoop = false;
	
	
	
	/////////////////TO BE FILLED BY STUDENTS/////////////////////////////////////////////
	/** The list of all the invaders that exist in our game */
	private ArrayList<Invader> invaders;
	/** The list of all the shots that exist in our game */
	private ArrayList<Shot> shots;
	/** The list of invaders that need to be removed from the game */
	private ArrayList<Invader> removeInvader;
	/** The list of shots that need to be removed from the game */	
	private ArrayList<Shot> removeShot;
	/** The ship object representing the player */
	private Ship ship;
	/** The time at which last fired a shot */
	private long lastFire;
	/** The interval between our players shot (ms) */
	private long firingInterval=500;
	/** The number of aliens left on the screen */
	private int nInvaders;
	/**The width of the board*/
	public static int sizeBoardX;
	/**The height of the board*/
	public static int sizeBoardY;
	/**The number of rows of invaders*/
	public static int nRowInvaders;
	/**The number of columns of invaders*/
	public static int nColInvaders;
	///////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Constructor of the game (create and configure the game).
	 */
	public Game(int sizeX, int sizeY, int rowInv, int colInv) {
		/////////////////TO BE FILLED BY STUDENTS/////////////////////////////////////////////	
		/**Assign the value of the width of the board from the correct parameter*/
			sizeBoardX=sizeX;
		/**Assign the value of the height of the board from the correct parameter*/
			sizeBoardY=sizeY;
		/**Assign the value of the number of rows of invaders from the correct parameter*/
			nRowInvaders=rowInv;
		/**Assign the value of the number of columns of invaders from the correct parameter*/
			nColInvaders=colInv;
		///////////////////////////////////////////////////////////////////////////
	
		// create a frame to contain our game
		JFrame container = new JFrame("Space Invaders|Programming 2013/14");
		
		// get hold the content of the frame and set up the resolution of the game

		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(sizeBoardX,sizeBoardY));
		panel.setLayout(null);
		
		// setup our canvas size and put it into the content of the frame

		setBounds(0,0,sizeBoardX,sizeBoardY);
		panel.add(this);
		
		// Tell AWT not to bother repainting our canvas since we're
		// going to do that our self in accelerated mode

		setIgnoreRepaint(true);
		
		// finally make the window visible 

		container.pack();
		container.setResizable(false);
		container.setVisible(true);
		
		// add a listener to respond to the user closing the window. If they
		// do we'd like to exit the game

		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		// add a key input system (defined below) to our canvas
		// so we can respond to key pressed

		addKeyListener(new KeyInputHandler());
		
		// request the focus so key events come to us

		requestFocus();

		// create the buffering strategy which will allow AWT
		// to manage our accelerated graphics

		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
		// initialise the entities in our game so there's something
		// to see at startup

		initEntities();
	}
	
	/**
	 * Start a fresh game, this should clear out any old data and
	 * create a new set.
	 */
	private void startGame(){
			
		/////////////////TO BE FILLED BY STUDENTS/////////////////////////////////////////////	
		// clear out any existing invaders		
		invaders=new ArrayList<Invader> ();
		shots=new ArrayList<Shot> ();
		removeInvader=new ArrayList<Invader> ();
		removeShot=new ArrayList<Shot> ();
		//initialize the game 
		initEntities();
		///////////////////////////////////////////////////////////////////////////
		
		// blank out any keyboard settings we might currently have
		leftPressed = false;
		rightPressed = false;
		firePressed = false;
	}
	
	/////////////////METHODS TO BE FILLED BY STUDENTS/////////////////////////////////////////////	
	/**
	 * Initialise the starting state of the entities (ship and invaders).
	 */
	private void initEntities() {
		

		// create the player ship and place it in the center of the screen
			ship=new Ship(this, "sprites/shipr.gif",sizeBoardX/2,(sizeBoardY*3)/4);
		
		// create a block of invaders with the correct rows and columns. Spaced them evenly)
			nInvaders=nRowInvaders*nColInvaders;
			for(int a=0; a<nRowInvaders;a++){
				for(int b=0; b<nColInvaders;b++){
					Invader invs=new Invader(this, "sprites/invader.gif", b*50+100, a*30+100);
					invaders.add(invs);
				}
			}
	}
	
	/**
	 * Remove an invader from the game. The invader removed will
	 * no longer move or be drawn.
	 * 
	 * @param inv The entity that should be removed
	 */
	public void removeInvader(Invader inv) {
		removeInvader.add(inv);
		
	}
	
	/**
	 * Remove a shot from the game. The shot removed will
	 * no longer move or be drawn.
	 * 
	 * @param s The shot that should be removed
	 */
	public void removeShot(Shot s) {
		removeShot.add(s);
	}	
	
	/**
	 * Notification that an invader has been killed
	 */
	public void notifyInvaderKilled() {
				
		// reduce the invader count, if there are none left, the player has won!
		nInvaders-=1;
		if(nInvaders==0){
			notifyWin();
		}
		else{
			for(int a=0;a<invaders.size();a++){
				Invader c=invaders.get(a);
				c.setSpeedX(c.getSpeedX()*1.02);
			}
		}
		
		// if there are still some invaders left then they all need to get faster, so
		// speed up all the existing invaders, for example a 2%


	}
	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Attempt to fire a shot from the player. Its called "try"
	 * since we must first check that the player can fire at this 
	 * point, i.e. has he/she waited long enough between shots
	 */
	public void tryToFire() {
		// check that we have waiting long enough to fire

		if (System.currentTimeMillis() - lastFire >= firingInterval) {
			
		/////////////////TO BE FILLED BY STUDENTS/////////////////////////////////////////////			
		// create the shot entity just above the ship position
			Shot s=new Shot(this,"sprites/shotr.gif", (int)ship.getX()+10, (int)ship.getY()-30);
		//add it to the list of shots
			shots.add(s);
		//record the time the shot is fired.
			lastFire=System.currentTimeMillis();
		
		//////////////////////////////////////////////////////////////////////////
		}
	}
	
	/**
	 * The main game loop. This loop is running during all game
	 * play as is responsible for the following activities:
	 * 
	 * - Working out the speed of the game loop to update moves
	 * - Moving the game entities
	 * - Drawing the screen contents (entities, text)
	 * - Updating game events
	 * - Checking Input
	 * <p>
	 */
	public void gameLoop() {
		long lastLoopTime = System.currentTimeMillis();
		
		// keep looping round until the game ends

		while (gameRunning) {
			
			// work out how long its been since the last update, this
			// will be used to calculate how far the entities should
			// move this loop

			long delta = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
			
			// Get hold of a graphics context for the accelerated 

			// surface and blank it out

			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0,0,sizeBoardX,sizeBoardY);
			
			// cycle round asking each entity to move itself
			/////////////////TO BE FILLED BY STUDENTS/////////////////////////////////////////////	
			if (!waitingForKeyPress) {
				
				
				//obtain each invader from the list of invaders and move them one at a time
				for(int a=0; a<invaders.size();a++){
					Invader in= invaders.get(a);
					in.move(delta);
				}
				
				//obtain each shot from the list of shots and move them one at a time
				for(int b=0;b<shots.size();b++){
					Shot sh=shots.get(b);
					sh.move(delta);
				}
				
				
				//move the ship
				ship.move(delta);
				

			}
			
		
			//obtain each invader from the list of invaders and draw them one at a time
			for(int a=0; a<invaders.size();a++){
				Invader in= invaders.get(a);
				in.draw(g);
			}
			
			//obtain each shot from the list of shots and draw them one at a time
			for(int b=0;b<shots.size();b++){
				Shot sh=shots.get(b);
				sh.draw(g);
			}
			
			//draw the ship
			ship.draw(g);
	
			
			// brute force collisions, compare every entity against
			// every other entity. If any of them collide notify 
			// both entities that the collision has occured
			
			
			//first check the collisions between an invader and a shot. If a shot collides
			//with an invader they both need to call colidedWith and collidesWith
			for(int a=0; a<shots.size();a++){
				for(int b=0; b<invaders.size();b++){
					Shot s= (Shot) shots.get(a);
					Invader in= (Invader)invaders.get(b);
					if(s.collidesWith(in)){
						s.collidedWith(in);
						in.collidesWith(s);
					}
				}
			}
			
			//check the collisions between an invader and a ship. If an invader collides
			//with the ship it will need to call colidedWith and collidesWith
			for(int c=0; c<invaders.size();c++){
				Invader in=(Invader)invaders.get(c);
				if(ship.collidesWith(in)){
					ship.collidedWith(in);
					in.collidesWith(ship);
				}
			}
		
			// remove any invader that has been marked for clear up
			invaders.removeAll(removeInvader);
			// remove any shot that has been marked for clear up
			
			// clear the list of removed invaders


			
			
			// if a game event has indicated that game logic should
			// be resolved, cycle round every entity requesting that
			// their personal logic should be considered.
			//////////////////////////////////////////////////////////////////////////
			
			//PLEASE DO NOT CHANGE ANYTHING IN HERE
			if (logicRequiredThisLoop) {
				for (int i=0;i<invaders.size();i++) {
					Invader myInv = invaders.get(i);
					myInv.doLogic();
				}
				
				logicRequiredThisLoop = false;
			}
			
			int sizeX = sizeBoardX;
			int sizeY = sizeBoardY;
			
			
			if (waitingForKeyPress) {
				g.setColor(Color.white);
				g.drawString(message,(sizeX-g.getFontMetrics().stringWidth(message))/2,sizeY/2);
				g.drawString("Press any key",(sizeX-g.getFontMetrics().stringWidth("Press any key"))/2,3*sizeY/4);
			}
			
			// finally, we've completed drawing so clear up the graphics

			// and flip the buffer over

			g.dispose();
			strategy.show();
			
			// resolve the movement of the ship. First assume the ship 

			// isn't moving. If either cursor key is pressed then

			// update the movement appropraitely

			ship.setSpeedX(0);
			
			if ((leftPressed) && (!rightPressed)) {
				ship.setSpeedX(-300);
			} else if ((rightPressed) && (!leftPressed)) {
				ship.setSpeedX(300);
			}
			
			// if we're pressing fire, attempt to fire

			if (firePressed) {
				tryToFire();
			}
			
			// finally pause for a bit. Note: this should run us at about

			// 100 fps but on windows this might vary each loop due to

			// a bad implementation of timer

			try { Thread.sleep(10); } catch (Exception e) {}
		}
	}
	
	
	/*
	 ******************************************************************************************
	 * 
	 * PLEASE DO NOT CHANGE ANYTHING IN HERE
	 * 
	 *******************************************************************************************
	 */
	
	/**
	 * Notification from a game entity that the logic of the game
	 * should be run at the next opportunity (normally as a result of some
	 * game event)
	 */
	public void updateLogic() {
		logicRequiredThisLoop = true;
	}
	
	/**
	 * Notification that the player has died. 
	 */
	public void notifyDeath() {
		message = "Oh no! They got you, try again?";
		waitingForKeyPress = true;
	}
	
	/**
	 * Notification that the player has won since all the aliens
	 * are dead.
	 */
	public void notifyWin() {
		message = "Well done! You Win!";
		waitingForKeyPress = true;
	}
	
	 /** 
	 * A class to handle keyboard input from the user. 
	 * 
	 * 
	 */
	private class KeyInputHandler extends KeyAdapter {
		/** The number of key presses we've had while waiting for an "any key" press */
		private int pressCount = 1;
		
		/**
		 * Notification that a key has been pressed. Note that
		 * a key being pressed is equal to being pushed down but *NOT*
		 * released. Thats where keyTyped() comes in.
		 *
		 * @param e The details of the key that was pressed 
		 */
		public void keyPressed(KeyEvent e) {
			// if we're waiting for an "any key" typed then we don't 
			// want to do anything with just a "press"
			if (waitingForKeyPress) {
				return;
			}
			
			
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				firePressed = true;
			}
		} 
		
		/**
		 * Notification from AWT that a key has been released.
		 *
		 * @param e The details of the key that was released 
		 */
		public void keyReleased(KeyEvent e) {
			// if we're waiting for an "any key" typed then we don't 
			// want to do anything with just a "released"
			if (waitingForKeyPress) {
				return;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				firePressed = false;
			}
		}

		/**
		 * Notification that a key has been typed. Note that
		 * typing a key means to both press and then release it.
		 *
		 * @param e The details of the key that was typed. 
		 */
		public void keyTyped(KeyEvent e) {
			// if we're waiting for a "any key" type then
			// check if we've recieved any recently. We may

			// have had a keyType() event from the user releasing

			// the shoot or move keys, hence the use of the "pressCount"
			// counter.

			if (waitingForKeyPress) {
				if (pressCount == 1) {
					// since we've now recieved our key typed

					// event we can mark it as such and start 

					// our new game

					waitingForKeyPress = false;
					startGame();
					pressCount = 0;
				} else {
					pressCount++;
				}
			}
			
			// if we hit escape, then quit the game

			if (e.getKeyChar() == 27) {
				System.exit(0);
			}
		}
	}
	
	/**
	 * 
	 * ******************************************************************/
	
}
