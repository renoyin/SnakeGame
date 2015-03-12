import java.util.Random;
import java.awt.*;

public class GameGrid {
	private static final Color EMPTY = Color.WHITE;
	private static final Color BODY = Color.GREEN;
	private static final Color HEAD = Color.RED;
	private static final Color OBSTACLE = Color.BLACK;
	private Snake snake;
	private Coord snakeHead;
	private int xMax;
	private int yMax;
	private Random rndm;
	private Color[][] grid;

	// xSegMax and ySegMax are number of segments in x and y
	public GameGrid(int xSegMax, int ySegMax, Snake s) {
		xMax = xSegMax;
		yMax = ySegMax;
		grid = new Color[xMax][yMax];
		snake = s;
		for (int i = 0; i < xMax; i++)
			for (int j = 0; j < yMax; j++)
				grid[i][j] = EMPTY;
	}

	public Color getColor(int x, int y) {
		return grid[x][y];
	}

	public boolean snakeMove(Coord direction) {
		snake.move(direction);

		if (!valid())
			return false;

		for (int i = 0; i < xMax; i++)
			for (int j = 0; j < yMax; j++) 
				if (grid[i][j] != OBSTACLE)
					grid[i][j] = EMPTY;

		grid[snake.getSnake().get(0).getX()]
			[snake.getSnake().get(0).getY()] = HEAD;
		for (int i = 1; i < snake.getSnake().size(); i++) 
			grid[snake.getSnake().get(i).getX()]
				[snake.getSnake().get(i).getY()] = BODY; 

		return true;
	}

	public boolean snakeGrow(Coord direction) {
		snake.grow(direction);

		if (!valid())
			return false;

		for (int i = 0; i < xMax; i++)
			for (int j = 0; j < yMax; j++) 
				if (grid[i][j] != OBSTACLE)
					grid[i][j] = EMPTY;

		grid[snake.getSnake().get(0).getX()]
		    [snake.getSnake().get(0).getY()] = HEAD;

		for (int i = 1; i < snake.getSnake().size(); i++) 
			grid[snake.getSnake().get(i).getX()]
				[snake.getSnake().get(i).getY()] = BODY; 

		return true;
	}

	public boolean valid() {
		snakeHead = snake.getSnake().get(0);

		if (snakeHead.getX() < 0 || snakeHead.getX() > xMax)
			return false;

		if (snakeHead.getY() < 0 || snakeHead.getY() > yMax)
			return false;

		if (grid[snake.getSnake().get(0).getX()]
				[snake.getSnake().get(0).getY()]==OBSTACLE)
			return false;

		if (snake.intersect())
			return false;

		return true;
	}
}