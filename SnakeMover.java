public class Mover implements Runnable {
    int w,h,p,wMax,hMax,speed;
    SnakeGrid grid;
    boolean going = true;
    Coord drct,cp;
   
    /**
     * Initializes the grid and the current target that is to be moved.
     * @param w The width of the grid
     * @param h The height of the grid
     * @param p The size of a cellar
     * @param cx The horizontal location of the center red cell
     * @param cy The vertical location of the center red cell
     * @param grid The reference of the current grid
     */
    public Mover(int w,int h,int p,Coord cp,Coord drct,SnakeGrid grid){
		this.w = w;
		this.h = h;
		this.p = p;
		this.grid = grid;
		this.cp = cp;
		this.drct = drct;
		wMax = w / p - 1;
		hMax = h / p - 1;
    }
    
    /**
     * Executes the moving of the center red cell back and forth.
     */
    public void run(){
	
		// drct is the current direction of moving.

		if ((wMax == 0) || (hMax == 0)) going = false;
		while (going) {
		    // Delay an amount of time
		    try { TimeUnit.MILLISECONDS.sleep(1000/speed);}
		    catch (InterruptedException e){};
		    // When it comes to either end, the direction reverses.
		    if (cx == wMax) drct = -drct;
		    if (cx == 0) drct = -drct;
		    
		    // Remove the cell (which has index 4)
		    grid.clearCell(4);
		    cx += drct;
		    // Draw the new one
		    grid.fillCell(cx,cy);
		}
    }

    public void stop(){
		going = false;
    }
}