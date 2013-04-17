package one.d.electrophoresis;
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
 * @author  updated  by Bader Alharbi
 *The Swing version 1D Electrophoresis simulation, as Desktop application
 */

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;

public class Electrophoresis extends JPanel {
	JTabbedPane tabPane, tabPane2;
	Parameters paramPanel;
	Simulation simPanel;
	ProteinData dataPanel;
	Plot plotPanel;

	public Electrophoresis() {
        super.setPreferredSize(new Dimension(340,500));

        paramPanel = new Parameters(this);
		simPanel = new Simulation(this);
		dataPanel = new ProteinData(this);
		plotPanel = new Plot(this);

		this.setLayout(new GridLayout(0, 2, 5, 0));

		// set up left Panel
		tabPane = new JTabbedPane();
		tabPane.addTab("Parameters", paramPanel);
		tabPane.addTab("ProteinData", dataPanel);

		// set up right Panel
		tabPane2 = new JTabbedPane();

		tabPane2.add("Casting Tray ", simPanel);
		tabPane2.add("Plot", plotPanel);

		this.add(tabPane);
		this.add(tabPane2);

		paramPanel.setDefaults();

	}

	public void addStandard() {
		simPanel.addStandard();
	}

	public void stopRun() {
		simPanel.stopRun();
	}

	public void displaySim() {
		tabPane.setVisible(true);
	}

	public void setAcrylaminde(Acrylamide acrylamide) {
		simPanel.setAcrylamide(acrylamide);
	}

	public void startRun(Protein aprotein[], Protein protein, Protein protein1,
			Protein protein2) {
		simPanel.startRun(aprotein, protein, protein1, protein2);
	}

	public void setPlotData(Protein aprotein[], Protein protein,
			Protein protein1) {
		plotPanel.setResults(aprotein, protein, protein1);
	}

	public void displayData() {
		tabPane2.setVisible(true);

	}

	public void addSample() {
		simPanel.addSample();
	}

	public void setAnimationSpeed(String s) {
		simPanel.setPause(s);
	}

	public void displayProtein(Protein protein) {
		dataPanel.displayData(protein);
	}
}