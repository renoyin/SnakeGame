import java.awt.*;
import javax.swing.*;

public class GraphicsGrid extends JPanel {
	private int width, height, segmentSize;
	private GameGrid gameGrid; 

	public GraphicsGrid(int w, int h, int p, GameGrid g) {
		width = w;
		height = h;
		segmentSize = p;
		gameGrid = g;
    }

    // code below unfinished

    public void draw() {
    	int width  = canvas.getWidth()/xMax;
		int height  = canvas.getHeight()/yMax;
		int wOffset = (canvas.getWidth()-xMax*width)/2;
		int hOffset = (canvas.getHeight()-yMax*height)/2;
    }

    @Override
    protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Point fillCell : fillCells) {
		    int cellX = (fillCell.x * pixelsGrid);
		    int cellY = (fillCell.y * pixelsGrid);
		    g.setColor(Color.RED);
		    g.fillRect(cellX, cellY, pixelsGrid, pixelsGrid);
		}
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, widthGrid, heightGrid);
		
		for (int i = 0; i <= widthGrid; i += pixelsGrid) {
		    g.drawLine(i, 0, i, heightGrid);
		}
		
		for (int i = 0; i <= heightGrid; i += pixelsGrid) {
		    g.drawLine(0, i, widthGrid, i);
		}
    }

    /**
     * Fill a cell in red given its coordinate
     * @param x  x coordinate of the cell
     * @param y  y corrdinate of the cell
     */
    public synchronized void fillCell(int x, int y) {
		fillCells.add(new Point(x, y));
		repaint();
    }

    /**
     * Clear the previous painted cell in the grid
     */
    public synchronized void clearCell() {
		fillCells.remove(4);
		repaint();
    } 
}