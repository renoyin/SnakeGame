import java.awt.*;
import java.awt.event.*;	

public class SnakeMover implements KeyListener, Runnable {
    int wMax,hMax,speed;
    GameGrid grid;
    boolean going = true;
    Coord drct,cp;
    Snake snake;
    boolean turn = false;
   
    /**
     * Initializes the grid and the current target that is to be moved.
     * @param w The width of the grid
     * @param h The height of the grid
     * @param p The size of a cellar
     * @param cp
     * @param drct
     * @param grid The reference of the current grid
     */
    public SnakeMover(Coord cp,Coord drct,GameGrid grid, Snake snake) {
		this.grid = grid;
		this.cp = cp;
		this.drct = drct;
		this.snake = snake;
    }
    
    /**
     * Executes the moving of the snake.
     */
    public void run(){
	    // Delay an amount of time
		try { TimeUnit.MILLISECONDS.sleep(1000/speed);}
		catch (InterruptedException e){};
		if ((wMax == 0) || (hMax == 0)) going = false;
		while (going) {
		    // determine snake's direction
			if (turn == true) {
				grid.snakeGrow(drct);
				// add an obstacle.
				// add 10 points
			}
			else {
				grid.snakeMove(drct);
			}
		    // Remove the cell (which has index 4)
		    grid.clearCell(4);
		    cx += drct;
		    // Draw the new one
		    grid.fillCell(cx,cy);

		}
    }

    public void stop(){
		going = false;
    }

    // Implement the KeyListener Interface
	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
		Coord nd;
		char key = e.getKeyChar();
		if (!going) return; // don't move

		if (key == 'l' || key == 'j')  
		{
			int change = 1;
			if (key == 'l') change = -1;
			if (drct.getX() == 0)
				nd= new Coord(change*direction.getY(), 0);
			else
				nd = new Coord(0,-change*direction.getX());
			drct = nd;
			turn = true;
		}
	}
}