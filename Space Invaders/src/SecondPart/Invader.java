package SecondPart;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Programming 13-14
 * @author Nerea
 */

public class Invader{
	/** The sprite that represents this entity */
	protected Sprite sprite;
	/** The rectangle used for this entity during collisions  resolution */
	private Rectangle me = new Rectangle();
	/** The rectangle used for other entities during collision resolution */
	private Rectangle him = new Rectangle();
	/////////////////TO BE FILLED BY STUDENTS/////////////////////////////////////////////	
	/** The speed at which the invader moves horizontally */
	private double moveSpeed=75;
	/** The game in which the entity exists */
	private Game game;
	/** The current x location of this entity */ 
	private double x;
	/** The current y location of this entity */
	private double y;
	/** The current speed of this entity horizontally (pixels/sec) */
	private double speedX;
	/** The current speed of this entity vertically (pixels/sec) */
	private double speedY;
	///////////////////////////////////////////////////////////////////////////

	
	/**
	 * Create a new invader entity
	 * 
	 * @param game The game in which this entity is being created
	 * @param ref The sprite which should be displayed for this alien
	 * @param x The initial x location of this invader
	 * @param y The initial y location of this invader
	 */
	public Invader(Game game,String ref,int x,int y) {		
		this.sprite = SpriteStore.get().getSprite(ref);
		/////////////////TO BE FILLED BY STUDENTS/////////////////////////////////////////////				
		//give values to x,y,game and speedX
		this.x=x;
		this.y=y;
		this.game=game;
		speedX=moveSpeed;
	
		
		///////////////////////////////////////////////////////////////////////////	
	}


	
	/////////////////TO BE FILLED BY STUDENTS/////////////////////////////////////////////		
	
	//methods get and set must be placed in here
	public double getSpeedX() {
		return speedX;
	}
	public void setSpeedX(double speed) {
		speedX = speed;
	}
	public double getSpeedY() {
		return speedY;
	}
	public void setSpeedY(double speed) {
		speedY = speed;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * Request that this alien moved based on time elapsed
	 * 
	 * @param delta The time that has elapsed since last move
	 */
	public void move(long delta) {
		/////////////////TO BE FILLED BY STUDENTS/////////////////////////////////////////////		
		// if we have reached the left hand side of the screen and
		// are moving left then request a logic update 
		if(this.speedX<0 && this.x>=10){
			game.updateLogic();
		}		
		// and vice versa, if we have reached the right hand side of
		// the screen and are moving right, request a logic update
		if(this.speedX>0 && this.x<=Game.sizeBoardX-50){
			game.updateLogic();
		}
		//update the position by moving on x and y
		this.x=(this.x+((speedX*delta)/1000));
		this.y=(this.y+((speedY*delta)/1000));
	
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
	 * 
	 * Draw this entity to the graphics context provided
	 * 
	 * @param g The graphics context on which to draw
	 */
	public void draw(Graphics g) {
		sprite.draw(g,(int) x,(int) y);
		
	}	
	
	/**
	 * Update the game logic related to aliens
	 */
	public void doLogic() {
		// swap over horizontal movement and move down the
		// screen a bit

		speedX = -speedX;
		y += 10;
		
		// if we've reached the bottom of the screen then the player
		// dies
		
		int maxSpeedX = Game.sizeBoardX;
		
		if (y > maxSpeedX-50) {
			game.notifyDeath();
		}
	}
	
	/**
	 * Check if this entity collised with another.
	 * 
	 * @param other The other entity to check collision against
	 * @return True if the entities collide with each other
	 */
	public boolean collidesWith(Shot other) {
		me.setBounds((int) x,(int) y,sprite.getWidth(),sprite.getHeight());
		him.setBounds((int) other.getX(),(int) other.getY(),other.sprite.getWidth(),other.sprite.getHeight());

		return me.intersects(him);
	}
	
	public boolean collidesWith(Ship other) {
		me.setBounds((int) x,(int) y,sprite.getWidth(),sprite.getHeight());
		him.setBounds((int) other.getX(),(int) other.getY(),other.sprite.getWidth(),other.sprite.getHeight());

		return me.intersects(him);
	}
	
	/**
	 * 
	 * ******************************************************************/
	
}


	