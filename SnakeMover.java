/**
 * A class that controls the snake to move forward.
 * Also detects user keyboard event that can turn the snake
 * left or right.
 * @author SHUMING CAO A99088201
 * @author YICHENG YIN A99076232
 * @version 10.10
 * @date 2014-03-13
 */

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
    // speedup is used to tell if speed should be increased.
    boolean speedup = false;
    GraphicsGrid graphicsGrid;

    /**
     * Initializes the snake's starting point, the starting direction,
     * the grid that the snake is on, and read in the snake reference.
     * @param cp The starting position of the snake head.
     * @param drct The starting direction of the snake's movement.
     * @param grid The reference of the GameGrid.
     * @param snake the reference of the snake.
     */
    public SnakeMover(Coord cp,Coord drct,GameGrid grid, Snake snake) {
		this.grid = grid;
		this.cp = cp;
		this.drct = drct;
		this.snake = snake;
    }

    /**
     * Resets the snake's moving direction.
     * @param drct The new direction of the snake's movement.
     */
    public void resetDrct(Coord drct) {
	this.drct = drct;
	speedup = false;
	going = true;
	speed = 1;
    }
    
    /**
     * Executes the moving of the snake forwards only.
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

    /**
     * Sets the speed of the snake to a number.
     * @param newSpeed The new speed of the snake.
     */
    public void speedTo(int newSpeed) {
    	speed = newSpeed;
    }

    /** 
     * Sets the speed of the snake to 0.
     */
    public void stop(){
	drct = new Coord(0,0);
	going = false;
    }

    /**
     * Implement the KeyListener Interface
     */
 	public void keyPressed(KeyEvent e) {
    }

    /**
     * Implement the KeyListener Interface
     */
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Implement the KeyListener Interface
     * Turns the snake if the user hits 'l' or 'j' key.
     */
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

		grid.addPoints(10);

		// Deals if speed should be increased
		speedup = true;	
		if ((grid.getPoints() % 100 == 0)
		    && (speedup) && (speed < 20)) {
			speed += 1;
			grid.sliderSync(speed);
			speedup = false;
		}
		
		// Add an obstacle after 10 steps of movements
		if (grid.tenStep())
			grid.addObstacle();
	}
    }
}