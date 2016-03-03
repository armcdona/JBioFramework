
/**
 *
 * @author Amanda Fisher
 */

import javax.swing.*;
import java.awt.event.*;

/**
 * The type Stop button swing version.
 */
public class StopButtonSwingVersion extends JButton implements ActionListener {

	/**
	 * The Electro 2 d.
	 */
	Electro2D electro2D;

	/**
	 * Instantiates a new Stop button swing version.
	 *
	 * @param e the e
	 */
	public StopButtonSwingVersion(Electro2D e) {
        super("Stop");
        addActionListener(this);
        electro2D = e;
    }

    public void actionPerformed(ActionEvent e) {

        GelCanvasSwingVersion g = electro2D.getGel();
	g.clearIEF();
	g.resetLocation();
	g.resetRanges();
	g.clearCanvas();
	electro2D.resetIEF();
	IEFProteinSwingVersion.resetProtein();

	if( ProteinDotSwingVersion.getShow() ){
	    ProteinDotSwingVersion.setShow();
	    electro2D.stopThread();
	}
	g.restartCanvas();

	electro2D.resetPlay();
	electro2D.resetSdsStatus();
	electro2D.setBool();
	electro2D.clearpH();
	electro2D.setIEF();
	GelCanvasSwingVersion.setRed();
	GelCanvasSwingVersion.setGreen();
	GelCanvasSwingVersion.setBlue();
	IEFProteinSwingVersion.resetTempWidth();
    }
}
