import java.awt.*;
import java.awt.event.*;	
import java.util.concurrent.TimeUnit;

public class SnakeMover implements KeyListener, Runnable {
    int wMax,hMax,speed=1;
    GameGrid grid;
    boolean going = true;
    Coord drct,cp;
    Snake snake;
    boolean turn = false;
    GraphicsGrid graphicsGrid;
   
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
    
    public void setGraphics(int w,int h,int p,GameGrid grid) {
    	graphicsGrid = new GraphicsGrid(w,h,p,grid);
    	graphicsGrid.fillCell();
    	return;
    }
    /**
     * Executes the moving of the snake.
     */
    public void run(){
		
		while (going) {

			try { TimeUnit.MILLISECONDS.sleep(1000/speed);}
			catch (InterruptedException e){};

		    // determine snake's direction
			if (turn == true) {
				grid.snakeGrow(drct);
				// add an obstacle
				// add 10 points
			}
			else {
				grid.snakeMove(drct);
			}
			turn = false;
		    // Draw the new one
		    graphicsGrid.fillCell();
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
				nd= new Coord(change*drct.getY(), 0);
			else
				nd = new Coord(0,-change*drct.getX());
			drct = nd;
			turn = true;
		}
	}
}