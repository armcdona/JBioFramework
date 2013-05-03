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

public class PlayE2AnimationButton extends JButton implements ActionListener {

    Electro2D electro2D;
    boolean sdsPlaying;
    private boolean iefDrawn;
    private boolean sdsDrawn;
    private static boolean compareFiles;
    String choice;

    public PlayE2AnimationButton(Electro2D e) {

        super("Start");
        addActionListener(this);
        electro2D = e;
        sdsPlaying = false;
        iefDrawn = false;
        sdsDrawn = false;
        compareFiles = false;
        setHoverText();
    }

    public boolean getSdsStatus(){
	return sdsDrawn;
    }

    public void resetPlay(){
	sdsPlaying = false;
        this.setText("Start");
    }

    public void resetIEF(){
	iefDrawn = false;
    }

    public void resetSdsStatus(){
	sdsDrawn = false;
    }

    public static void setCompare(boolean bool){
	compareFiles = bool;
    }

    public void setHoverText(){
        if(sdsPlaying){
            super.setText("Stop");
            setToolTipText("Stop separation");
        }
        else if(!iefDrawn && !sdsDrawn){
            super.setToolTipText("Start IEF pH separation");
        }else if(iefDrawn && !sdsDrawn){
            super.setToolTipText("Start SDS-Page separation");
        }else if(iefDrawn && sdsDrawn && !sdsPlaying){
            super.setToolTipText("Press 'Restart' to start over.");
        }else{
            super.setToolTipText("Press 'Add Proteins' to begin");
        }
    }

    public void actionPerformed(ActionEvent e) {

        if(sdsPlaying) {
        super.setText("Stop");
        super.setToolTipText("Stop Separation");
	    electro2D.stopThread();
        sdsPlaying = false;

        }
	// otherwise determine which parts of the animation need to be drawn
	// and start the appropriate thread.
	else {
	    //get the animation the user wishes to see
        electro2D.clearpH();
	    choice = electro2D.getAnimationChoice();
	    if( electro2D.getSequencesReady()){

		//if the data for the animation needs to be processed,
		//do so
		    if( choice.equals( "IEF" )){
			electro2D.getGel().prepare();
			electro2D.resetBool();
                if( compareFiles ){
			        electro2D.getGel().prepare2();
			    }
		    }

		//if the user selected IEF animation and the image is not
		// already displayed on the screen, perform the IEF
		// animation
		if( choice.equals( "IEF") ){
		    if( !iefDrawn ){
			electro2D.restartIEF();
			iefDrawn = true;
			electro2D.getGel().resetReLine();
		    }
        new ColorFrame().showKey(); //@Todo: set position on screen
        }
		//if the user selected SDS-PAGE animation, and the IEF is
		// already drawn, perform the SDS-PAGE animation
		else if( choice.equals( "SDS-PAGE" )){
                    if( iefDrawn ){
			electro2D.getGel().clearCanvas();
                        electro2D.getGel().clearIEF();
			electro2D.getGel().resetLocation();
                        electro2D.clearpH();
			sdsPlaying = true;
			electro2D.restartThread();
			sdsDrawn = true;

            }
		}
        setHoverText();

        }
	}
    }
}
