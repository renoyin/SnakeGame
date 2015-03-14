/**
 * This class creates a instance of snake which 
 * stores an arraylist of the snakes' coordinates.  
 */
import java.util.ArrayList;

public class Snake {
	private ArrayList<Coord> coords;

	/**
	 * Constructor
	 * @param initX initial x coordinate of snake' head
	 * @param initY initial y coordinate of snake' head
	 */
	public Snake(int initX, int initY) {
		coords = new ArrayList<Coord>();
		coords.add(new Coord(initX, initY));
	}
    
    /**
     * Resets the snake without changing its reference.
     * @param initX initial x coordinate of snake' head
     * @param initY initial x coordinate of snake' head
     */
    public void setSnake(int initX, int initY) {
	    coords = new ArrayList<Coord>();
	    coords.add(new Coord(initX,initY));
	}

	/**
	 * Moves coordinates of cells in snake 
	 * when given a direction.
	 * @param direction moving direction of snake
	 */
	public void move(Coord direction) {
		int x = direction.getX();
		int y = direction.getY();
		int xHead = coords.get(0).getX();
		int yHead = coords.get(0).getY();
		for (int i = coords.size()-1; i > 0; i--) {
			coords.get(i).xIs(coords.get(i-1).getX());
			coords.get(i).yIs(coords.get(i-1).getY());
		}
		coords.get(0).xIs(xHead + x);
		coords.get(0).yIs(yHead + y);
	}

	/**
	 * Turns snake according to the given direction
	 * and grows one cell to the head of snake.
	 * @param direction the direction of turning
	 */
	public void grow(Coord direction) {
		int x = direction.getX();
		int y = direction.getY();
		coords.add(0, new Coord(coords.get(0).getX() + x,
								coords.get(0).getY() + y));
	}

	/**
	 *	Gets the Coord reference of snake's head.
	 */
	public Coord getHead() {
		return coords.get(0);
	}

	/**
	 * Gets the arraylist of snake's Coord references.
	 */
	public ArrayList<Coord> getSnake() {
		return coords;
	}

	/**
	 * Checks if snake intersects itself 
	 * and return a boolean parameter.
	 * @return true if snake does not intersect with itself,
	 * false if it intersects
	 */
	public boolean intersect() {
		for (int i = 1; i < coords.size(); i++)
			if (coords.get(0).equals(coords.get(i)))
				return true;
		return false;
	}
}