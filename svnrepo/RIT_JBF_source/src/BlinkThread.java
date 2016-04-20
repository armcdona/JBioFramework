import java.awt.*;

/**
 * Alternates the color of a protein dot (make them blink) between green and red, but is not currently used.
 */
public class BlinkThread extends Thread {

    private ProteinDot theDot;
    private GelCanvas theGel;
    
    public BlinkThread(ProteinDot p, GelCanvas g ){
	theGel = g;
	theDot = p;
    }

    public void run(){
	
	while( !GelCanvas.getBlink() ){
	}

	while( GelCanvas.getBlink() && !DotThread.getDotState() ){
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
