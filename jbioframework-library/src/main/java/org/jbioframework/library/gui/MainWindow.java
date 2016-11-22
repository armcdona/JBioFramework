package org.jbioframework.library.gui;

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
import java.awt.*;

/**
 * The main frame for the JBioFramework program.
 *  -Adds Main.Welcome, Electrophoresis, Electro2D.Electro2D, MassSpec and MarvinSketch
 *   applications to the JFrame it extends  with a JTabbedPane.
 */
public class MainWindow extends JFrame {

    public static final long serialVersionUID = 1L;
    private static JTabbedPane tabbedPane;

    public MainWindow(String simulationName) {
        super("JBioFramework "+simulationName);
        tabbedPane = new JTabbedPane();
        this.add(tabbedPane);
        super.pack();
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(false);
    }

    public void addTab(Component tab) {
        tabbedPane.add(tab);
        super.pack();
    }

    public void toggleVisibility() {
        if (super.isVisible()) {
            super.setVisible(false);
        } else {
            super.setVisible(true);
        }
    }

}