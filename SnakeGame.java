import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.io.IOException;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.TimeUnit;
import java.awt.event.*;

public class SnakeGame extends JFrame {
	private static int w = 400, h = 400, p = 10;
    private static GameGrid grid;
   	private static SnakeMover move;
   	private static Snake snake;
   	private static GraphicsGrid graph;

   	public SnakeGame(int width, int height, int pixel) {
		super();
		w = width;
		h = height;
		p = pixel;
		//initialize graph
		setSize(w + 10, h + 35);
		snake = new Snake(w / (p * 2), h / (p * 2));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(graph);
		setVisible(true);
		graph.repaint();
	}

	public static void main(String[] a) {

		// Detect the arguments and avoid errors.
		if (a.length == 3) {
			try {
			    w = Integer.parseInt(a[0]);
			    h = Integer.parseInt(a[1]);
			    p = Integer.parseInt(a[2]);
			    if ((w <= 0) || (h <= 0) || (p <= 0)
				|| (w < p) || (h < p))
				throw(new Exception());
			}
			catch (Exception excpt) {
			    printHelp();
			    return;
			}
	    }
	    else {
			printHelp();
			return;
	    }
		
		// Create the window.
		SnakeGame game = new SnakeGame(w, h, p);
		snake = new Snake(w/p/2,0);
		grid = new GameGrid(w/p,h/p,snake);
		graph = new GraphicsGrid(w,h,p,grid);
		move = new SnakeMover(new Coord(w/p/2,0),new Coord(0,-1),grid,snake);
		Thread t = new Thread(move);
		
		try 
		    {
			System.out.format("Hit Return to exit program");
			t.start();
			System.in.read();
		    }
		catch (Exception excpt) {
		    move.stop();
		}
		game.dispatchEvent(new WindowEvent(game, 
						     WindowEvent.WINDOW_CLOSING));
		game.dispose();	
	}

	public static void printHelp() {
		System.out.println("     width – Integer width of the playing grid in pixels\n"+
			"     height – Integer height of the playing grid in pixels\n"+
   			"     segmentsize – Integer size of each snake segement in pixels\n\n"+
			"     defaults: width = 400, height = 400, segmentsize = 10");
	}
    
}