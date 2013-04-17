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
 * MessageFrame.java
 *
 * This class encapsulates all the functionality required to pop up a frame
 * and display a message to the user.
 *
 * @author Adam Bazinet
 * @author Jill Zapoticznyj
 */

import java.awt.Frame;
import java.awt.Panel;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class MessageFrame extends Frame {

 /** variables for the file reading pop-up frame **/

    private Panel thePanel;              //panel to add components to
    private Label theLabel;              //label to display message on
    private String theMessage = "";      //message to display to user
    
    //construct simple GUI
    public MessageFrame() {
	
	//set the title to Alert
	setTitle("Alert");
	thePanel = new Panel();          //init components
	//position the message label to be centered in the frame
	theLabel = new Label(theMessage, Label.CENTER);
	
	// allow the user to close the frame
	this.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
		    dispose();
		}
	    });

	// set the sizes of the frame and its components
	this.setBounds(20,140,600,100);
	thePanel.setBounds(0,0,600,100);
	theLabel.setBounds(0,0,600,100);
	
	//add the components
	this.add(thePanel);
	thePanel.add(theLabel);
    }
    //set the message
    public void setMessage(String message) {
	theMessage = message;
	theLabel.setText(theMessage);
    }
}//MessageFrame



