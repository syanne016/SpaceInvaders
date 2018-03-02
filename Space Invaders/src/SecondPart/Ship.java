package SecondPart;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Programming 13-14
 * @author Nerea
 */

public class Ship {
	/** The sprite that represents this entity */
	protected Sprite sprite;
	/** The rectangle used for this entity during collisions  resolution */
	private Rectangle me = new Rectangle();
	/** The rectangle used for other entities during collision resolution */
	private Rectangle him = new Rectangle();	
	/////////////////TO BE FILLED BY STUDENTS/////////////////////////////////////////////		
	/** The game in which the ship exists */
	private Game game;
	/** The current x location of this entity */ 
	private double x;
	/** The current y location of this entity */
	private double y;
	/** The current horizontal speed of this entity  (pixels/sec) */
	private double speedX;
	
	///////////////////////////////////////////////////////////////////////////

	
	/**
	 * Create a new entity to represent the players ship
	 *  
	 * @param game The game in which the ship is being created
	 * @param ref The reference to the sprite to show for the ship
	 * @param x The initial x location of the player's ship
	 * @param y The initial y location of the player's ship
	 */
	public Ship(Game game,String ref,int x,int y) {
		this.sprite = SpriteStore.get().getSprite(ref);
		/////////////////TO BE FILLED BY STUDENTS/////////////////////////////////////////////			
		//give values to x,y and game
		this.game=game;
		this.x=x;
		this.y=y;
		speedX=0.0;
		
		///////////////////////////////////////////////////////////////////////////			
	}
	
	/////////////////TO BE FILLED BY STUDENTS/////////////////////////////////////////////		
	
	//methods get and set must be placed in here
	public void setSpeedX(double speed){
		speedX=speed;
	}
	public double getSpeedX(){
		return speedX;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * Request that this entity move itself based on a certain ammount
	 * of time passing.
	 * 
	 * @param delta The ammount of time that has passed in milliseconds
	 */
	public void move(long delta) {
		/////////////////TO BE FILLED BY STUDENTS/////////////////////////////////////////////		
		
		// check if we can move (we didn't reach the limits of the screen)
		if ( !(this.speedX < 0 && this.x <= 10) && 
				!(this.speedX > 0 && this.x >= Game.sizeBoardX - 50)){
			


		//update the position by moving on x 		
			this.x=(this.x+((delta*this.speedX)/1000));
		}
		
		///////////////////////////////////////////////////////////////////////////		
	}
	

	
	
	/*
	 ******************************************************************************************
	 * 
	 * PLEASE DO NOT CHANGE ANYTHING IN HERE
	 * 
	 *******************************************************************************************
	 */
	
	 /**
	 * Draw this entity to the graphics context provided
	 * 
	 * @param g The graphics context on which to draw
	 */
	public void draw(Graphics g) {
		sprite.draw(g,(int) x,(int) y);
	}


	/** Check if this entity collised with another.
	 * 
	 * @param other The other entity to check collision against
	 * @return True if the entities collide with each other
	 */
	public boolean collidesWith(Invader other) {
		me.setBounds((int) x,(int) y,sprite.getWidth(),sprite.getHeight());
		him.setBounds((int) other.getX(),(int) other.getY(),other.sprite.getWidth(),other.sprite.getHeight());

		return me.intersects(him);
	}	

	
	/**
	 * Notification that the player's ship has collided with something
	 * 
	 * @param other The entity with which the ship has collided
	 */
	public void collidedWith(Invader other) {
		// if its an alien, notify the game that the player
		// is dead		
			game.notifyDeath();
		
	}
}