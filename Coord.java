/**
 * This class stores the two coordinates of a point.
 * @author SHUMING CAO A99088201
 * @author YICHENG YIN A99076232
 * @version 10.10
 * @date 2014-03-13
 */

public class Coord {

	private int x;
	private int y;

	/**
	 * Creates a Coord with certain X and Y.
	 * @param X The x coordinate.
	 * @param Y the y coordinate.
	 */
	public Coord(int X, int Y) {
		x = X;
		y = Y;
	}

	/**
	 * Creates a Coord that is the same as another Coord.
	 * @param initial The coord to be copied.
	 */
	public Coord(Coord initial) {
		x = initial.getX();
		y = initial.getY();
	}

	/**
	 * @return The x coordinate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return The y coordinate.
	 */
	public int getY() {
		return y;
	}

	/**
	 * sets the x coordinate.
	 * @param xCoord the new x coordinate.
	 */
	public void xIs(int xCoord) {
		x = xCoord;
	}	

	/**
	 * sets the y coordinate.
	 * @param yCoord the new y coordinate.
	 */
	public void yIs(int yCoord) {
		y = yCoord;
	}

	/**
	 * Compares the Coord with another Coord.
	 * @param point The other Coord to be compared.
	 * @return true if the two Coords have the same values and false otherwise.
	 */
	public boolean equals(Coord point) {
		if (point.getX() == x && point.getY() == y)
			return true;
		else 
			return false;
	}
}