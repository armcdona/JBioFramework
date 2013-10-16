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
 *
 * @author Amanda Fisher
 * @contrib Aidan Sawyer [rit: aks5238 | sf daniels-ai]
 */

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import one.d.electrophoresis.Electrophoresis;

/**
 * The main frame for the JBioFramework program.
 *  -Adds Welcome, Electrophoresis, Electro2D, MassSpec and MarvinSketch
 *   applications to itself with a JTabbedPane.
 */
public class JBioFrameworkMain extends JFrame {

    public static final long serialVersionUID = 1L;
    private static JTabbedPane tabbedPane;

    private Welcome welcome;
    private Electrophoresis oneDE;
    private Electro2D electro2D;
    private MassSpecMain spectrometer;
    private MarvinTab marvin;
    /*private [NameOfClass] [user-created reference]*/

    /**
     * Main method for entire program.
     */ 
    public static void main(String[] args) {
        JBioFrameworkMain jbfMain = new JBioFrameworkMain();
    }

    /**
     * Constructor for JBioFrameworkMain Frame object.
     * -sets frame to visible, sets some general behaviors, adds 
     *  panels from Electro1D, Electro2d, etc. and adjusts size.
     */ 
    public JBioFrameworkMain() {

        super("JBioFramework");

        super.setVisible(true);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Welcome", new Welcome());
        tabbedPane.add("Electro1D", new Electrophoresis());
        tabbedPane.addTab("Electro2D", new Electro2D());
        tabbedPane.addTab("Mass Spectrometer", new MassSpecMain());
        tabbedPane.addTab("Marvin Sketch", new MarvinTab().createMainPanel());
        /*tabbedPane.addTab(["name (to be displayed)"], [object]);*/

        add(tabbedPane);

        /**
         * Use a toolkit to find the screen size of the user's monitor
	 * and set the window size to it.
         */

        super.setSize(Toolkit.getDefaultToolkit().getScreenSize());

        this.pack();
    }

    /**
     * returns the JTabbedPane which holds all of our simulation panels.
     */ 
    public static JTabbedPane getTabs() {
        return tabbedPane;
    }
}
