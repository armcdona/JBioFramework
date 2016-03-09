import java.awt.*;

/**
 * Supposed to alternate the color of a protein dot (make them blink) between green and red, but is not currently used
 */
public class BlinkThread extends Thread {

    private ProteinDotSwingVersion theDot;
    private GelCanvasSwingVersion theGel;
    
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
