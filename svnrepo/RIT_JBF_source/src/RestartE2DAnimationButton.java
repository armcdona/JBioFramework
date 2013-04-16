/*
 * Copyright (C) 2013 Rochester Institute of Technology
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU General Public License for more details.
 */
/**
 *
 * @author Amanda Fisher
 */

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

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
        PlayE2AnimationButton.setCompare(false);
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
