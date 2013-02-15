
/**
 *
 * @author Amanda Fisher
 */

import javax.swing.*;
import java.awt.event.*;

public class StopButtonSwingVersion extends JButton implements ActionListener {

    Electro2D electro2D;

    public StopButtonSwingVersion(Electro2D e) {
        super("Stop");
        addActionListener(this);
        electro2D = e;
    }

    public void actionPerformed(ActionEvent e) {

    GelCanvas g = electro2D.getGel();
	g.clearIEF();
	g.resetLocation();
	g.resetRanges();
	g.clearCanvas();
	electro2D.resetIEF();
	IEFProtein.resetProtein();

	if( ProteinDot.getShow() ){
	    ProteinDot.setShow();
	    electro2D.stopThread();
	}
	g.restartCanvas();

	electro2D.resetPlay();
	electro2D.resetSdsStatus();
	electro2D.setBool();
	electro2D.clearpH();
	electro2D.setIEF();
	GelCanvas.setRed();
	GelCanvas.setGreen();
	GelCanvas.setBlue();
	IEFProtein.resetTempWidth();
    }
}
