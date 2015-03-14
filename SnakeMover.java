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
    boolean speedup = false;
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

    public void resetDrct(Coord drct) {
	this.drct = drct;
	speedup = false;
	going = true;
	speed = 1;
    }
    
    /**
     * Executes the moving of the snake.
     */
    public void run(){

		while (true) {
			try { TimeUnit.MILLISECONDS.sleep(1000/speed);}
			catch (InterruptedException e){};
			if (!grid.snakeMove(drct))
			    stop();

			// add an obstacle every ten move
			if (grid.tenStep())
				grid.addObstacle();
			if ((grid.getPoints() % 100 == 0)
			    && (speedup) && (speed < 20)) {
				speed += 1;
				grid.sliderSync(speed);
				speedup = false;
			}
		}
    }

    public void speedTo(int newSpeed) {
    	speed = newSpeed;
    }

    public void stop(){
	drct = new Coord(0,0);
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
		drct = nd;
		if (!grid.snakeGrow(drct))
			stop();

		// add 10 points
		grid.addPoints(10);

		speedup = true;	
		if ((grid.getPoints() % 100 == 0)
		    && (speedup) && (speed < 20)) {
			speed += 1;
			grid.sliderSync(speed);
			speedup = false;
		}
		
		if (grid.tenStep())
			grid.addObstacle();
	}
    }
}