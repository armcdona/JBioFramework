package main.java.utilities;/*
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
 * utilities.MessageFrame.java
 * <p>
 * This class encapsulates all the functionality required to pop up a frame
 * and display a message to the user.
 *
 * @author Adam Bazinet
 * @author Jill Zapoticznyj
 */

//GUI components

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

//event listeners
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MessageFrame extends JFrame {

    /**
     * variables for the file reading pop-up frame
     **/

    private JPanel panel;              //panel to add components to
    private JLabel label;              //label to display message on
    private String theMessage = "";      //message to display to user

    /**
     * construct and display a simple GUI
     */
    public MessageFrame() {

        //set the title to Alert
        super("Alert");

        //initialize components
        panel = new JPanel();          //init components
        //position the message label to be centered in the frame
        label = new JLabel(theMessage, JLabel.CENTER);

        // allow the user to close the frame
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // set the sizes of the frame and its components
        this.setBounds(20, 140, 600, 100);
        panel.setBounds(0, 0, 600, 100);
        label.setBounds(0, 0, 600, 100);

        //add the components
        this.add(panel);
        panel.add(label);

        super.setVisible(true);
    }

    //set the message
    public void setMessage(String message) {
        theMessage = message;
        label.setText(theMessage);
    }

}



