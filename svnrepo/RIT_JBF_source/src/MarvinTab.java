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
 * Main GUI class for Marvin integration with JBioFramework.
 * Instantiated by /JBioFrameworkMain/ as a JPanel object (extends JPanel) then
 * added to main frame.
 */

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import chemaxon.marvin.beans.MSketchPane;
import chemaxon.marvin.common.UserSettings;
import chemaxon.marvin.sketch.SketchParameterConstants;

/**
 * Extends JPanel so that the 
 *
 */
public class MarvinTab extends JPanel {

    private JTextArea textarea = new JTextArea(10,50);

    private static MSketchPane marvinPane;

	private MSketchPane createSketchPane() {
		MSketchPane pane = new MSketchPane(createUserSettings());
		pane.setPreferredSize(new Dimension(900, 500));
		return pane;
	}

	private UserSettings createUserSettings() {
		UserSettings settings = new UserSettings(
				            this.getClass().getResourceAsStream("marvin.properties"));
		settings.setProperty(SketchParameterConstants.MENU_CUSTOMIZATION_FILE,
				System.getProperty("user.dir") + "/src/customization.xml");
		settings.setProperty(SketchParameterConstants.SHORTCUTS,
				System.getProperty("user.dir") + "/src/shortchuts.xml");
		settings.setProperty(SketchParameterConstants.TOOLBAR_TEMPLATES + "20",
				":specials:specialTemplates.mrv");
		return settings;
	}

	public JPanel createMainPanel() {
		JPanel topPanel = new JPanel();
		marvinPane = createSketchPane();
		JPanel sketchPanel = new JPanel();
		sketchPanel.add(marvinPane);
		
		topPanel.add(sketchPanel);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(topPanel);
		return mainPanel;
	}

    public static MSketchPane getSketchPane() {
        return marvinPane;
    }
}
