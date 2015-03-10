import java.util.ArrayList;
import java.awt.*;

public class Snake {
	private ArrayList<Point> snake;

	public Snake(int initX, int initY) {
		snake = new ArrayList<Point>();
		snake.add(new Point(initX, initY));
	}
/*
	public void move(int x, int y) {
		for (int i = 1; i < snake.length; i++)
			snake.set(i, snake.get(i - 1)); 
		snake.set(0, Point(x, y));
	}
*/
	public boolean grow(int x, int y) {
		for (int i = 0; i < snake.length; i++) 
			if (snake.get(i).getX == x && snake.get(i).getY == y)
				return false;
		snake.add(0, new Point(x, y));
		return true;
	}

	public Point getHead() {
		return snake.get(0);
	}
}