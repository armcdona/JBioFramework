
/**
 * @author Amanda Fisher
 */

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * The type Restart button swing version.
 */
public class RestartButton extends JButton implements ActionListener {

    /**
     * The Electro 2 d.
     */
    Electro2D electro2D;

    /**
     * Instantiates a new Restart button swing version.
     *
     * @param e the e
     */
    public RestartButton(Electro2D e) {
        super("Restart");
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

        if (ProteinDot.getShow()) {
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
        PlayButton.setCompare(false);
        electro2D.resetBothProteinLists();
        electro2D.setSequences(new Vector());
        electro2D.setSequenceTitles(new Vector());
        electro2D.setMolecularWeights(new Vector());
        electro2D.setPiValues(new Vector());
        electro2D.setFunctionValues(new Vector());
        electro2D.setSequences2(new Vector());
        electro2D.setSequenceTitles2(new Vector());
        electro2D.setMolecularWeights2(new Vector());
        electro2D.setPiValues2(new Vector());
        electro2D.setFunctionValues2(new Vector());
        electro2D.refreshProteinList();
        electro2D.refreshProteinList2();

    }

}
