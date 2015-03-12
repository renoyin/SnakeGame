import java.awt.*;
import javax.swing.*;

public class GraphicsGrid extends JPanel {
	private int width, height, segSize;
	private int xSeg, ySeg, wOffset, hOffset;
	private GameGrid gameGrid; 

	public GraphicsGrid(int w, int h, int p, GameGrid g) {
		width = w;
		height = h;
		segSize = p;
		gameGrid = g;
		xSeg = width / segSize;
		ySeg = height / segSize;
		wOffset = (width - xSeg * segSize + 10) / 2;
		hOffset = (height - ySeg * segSize + 35) / 2;
    }

    // code below unfinished
    @Override
    protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		/*
		for (Point fillCell : fillCells) {
		    int cellX = (fillCell.x * pixelsGrid);
		    int cellY = (fillCell.y * pixelsGrid);
		    g.setColor(Color.RED);
		    g.fillRect(cellX, cellY, pixelsGrid, pixelsGrid);
		}
		*/
		g.setColor(Color.BLACK);
		g.drawRect(wOffset, hOffset, 
				   wOffset + width, hOffset + height);
		
		for (int i = 0; i <= width; i += segSize) {
		    g.drawLine(i + hOffset, wOffset, 
		    	       i + hOffset, height + wOffset);
		}
		
		for (int i = 0; i <= height; i += segSize) {
		    g.drawLine(wOffset, i + hOffset, 
		    	       width + wOffset, i + hOffset);
		}
    }

    public synchronized void fillCell() {
    	g.setColor(c);
		fillCells.add(new Point(x, y));
		repaint();
    }

    public synchronized void clearCell() {
		fillCells.remove(4);
		repaint();
    } 
}