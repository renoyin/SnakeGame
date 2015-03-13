import java.awt.*;
import java.awt.event.*;	
import java.util.concurrent.TimeUnit;
import java.lang.Thread;
import javax.swing.*;

public class SnakeMover implements KeyListener, Runnable {
    int wMax,hMax,speed=1;
    GameGrid grid;
    boolean going = true;
    Coord drct,cp,nd;
    Snake snake;
    boolean turn = false,speedup = false;
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
    

    /**
     * Executes the moving of the snake.
     */
    public void run(){

		while (going) {

			try { TimeUnit.MILLISECONDS.sleep(1000/speed);}
			catch (InterruptedException e){};

		    // whether the snake changes direction
			if (turn == true) {
				// modify System.exit(-1)
				drct = nd;
				if (!grid.snakeGrow(drct))
					System.exit(-1);

				// add 10 points
				grid.addPoints(10);

				// add an obstacle
				grid.addObstacle();

				speedup = true;
			}
			else {
				// modify System.exit(-1)
				if (!grid.snakeMove(drct))
					System.exit(-1);
			}

			// add an obstacle every ten move
			if (grid.tenStep())
				grid.addObstacle();
			turn = false;

			if ((grid.getPoints() % 100 == 0)
				&& (speedup)) {
				speed += 1;
			}
		    

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
		char key = e.getKeyChar();

		if (!going) return; // don't move

		if (key == 'l' || key == 'j') {
			int change = 1;
			if (key == 'l') change = -1;
			if (drct.getX() == 0)
				nd= new Coord(change*drct.getY(), 0);
			else
				nd = new Coord(0,-change*drct.getX());
			turn = true;
		}
	}
}