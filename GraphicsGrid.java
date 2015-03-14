import java.awt.*;
import javax.swing.*;
import java.lang.Thread;

public class GraphicsGrid extends JPanel {
    private int width, height, segSize;
    private int xSeg, ySeg;
    private int widthGrid, heightGrid;
    private int wOffset, hOffset;
    private GameGrid gameGrid; 
    
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
    
    public void resizeGraph(int wOS, int hOS) {
	wOffset = wOS;
	hOffset = hOS;
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
	
	for (int i = 0; i <= width; i += segSize)
	    g.drawLine(i + wOffset, hOffset, 
		       i + wOffset, hOffset+heightGrid);
	
	for (int i = 0; i <= height; i += segSize)
	    g.drawLine(wOffset, i + hOffset, 
		       wOffset+widthGrid, i + hOffset);

	//System.out.println(hOffsetT + " "+hOffsetB+" "+wOffsetL+" "+wOffsetR);
    }
    
    public void fillCell() {
    	repaint();
    }
}