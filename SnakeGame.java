import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.io.IOException;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.TimeUnit;
import java.awt.event.*;

public class SnakeGame extends JFrame implements ChangeListener{
	private static int w = 400, h = 400, p = 10;
    private static GameGrid grid;
   	private static SnakeMover move;
   	private static Snake snake;
   	private static GraphicsGrid graph;
   	private static JLabel text1, text2, score, highScore;
   	private static JLabel gameOver;
   	private static JPanel topPanel;
   	private static JPanel bottomPanel;
   	private Container contentPane = getContentPane();
   	private static JSlider slider;
   	private static int sliderValue = 1;
   	private static int speedMin = 0, speedMax = 20, speedInit = 1;
   	private static JButton newGame, reset;

   	public SnakeGame(int width, int height, int pixel) {
		super();
		w = width;
		h = height;
		p = pixel;

		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2, 3));
		text1 = new JLabel("Score: ");
		text2 = new JLabel("High Score: ");
		score = new JLabel("0");
		highScore = new JLabel("0");
		gameOver = new JLabel("");
		
		topPanel.add(text1);
		topPanel.add(score);
		topPanel.add(gameOver);
		topPanel.add(text2);
		topPanel.add(highScore);

		bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		newGame = new JButton("NEW GAME");
		reset = new JButton("Reset!");
		slider = new JSlider();

		bottomPanel.add(newGame);
		bottomPanel.add(reset);
		bottomPanel.add(slider);
		
		contentPane.add(topPanel, BorderLayout.NORTH);
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		contentPane.validate();

		setSize(w + 10, h + 95);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(graph);
		setVisible(true);
		graph.repaint();

		
		slider = new JSlider(speedMin, speedMax, speedInit);
		slider.addChangeListener(this);
		
		/*
		newGame.addActionListener(this);
		reset.addActionListener(this);
		*/

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
		grid.passGame(game);
		
		Thread t = new Thread(move);
		
		try {
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
    
    public void setScore() {
    	score.setText(String.valueOf(grid.getPoints()));
    }

    
    @Override 
    public void stateChanged(ChangeEvent e) {
    	int speedValue = slider.getValue();
    	move.speedTo(speedValue);
    }
    /*
    @Override 
    public void actionPerformed(ActionEvent e) {
    	JButton sourceEvent = (JButton) event.getSource();

    	if (sourceEvent == newGame) {
    		//new game
    	}
    	else if (sourceEvent == reset) {
    		//reset
    	}
    }
    */

    public void gameOver() {
    	gameOver.setText("GAME OVER!");
    	move.stop();
    }
}