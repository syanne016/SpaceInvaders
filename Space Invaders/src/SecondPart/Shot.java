package SecondPart;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Programming 13-14
 * @author Nerea
 */

public class Shot {
	/** The sprite that represents this entity */
	protected Sprite sprite;
	/** The rectangle used for this entity during collisions  resolution */
	private Rectangle me = new Rectangle();
	/** The rectangle used for other entities during collision resolution */
	private Rectangle him = new Rectangle();
	
	
	
	
	
	
	
	/////////////////TO BE FILLED BY STUDENTS/////////////////////////////////////////////	
	/** The vertical speed at which the players shot moves */
	private double moveSpeed=-300;
	/** The game in which this entity exists */
	private Game game;
	/** True if this shot has been "used", i.e. its hit something */
	private boolean used=false;
	/** The current x location of this entity */ 
	private double x;
	/** The current y location of this entity */
	private double y;
	/** The current speed of this entity vertically (pixels/sec) */
	private double speedY;
	///////////////////////////////////////////////////////////////////////////

	
	/**
	 * Create a new shot from the player
	 * 
	 * @param game The game in which the shot has been created
	 * @param sprite The sprite representing this shot
	 * @param x The initial x location of the shot
	 * @param y The initial y location of the shot
	 */
	public Shot(Game game,String ref,int x,int y) {
		this.sprite = SpriteStore.get().getSprite(ref);
		/////////////////TO BE FILLED BY STUDENTS/////////////////////////////////////////////		
		//give values to x,y,game and speedX
		this.x=x;
		this.y=y;
		this.game=game;
		speedY=moveSpeed;
		
		///////////////////////////////////////////////////////////////////////////		
	}
	
	/////////////////TO BE FILLED BY STUDENTS/////////////////////////////////////////////		
	
	//methods get and set must be placed in here
	public void setSpeedY(double speed){
		speedY=speed;
	}
	public double getSpeedY(){
		return speedY;
	}
	public double getY(){
		return y;
	}
	public double getX(){
		return x;
	}	
	///////////////////////////////////////////////////////////////////////////

	/**
	 * Request that this shot moved based on time elapsed
	 * 
	 * @param delta The time that has elapsed since last move
	 */
	public void move(long delta) {
		
		/////////////////TO BE FILLED BY STUDENTS/////////////////////////////////////////////
		//update the position by moving on x and y
		this.y=(this.y+((delta*this.speedY)/1000));		
		///////////////////////////////////////////////////////////////////////////
		
		// if we shot off the screen, remove ourselfs
		if (y < -100) {
			game.removeShot(this);
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
	 *
	 * Draw this entity to the graphics context provided
	 * 
	 * @param g The graphics context on which to draw
	 */
	public void draw(Graphics g) {
		sprite.draw(g,(int) x,(int) y);
	}
	
	
	/**
	 * Check if this entity collised with another.
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
	 * Notification that this shot has collided with another
	 * entity
	 * 
	 * @parma other The other entity with which we've collided
	 */
	public void collidedWith(Invader other) {
		// prevents double kills, if we've already hit something,

		// don't collide

		if (!used){ 
		
		// if we've hit an alien, kill it!		
			// remove the affected entities

			game.removeShot(this);
			game.removeInvader(other);
			
			// notify the game that the alien has been killed

			game.notifyInvaderKilled();
			used = true;
		}
	}
	/**
	 * 
	 * ******************************************************************/
}