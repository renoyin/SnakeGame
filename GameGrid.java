/**
 * This class creates a 2D array to store the type of 
 * each cells in the grid. Everytime the method in this
 * class called to move snake, repaint() will be invoked 
 * to refresh the grid in GUI.
 * @author SHUMING CAO A99088201
 * @author YICHENG YIN A99076232
 * @version 10.10
 * @date 2014-03-13
 */

import java.util.Random;
import java.awt.*;

public class GameGrid {
	private static final Color EMPTY = Color.WHITE;
	private static final Color BODY = Color.GREEN;
	private static final Color HEAD = Color.RED;
	private static final Color OBSTACLE = Color.BLACK;
	private Snake snake;
	private Coord snakeHead;
	private int xMax, yMax;
	private Random rndm;
	private Color[][] grid;
	private int step = 0;
	private int points = 0;
	private GraphicsGrid graphicsGrid;
	private SnakeGame game;

	/**
	 * Constructor
	 * @param xSegMax number of segments in x direction
	 * @param ySegMax number of segments in y direction
	 * @param s the reference of a Snake instance
	 */
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

	/**
	 * Gets the reference of SnakeGame instance.
	 * @param g reference of SnakeGame instance
	 */
	public void passGame(SnakeGame g) {
		game = g;
	}

	/**
	 * Reset the fields in GameGrid to start a new game.
	 * @param xSegMax number of segments in x direction
	 * @param ySegMax number of segments in y direction
	 */
    public void reset(int xSegMax, int ySegMax) {
	xMax = xSegMax;
	yMax = ySegMax;
	snakeHead = snake.getSnake().get(0);
	grid = new Color[xMax][yMax];
	for (int i = 0; i < xMax; i++)
	    for (int j = 0; j < yMax; j++)
		grid[i][j] = EMPTY;
	grid[snakeHead.getX()][snakeHead.getY()] = HEAD;
	rndm = new Random();
	points = 0;
	step = 0;
    }

    /**
     * Creates a new GraphicsGrid instance and return 
     * the reference.
     * @param w width of grid
     * @param h height of grid
     * @param p size of each segment
     * @param grid reference to GameGrid instance
     * @return reference to a new GraphicsGrid instance
     */
    public GraphicsGrid setGraphics(int w,int h,int p,
    								GameGrid grid) {
    	graphicsGrid = new GraphicsGrid(w,h,p,grid);
    	return graphicsGrid;
    }

    /**
     * Gets the color of a cell given its coordinate.
     * @param x x coordinate of the cell
     * @param y y coordinate of the cell
     * @return reference to a Color instance
     */
	public Color getColor(int x, int y) {
		return grid[x][y];
	}

	/**
	 * Moves snake given a direction.
	 * Then repaint the grid.
	 * @param direction the direction of moving
	 * @return true if the movement is valid,
	 * false if the movement is invalid
	 */
	public boolean snakeMove(Coord direction) {
		snake.move(direction);

		if ((direction.getX() == 0) && (direction.getY() == 0))
		    return true;
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

	/**
	 * Turns snake given a direction and grows a cell
	 * to the head of snake. Then repaint the grid.
	 * @param direction the direction of the turning
	 * @return true if the movement is valid,
	 * false if the movement is invalid
	 */
	public boolean snakeGrow(Coord direction) {
		snake.grow(direction);
		
		if ((direction.getX() == 0) && (direction.getY() == 0))
		    return true;
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

	/**
	 * Checks if the position of snake is valid 
	 * and if snake intersects with itself.
	 * @return true if the position is valid and snake 
	 * does not intersect with itself, false if anything
	 * breaks the rule
	 */
	public boolean valid() {
		snakeHead = snake.getSnake().get(0);

		if (snakeHead.getX() < 0 || snakeHead.getX() >= xMax) {
			game.gameOver();
			return false;
		}

		if (snakeHead.getY() < 0 || snakeHead.getY() >= yMax) {
			game.gameOver();
			return false;
		}

		if (grid[snake.getSnake().get(0).getX()]
				[snake.getSnake().get(0).getY()]==OBSTACLE) {
			game.gameOver();
			return false;
		}
		
		if (snake.intersect()) {
			game.gameOver();
			return false;
		}
		step++;
		
		return true;
	}

	/**
	 * Randomly adds an obstacle on the grid.
	 */
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

	/**
	 * Counts the numbers of consecutive movements.
	 * Resets step to zero if snake moves ten steps.
	 * @return true if snake moves ten times, false if
	 * less than ten steps
	 */
    public boolean tenStep() {
	if (step == 10) {
	    step = 0;
	    return true;
		}
	return false;
    }
    
    /**
     * Adds points and display on the GUI interface.
     * @param p points need to be added
     */
    public void addPoints(int p) {
	points += p;
	game.setScore();
	game.setHighScore(points);
    }
    
    /**
     * Gets current number of points.
     * @return number of current points
     */
    public int getPoints() {
	return points;
    }
    
    /**
     * SnakeMover can call this method to update
     * the value of slider on the GUI interface.
     * @param speed value of current speed of snake
     */
    public void sliderSync(int speed) {
	game.setSlider(speed);
    }
}