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
	private int step = 0;
	private int points = 0;
	private GraphicsGrid graphicsGrid;
	private SnakeGame game;


	// xSegMax and ySegMax are number of segments in x and y
	public GameGrid(int xSegMax, int ySegMax, Snake s) {
		xMax = xSegMax;
		yMax = ySegMax;
		snakeHead = s.getSnake().get(0);
		grid = new Color[xMax][yMax];
		snake = s;
		for (int i = 0; i < xMax; i++)
			for (int j = 0; j < yMax; j++)
				grid[i][j] = EMPTY;
		grid[snakeHead.getX()][snakeHead.getY()] = HEAD;
		rndm = new Random();
	}

	public void passGame(SnakeGame g) {
		game = g;
	}

    public GraphicsGrid setGraphics(int w,int h,int p,GameGrid grid) {
    	graphicsGrid = new GraphicsGrid(w,h,p,grid);
    	return graphicsGrid;
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
		graphicsGrid.fillCell();
		
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
		graphicsGrid.fillCell();	
				
		return true;
	}

	public boolean valid() {
		snakeHead = snake.getSnake().get(0);
		step++;

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

	public void addObstacle() {
		boolean valid = true;

		while (valid) {
			int i = rndm.nextInt(xMax);
			int j = rndm.nextInt(yMax);
			if (grid[i][j] == EMPTY) {
				grid[i][j] = OBSTACLE;
				valid = false;
			}
		}
	}

	public boolean tenStep() {
		if (step == 10) {
			step = 0;
			return true;
		}
		return false;
	}

	public void addPoints(int p) {
		points += p;
		game.setScore();
	}

	public int getPoints() {
		return points;
	}
}