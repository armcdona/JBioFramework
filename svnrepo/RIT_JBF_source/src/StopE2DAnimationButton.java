
/**
 *
 * @author Amanda Fisher
 */

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StopE2DAnimationButton extends JButton implements ActionListener {

    Electro2D electro2D;

    public StopE2DAnimationButton(Electro2D e) {
        super("Stop");
        super.setToolTipText("Stop Animation");
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
