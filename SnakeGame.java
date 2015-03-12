import java.awt.*;
import javax.swing.*;

public class SnakeGame extends JFrame {
	private static int w = 400, h = 400, p = 10;
    private GameGrid grid;
   	private SnakeMover move;
   	private Snake snake;
   	private GraphicGrid graph;
   	public SnakeGame(int width, int height, int pixel) {
		super();
		w = width;
		h = height;
		p = pixel;
		//initialize graph
		graph.setSize(w + 10, h + 35);
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
		graph = new GraphicGrid(w,h,p,grid);
		move = new SnakeMover(new Coord(w/p/2,0),new Coord(0,-1),grid,graph);
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
		WindowEvent.dispatchEvent(new WindowEvent(window, 
						     WindowEvent.WINDOW_CLOSING));
		window.dispose();	
	}

	public void printHelp() {
		System.out.println("     width – Integer width of the playing grid in pixels\n"+
			"     height – Integer height of the playing grid in pixels\n"+
   			"     segmentsize – Integer size of each snake segement in pixels\n\n"+
			"     defaults: width = 400, height = 400, segmentsize = 10");
	}
    
}