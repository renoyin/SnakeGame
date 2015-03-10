import java.util.ArrayList;
import java.awt.*;

public class Snake {
	private ArrayList<Coord> snake;

	public Snake(int initX, int initY) {
		snake = new ArrayList<Coord>();
		snake.add(new Coord(initX, initY));
	}

	public boolean grow(int x, int y) {
		for (int i = 0; i < snake.size(); i++) 
			if (snake.get(i).getX == x && snake.get(i).getY == y)
				return false;
		snake.add(0, new Coord(x, y));
		return true;
	}

	public Coord getHead() {
		return snake.get(0);
	}

	public ArrayList<Coord> getSnake() {
		return snake;
	}

	public boolean intersect() {
		for (int i = 1; i < snake.size(); i++)
			if (snake.get(0).equals(snake.get(i)))
				return true;
		return false;
	}
}