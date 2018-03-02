package FirstPart;

public class Ship {
    public int id;
    public int lives;
    public int position;
    public int maxPosition;
    public int minPosition;
    public int score;
    public Ship(int i, int v, int p, int maxP, int minP){
    	score=0;
    	id=i;
    	lives=v;
    	maxPosition=maxP;
    	minPosition=minP;
    	position=p;
    }
    
}

