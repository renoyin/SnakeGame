import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.io.IOException;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.TimeUnit;
import java.awt.event.*;
import javax.swing.event.*;

public class SnakeGame extends JFrame implements ChangeListener, ActionListener {
    private static int w = 400, h = 400, p = 10;
    private static int wOffset, hOffset;
    private static GameGrid grid;
    private static SnakeMover move;
    private static Snake snake;
    private static GraphicsGrid graph;
    private static SnakeGame game;
    private static JLabel text1, text2, score, highScore;
    private static JLabel gameOver;
    private static JPanel topPanel;
    private static JPanel bottomPanel;
    private Container contentPane = getContentPane();
    private static JSlider slider;
    private static int sliderValue = 1;
    private static int speedMin = 0, speedMax = 20, speedInit = 1;
    private static JButton newGame, reset;
    private static int hsValue = 0;
    private static boolean gamePause = false, gameStarted = false;

    public SnakeGame(int width, int height, int pixel) {
	super();
	w = width;
	h = height;
	p = pixel;
	//int wOffset = (widthTemp - w/p*p) / 2;
	//int hOffset = (heightTemp - h/p*p) / 2;
	
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
	newGame.setFocusable(false);
	reset.setFocusable(false);
	newGame.addActionListener(this);
	reset.addActionListener(this);

	slider = new JSlider(speedMin, speedMax, speedInit);
	slider.addChangeListener(this);
	slider.addKeyListener(move);
		
	contentPane.add(topPanel, BorderLayout.NORTH);
	contentPane.add(bottomPanel, BorderLayout.SOUTH);
	contentPane.validate();

    bottomPanel.add(newGame);
	bottomPanel.add(reset);
	bottomPanel.add(slider);
	bottomPanel.validate();

	int iniW = w;
	int iniH = h;
	if (w<400) iniW = 400;
	if (h<400) iniH = 400;
	setSize(iniW + 10 + wOffset, iniH + 99 + hOffset);
	wOffset = (iniW - w/p*p) / 2;
	hOffset = (iniH - h/p*p) / 2;
	graph.resizeGraph(wOffset, hOffset);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	contentPane.add(graph, BorderLayout.CENTER);
	setVisible(true);
	graph.repaint();

	this.addComponentListener(new ComponentAdapter() {  
		public void componentResized(ComponentEvent evt) {
		    Component c = (Component)evt.getSource();
		    int widthTemp = getWidth();
		    int heightTemp = getHeight();
		    wOffset = (widthTemp - 10 - w/p*p) / 2;
		    hOffset = (heightTemp - 99 - h/p*p) / 2;
		    graph.resizeGraph(wOffset, hOffset);
		}
	    });
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
			      new Coord(0,0),grid,snake);
	graph = grid.setGraphics(w,h,p,grid);
	graph.addKeyListener(move);
	
	game = new SnakeGame(w, h, p);
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
	System.out.println("     width - Integer width of the playing grid in pixels\n"+
			   "     height - Integer height of the playing grid in pixels\n"+
			   "     segmentsize - Integer size of each snake segement in pixels\n\n"+
			   "     defaults: width = 400, height = 400, segmentsize = 10");
    }
    
    public void setScore() {
    	score.setText(String.valueOf(grid.getPoints()));
    }

    
    @Override 
    public void actionPerformed(ActionEvent e) {
    	JButton sourceEvent = (JButton) e.getSource();
    	if (sourceEvent == newGame) {
    		if (gameStarted) {
    		    restartGame();
    		}
    		else {
    			startGame();
    			gameStarted = true;
    		}
    	}
    	else if (sourceEvent == reset) {
	    hsValue = 0;
	    highScore.setText("0");
	    gamePause = true;
	    gameOver();
    	}
	graph.repaint();
    }

    public void stateChanged(ChangeEvent e) {
	int speedValue = slider.getValue();
	if (speedValue > 0)
	    move.speedTo(speedValue);
    }

    public void setSlider(int speed) {
	slider.setValue(speed);
    }

    public void setHighScore(int nhs) {
	if (hsValue < nhs) hsValue = nhs;
	highScore.setText(String.valueOf(hsValue));
    }
    public void startGame() {
    	move.resetDrct(new Coord(0,1));
    }

    public void restartGame() {
	w = graph.getWidth();
	h = graph.getHeight();
	gameOver.setText("");
	snake.setSnake(w/p/2,0);
	grid.reset(w/p,h/p);
	move.resetDrct(new Coord(0,1));
	graph.resetGraph(w,h,p);
	graph.repaint();
	slider.setValue(1);
	move.speedTo(1);
	setScore();
    }

    public void gameOver() {
    	if (!gamePause) gameOver.setText("GAME OVER!");
    	move.stop();
	gamePause = false;
    }

}
