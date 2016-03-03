import java.awt.*;

/**
 * The type Blink thread.
 */
public class BlinkThread extends Thread {

    private ProteinDotSwingVersion theDot;
    private GelCanvasSwingVersion theGel;

	/**
	 * Instantiates a new Blink thread.
	 *
	 * @param p the p
	 * @param g the g
	 */
	public BlinkThread( ProteinDotSwingVersion p, GelCanvasSwingVersion g ){
	theGel = g;
	theDot = p;
    }

    public void run(){
	
	while( !GelCanvasSwingVersion.getBlink() ){
	}

	while( GelCanvasSwingVersion.getBlink() && !DotThread.getDotState() ){
	    if( theDot.getColor() == Color.RED ){
		theDot.changeColor( Color.GREEN );
	    }
	    else{
		theDot.changeColor( Color.RED );
	    }
	    theGel.repaint();
	    try{
		sleep( (long)1000 );
	    }catch( Exception e ){}
	}
	
	theDot.changeColor( Color.GREEN );

    }
}
