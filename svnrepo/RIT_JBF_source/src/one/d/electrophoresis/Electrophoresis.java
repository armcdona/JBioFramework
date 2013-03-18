package one.d.electrophoresis;

/**
 * @author  updated  by Bader Alharbi
 *The Swing version 1D Electrophoresis simulation, as Desktop application
 */

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Electrophoresis extends JFrame {
	JTabbedPane tabPane, tabPane2;
	Parameters paramPanel;
	Simulation simPanel;
	ProteinData dataPanel;
	Plot plotPanel;

	public Electrophoresis(Container pane) {
		paramPanel = new Parameters(this);
		simPanel = new Simulation(this);
		dataPanel = new ProteinData(this);
		plotPanel = new Plot(this);

		pane.setLayout(new GridLayout(0, 2, 5, 0));

		// set up left Panel
		tabPane = new JTabbedPane();
		tabPane.addTab("Parameters", paramPanel);
		tabPane.addTab("ProteinData", dataPanel);

		// set up right Panel
		tabPane2 = new JTabbedPane();

		tabPane2.add("Casting Tray ", simPanel);
		tabPane2.add("Plot", plotPanel);

		pane.add(tabPane);
		pane.add(tabPane2);

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

	public static void main(String... args) {

		// Create and set up the window.
		JFrame frame = new JFrame("Electrophoresis");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Set up the content pane.
		new Electrophoresis(frame.getContentPane());
		frame.setPreferredSize(new Dimension(622, 500));
		frame.pack();
		frame.setVisible(true);

	}

}