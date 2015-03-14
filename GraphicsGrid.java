/**
 * This class includes methods related to painting graphs.
 * Other class can call methods in this class to draw grid
 * and fill cells.
 */

import java.awt.*;
import javax.swing.*;
import java.lang.Thread;

public class GraphicsGrid extends JPanel {
    private int width, height, segSize;
    private int xSeg, ySeg, wOffset, hOffset;
    private int widthGrid, heightGrid;
    private GameGrid gameGrid; 
    
    /**
	 * Constructor
	 * @param w width of the grid
	 * @param h height of the grid
	 * @param p size of each segment
	 * @param g reference to GameGrid instance
	 */
    public GraphicsGrid(int w, int h, int p, 
			GameGrid g) {
	width = w;
	height = h;
	segSize = p;
	gameGrid = g; 

	xSeg = width / segSize;
	ySeg = height / segSize;
	widthGrid = xSeg * segSize;
	heightGrid = ySeg * segSize;
	wOffset = (width - xSeg * segSize) / 2;
	hOffset = (height - ySeg * segSize) / 2;
	setFocusable(true);
	requestFocusInWindow();
    }
    
    /**
     * Resets the grid to a given size.
     * @param w width of grid 
     * @param h height of grid
     * @param p size of each segment
     */
    public void resetGraph(int w, int h, int p) {
	width = w;
	height = h;
	segSize = p;
	xSeg = width / segSize;
	ySeg = height / segSize;
	widthGrid = xSeg * segSize;
	heightGrid = ySeg * segSize;
	wOffset = (width - xSeg * segSize) / 2;
	hOffset = (height - ySeg * segSize) / 2;
    }
    
    /**
     * Adjusts the horizontal and vertical offset in order. 
     * to center the grid in the window
     * @param wOS offset for left and right
     * @param hOS offset for top and bottom
     */
    public void resizeGraph(int wOS, int hOS) {
	wOffset = wOS;
	hOffset = hOS;
    }

	/**
     * This method will be called once repaint() is invoked.
     * Draws grid with offset and paints all the cells with
     * different colors.
     * @param g  default Graphics object
     */
    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	
	for (int i = 0; i < xSeg; i++) 
	    for (int j = 0; j < ySeg; j++) {
		int cellX = (i * segSize + wOffset);
		int cellY = (j * segSize + hOffset);
		g.setColor(gameGrid.getColor(i, j));
		g.fillRect(cellX, cellY, segSize, segSize);
	    }
	
	g.setColor(Color.BLACK);
	
	for (int i = 0; i <= width; i += segSize)
	    g.drawLine(i + wOffset, hOffset, 
		       i + wOffset, hOffset+heightGrid);
	
	for (int i = 0; i <= height; i += segSize)
	    g.drawLine(wOffset, i + hOffset, 
		       wOffset+widthGrid, i + hOffset);
    }
    
    /**
     * Refreshs the grid and all the cells.
     */
    public void fillCell() {
    	repaint();
    }
}