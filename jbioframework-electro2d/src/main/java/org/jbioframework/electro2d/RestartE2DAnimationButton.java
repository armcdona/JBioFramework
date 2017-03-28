package org.jbioframework.electro2d;
/**
 * @author Amanda Fisher
 */

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RestartE2DAnimationButton extends JButton implements ActionListener {

    Electro2D electro2D;

    public RestartE2DAnimationButton(Electro2D e) {
        super("Restart");
        setToolTipText("Restart animation");
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
        PlayE2AnimationButton.setCompare(false);
        electro2D.resetBothProteinLists();
        electro2D.setSequences(new ArrayList());
        electro2D.setSequenceTitles(new ArrayList());
        electro2D.setMolecularWeights(new ArrayList());
        electro2D.setPiValues(new ArrayList());
        electro2D.setFunctionValues(new ArrayList());
        electro2D.setSequences2(new ArrayList());
        electro2D.setSequenceTitles2(new ArrayList());
        electro2D.setMolecularWeights2(new ArrayList());
        electro2D.setPiValues2(new ArrayList());
        electro2D.setFunctionValues2(new ArrayList());
        electro2D.refreshProteinList();
        electro2D.refreshProteinList2();

    }

}
