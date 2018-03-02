package FirstPart;

public class Invader {
	public int id;
	public boolean isAlive;
	public int position;
	public boolean canShoot;
	public int score;
	public Invader(int i, int p){
		isAlive=true;
		canShoot=false;
		score=20;
		id=i;
		position=p;
	}
}
