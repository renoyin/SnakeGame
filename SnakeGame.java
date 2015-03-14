/**
 * A snake game.
 * The snake starts at the top mid of the blockgrid and starts going downwards.
 * After every 10 moves or turns, an obstacle shows randomly.
 * Every turn makes the snake 1 unit longer and gives the user 10 points.
 * After every 100 points, the snake speeds up.
 * User can also control the speed by the slider.
 * @author SHUMING CAO A99088201
 * @author YICHENG YIN A99076232
 * @version 10.10
 * @date 2014-03-13
 */

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.io.IOException;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.TimeUnit;
import java.awt.event.*;
import javax.swing.event.*;

/**
 * The main body of the project, and also the class of JFrame(the window).
 * Asks for user input, create the window and the contents inside.
 * Detects the actions from buttons and slider.
 */
public class SnakeGame extends JFrame implements ChangeListener, ActionListener {
	private static int w = 400, h = 400, p = 10, wOffset, hOffset;
    private static GameGrid grid;
    private static SnakeMover move;
    private static Snake snake;
    private static GraphicsGrid graph;
    private static SnakeGame game;
    private static JLabel text1, text2, score, highScore, gameOver;
    private static JPanel topPanel, bottomPanel;
    private Container contentPane = getContentPane();
    private static JSlider slider;
    // variables for the slider
    private static int sliderValue = 1, speedMin = 0, speedMax = 20, speedInit = 1;
    private static JButton newGame, reset;
    private static int hsValue = 0; // The value of current high score
    private static boolean gamePause = false, gameStarted = false;

/**
     * Creates a new window that contains a grid of certain width and height.
     * @param width The value of width in pixels of the grid inside this window
     * @param height The value of height in pixels of the grid inside this window
     * @param pixel The length of one side of a cell in pixels.
     */
    public SnakeGame(int width, int height, int pixel) {
	super();
	w = width;
	h = height;
	p = pixel;
	// set the top panel 
	topPanel = new JPanel();
	topPanel.setLayout(new GridLayout(2, 3));
	text1 = new JLabel("Score: ");
	text2 = new JLabel("High Score: ");
	score = new JLabel("0");
	highScore = new JLabel("0");
	gameOver = new JLabel("");
	// add component to top panel
	topPanel.add(text1);
	topPanel.add(score);
	topPanel.add(gameOver);
	topPanel.add(text2);
	topPanel.add(highScore);
	//set bottom panel
	bottomPanel = new JPanel();
	bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	newGame = new JButton("NEW GAME");
	reset = new JButton("Reset!");
	newGame.setFocusable(false);
	reset.setFocusable(false);
	newGame.addActionListener(this);
	reset.addActionListener(this);
	// set slider 
	slider = new JSlider(speedMin, speedMax, speedInit);
	slider.addChangeListener(this);
	slider.addKeyListener(move);
	// add components to bottom panel
	bottomPanel.add(newGame);
	bottomPanel.add(reset);
	bottomPanel.add(slider);
	bottomPanel.validate();
	// add panels to contentPane	
	contentPane.add(topPanel, BorderLayout.NORTH);
	contentPane.add(bottomPanel, BorderLayout.SOUTH);
	contentPane.validate();

	int iniW = w;
	int iniH = h;
	if (w<400) iniW = 400;
	if (h<400) iniH = 400;
	// 99 is approximately the height of the two
	// labels above and the buttons below.
	// 10 is approximately the extra width needed to 
	// fit the graph grid.
	setSize(iniW + 10 + wOffset, iniH + 99 + hOffset);
	wOffset = (iniW - w/p*p) / 2;
	hOffset = (iniH - h/p*p) / 2;
	graph.resizeGraph(wOffset, hOffset);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	contentPane.add(graph, BorderLayout.CENTER);
	setVisible(true);
	graph.repaint();

	// Responds to the action of resizing the window 
	// and centering the graphic grid.
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
	// Initialize the window, the graphgrid, the gamegrid and the mover thread.
	snake = new Snake(w/p/2,0);
	grid = new GameGrid(w/p,h/p,snake);
	move = new SnakeMover(new Coord(w/p/2,0),
			      new Coord(0,0),grid,snake);
	graph = grid.setGraphics(w,h,p,grid);
	graph.addKeyListener(move);
	game = new SnakeGame(w, h, p);
	grid.passGame(game);
	Thread t = new Thread(move);

	// Run the mover thread.		
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
    
    /**
	 * Prints the usage statement.
     */
    public static void printHelp() {
	System.out.println("$ java SnakeGame"+
				" [width height segmentsize]\n"+
			   	"     width - Integer width"+
			   	" of the playing grid in pixels\n"+
			   	"     height - Integer height of"+
			   	" the playing grid in pixels\n"+
			   	"     segmentsize - Integer size"+
			   	" of each snake segement in pixels\n\n"+
			   	"     defaults: width = 400, "+
			   	"height = 400, segmentsize = 10");
    }
    
    /**
     * Displays the score in the window.
     */
    public void setScore() {
    	score.setText(String.valueOf(grid.getPoints()));
    }

    /**
     * Catches the actions of buttons being clicked.
     * Calls different methods if different buttons are being clicked.
     */
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

    /**
     * Catches the action of slider moving and updates the speed.
     */
    public void stateChanged(ChangeEvent e) {
	int speedValue = slider.getValue();
	if (speedValue > 0)
	    move.speedTo(speedValue);
    }

    /**
     * Sets the slider as the speed changes.
     */
    public void setSlider(int speed) {
	slider.setValue(speed);
    }

    /**
     * Sets the new high score if the score is higher than the current one.
     * @param nhs The new current score that may be the total high score.
     */
    public void setHighScore(int nhs) {
	if (hsValue < nhs) hsValue = nhs;
	highScore.setText(String.valueOf(hsValue));
    }

    /**
     * Starts the game by setting the mover thread's speed to default.
     */
    public void startGame() {
    	move.resetDrct(new Coord(0,1));
    }

    /**
     * Starts the game again by initializing everything to initial state.
     */
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

    /**
     * If game is paused by the reset button, this method only stops the game.
     * If game is stopped because of death, this method stops the game
     * and displays GAME OVER.
     */
    public void gameOver() {
    	if (!gamePause) gameOver.setText("GAME OVER!");
    	move.stop();
		gamePause = false;
    }
}
