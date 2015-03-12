import java.util.ArrayList;

public class Snake {
	private ArrayList<Coord> coords;

	public Snake(int initX, int initY) {
		coords = new ArrayList<Coord>();
		coords.add(new Coord(initX, initY));
	}

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

	public void grow(Coord direction) {
		int x = direction.getX();
		int y = direction.getY();
		coords.add(0, new Coord(coords.get(0).getX() + x,
								coords.get(0).getY() + y));
	}

	public Coord getHead() {
		return coords.get(0);
	}

	public ArrayList<Coord> getSnake() {
		return coords;
	}

	public boolean intersect() {
		for (int i = 1; i < coords.size(); i++)
			if (coords.get(0).equals(coords.get(i)))
				return true;
		return false;
	}
}