public class Coord {

	private int x;
	private int y;

	public Coord(int X, int Y) {
		x = X;
		y = Y;
	}

	public Coord(Coord initial) {
		x = initial.getX();
		y = initial.getY();
	}

	public int getX() {
		return x;
	}

	public void xIs(int xCoord) {
		x = xCoord;
	}	

	public void yIs(int yCoord) {
		y = yCoord;
	}

	public int getY() {
		return y;
	}

	public boolean equals(Coord point) {
		if (point.getX() == x && point.getY() == y)
			return true;
		else 
			return false;
	}
}