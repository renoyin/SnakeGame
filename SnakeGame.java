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
   	private static JLabel score, highScore;
   	private static JPanel topPanel;
   	private Container contentPane = getContentPane();

   	public SnakeGame(int width, int height, int pixel) {
		super();
		w = width;
		h = height;
		p = pixel;

		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2, 3));
		score = new JLabel("Score: ");
		highScore = new JLabel("High Score: ");
		topPanel.add(score);
		topPanel.add(highScore);
		contentPane.add(topPanel, BorderLayout.NORTH);
		contentPane.validate();		

		setSize(w + 10, h + 100);
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
	    else if (a.length != 0) {
			printHelp();
			return;
	    }
		
		// Create the window.
		snake = new Snake(w/p/2,0);
		grid = new GameGrid(w/p,h/p,snake);
		move = new SnakeMover(new Coord(w/p/2,0),
							  new Coord(0,1),grid,snake);
		graph = grid.setGraphics(w,h,p,grid);
		graph.addKeyListener(move);
		SnakeGame game = new SnakeGame(w, h, p);
		Thread t = new Thread(move);
		
		try 
		    {
				t.start();
		    }
		catch (Exception excpt) {
		    move.stop();
		}
		/*try {
			while (true) {
				try { TimeUnit.MILLISECONDS.sleep(50);}
				catch (InterruptedException e){};
				graph.fillCell();
			}
		}*/
		catch (Exception e) {};
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