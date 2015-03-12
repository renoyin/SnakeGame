import java.awt.*;
import javax.swing.*;
import java.lang.Thread;

public class GraphicsGrid extends JPanel {
	private int width, height, segSize;
	private int xSeg, ySeg, wOffset, hOffset;
	private GameGrid gameGrid; 

	public GraphicsGrid(int w, int h, int p, 
						GameGrid g) {
		width = w;
		height = h;
		segSize = p;
		// check this
		gameGrid = g; 
		xSeg = width / segSize;
		ySeg = height / segSize;
		wOffset = (width - xSeg * segSize) / 2;
		hOffset = (height - ySeg * segSize) / 2;
		setFocusable(true);
		requestFocusInWindow();
    }

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
		g.drawRect(wOffset, hOffset, 
				   wOffset + width, hOffset + height);
		
		for (int i = 0; i <= width; i += segSize)
		    g.drawLine(i + hOffset, wOffset, 
		    	       i + hOffset, height + wOffset);
		
		for (int i = 0; i <= height; i += segSize)
		    g.drawLine(wOffset, i + hOffset, 
		    	       width + wOffset, i + hOffset);
    }

    public void fillCell() {
    	repaint();
    }
}