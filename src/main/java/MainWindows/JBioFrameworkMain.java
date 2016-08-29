package main.java.MainWindows;

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
 * The main frame of the entire suite.
 * Calls for the main GUI panels for other classes and adds them to a tabbed
 * frame.
 *
 * @author Amanda Fisher
 * @contrib Aidan Sawyer [rit: aks5238 | sf daniels-ai]
 */

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import main.java.Electro1D.Electrophoresis;
import main.java.Electro2D.Electro2D;
import main.java.MassSpec.MassSpecMain;
import main.java.MainWindows.MarvinTab;

/**
 * The main frame for the JBioFramework program.
 *  -Adds Main.Welcome, Electrophoresis, Electro2D.Electro2D, MassSpec and MarvinSketch
 *   applications to the JFrame it extends  with a JTabbedPane.
 */
public class JBioFrameworkMain extends JFrame {

    public static final long serialVersionUID = 1L;
    private static JTabbedPane tabbedPane;

    private Electrophoresis oneDE;
    private Electro2D electro2D;
    private MassSpecMain spectrometer;
    private MarvinTab marvin;
    /*private [NameOfClass] [user-created reference]*/

    /**
     * Main method for entire program.
     * Creates a new instantiation of the JBioFramework class (calls constructor)
     *
     * @param args program arguments
     */
    public static void main(String[] args) {
        new JBioFrameworkMain();
    }

    /**
     * Constructor for Main.JBioFrameworkMain Frame object.
     * -sets frame to visible, sets some general behaviors, adds
     *  panels from Electro1D, Electro2d, etc. and adjusts size.
     */
    public JBioFrameworkMain() {
        //calls JFrame constructor with String parametr which sets the displayed
        //name at the top of the window.
        super("JBioFramework");

        //swing component which facilitates switching between different panels.
        tabbedPane = new JTabbedPane();

        //add all of the relevant panels for each application's GUI to tabbedPane
        tabbedPane.addTab("Welcome", new Welcome());
        //tabbedPane.addTab("IonExchange", new Ionex()); //Not working at the current moment
        tabbedPane.addTab("Electro1D", new Electrophoresis());
        tabbedPane.addTab("Electro2D", new Electro2D());
        tabbedPane.addTab("Spectrometer", new MassSpecMain());
        tabbedPane.addTab("Marvin Sketch", new MarvinTab().createMainPanel());
        /*tabbedPane.addTab(["name (to be displayed)"], [object]);*/

        //add tabbedPane to frame
        add(tabbedPane);


        /**various last steps for the JFrame.*/

        // Method inherited from Window by JFrame which "Causes this Window to be
        // sized to fit the preferred size and layouts of its subcomponents.
        super.pack();

        // Use a toolkit to find the screen size of the user's monitor

        //scale window size so as to not take up the entire screen
//    double newWidth = Toolkit.getDefaultToolkit().getScreenSize().width*(3/4);
//    double newHeight = Toolkit.getDefaultToolkit().getScreenSize().height*(3/4);

        //set size of application to the size of the screen
//    super.setSize(Toolkit.getDefaultToolkit().getScreenSize());

        //ensure that the program stops when the Window for the GUI is closed.
        //TODO create WindowManager to set prompt for review or session log.
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //make the completed, correctly-sized window visible.
        // [should be the last thing called, to prevent the user from seeing
        //  the window get resized]
        super.setVisible(true);
    }

    /**
     * returns the JTabbedPane which holds all of our simulation panels.
     * tabbedPane is a Swing component which facilitates the switching between
     * multiple components.
     *
     * @return returns the tabbed pane
     */
    public static JTabbedPane getTabs() {
        return tabbedPane;
    }

}
