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
 * Play button on /Electro2D/. Calls /ColorFrame/ and uses /Electro2D/
 * methods/states.
 *
 * |Electro2D|
 * @author Amanda Fisher
 */

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
* JButton object with window to /Electro2D/.
* selectively set button functionality and readouts to fit the states
* of current /Electro2D/ run.
*/
public class PlayE2AnimationButton extends JButton implements ActionListener {

        Electro2D electro2D;
        boolean sdsPlaying;
        private boolean iefDrawn;
        private boolean sdsDrawn;
        private static boolean compareFiles;
        String choice;

   /**
    * constructor; initializes booleans, names button, gets access to /Electro2D/

    */
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

    /**
     * standard accessor for the boolean of whether the sdsPage animation
     * has been drawn or not.
     */
    public boolean getSdsStatus(){
	return sdsDrawn;
    }

    /**
     * Change button name to "Start" and sdsPlaying boolean to false.
     */
    public void resetPlay(){
	sdsPlaying = false;
        this.setText("Start");
    }

    /**
     * set boolean for whether the ieF animation has been drawn
     */
    public void resetIEF(){
	iefDrawn = false;
    }

    /**
     * set boolean for whether the sds page animation has been drawn.
     */
    public void resetSdsStatus(){
	sdsDrawn = false;
    }

    /**
     * sets 'compareFiles' boolean to whatever truth value is passed in the parameter.
     */
    public static void setCompare(boolean bool){
	compareFiles = bool;
    }

    /**
     * Make the hovertext (ToolTipText) read differently to reflect how the draw states
     * of both animations affect what the user wants to do.
     *
     * @author AidanSawyer
     */
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

    /**
     * Set the functionality of the Play button to start the next animation
     * (either IEF or sdsPage) and open /ColorFrame/.
     *
     */
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
        new ColorFrame().showKey(); //TODO: set position of colorFrame popup on screen.
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
