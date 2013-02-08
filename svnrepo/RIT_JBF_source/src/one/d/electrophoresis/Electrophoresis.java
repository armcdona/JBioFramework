package one.d.electrophoresis;

/**
 * @author  Win-Air
 *The Swing version 1D electrophoresis simulation, as Desktop
 application


 */

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Electrophoresis extends JFrame {
	public static void main(String... args) {
		Electrophoresis application = new Electrophoresis();
		// application.init();
		application.setBounds(0, 0, 450, 520);
		application.setResizable(false);
		application.setDefaultCloseOperation(EXIT_ON_CLOSE);
		application.setVisible(true);
		application.pack();
	}

	public Electrophoresis() {
		buttonPanel = new JPanel();
		masterPanel = new JPanel();
		leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(250, 500));

		// test

		rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(250, 500));
		JTabbedPane tabPane = new JTabbedPane();

		paramPanel = new Parameters(this);
		simPanel = new Simulation(this);
		dataPanel = new ProteinData(this);
		plotPanel = new Plot(this);

		final JButton loadButton = new JButton("Load file");
		final JButton setButton = new JButton("Set Parameters");
		final JButton plotButton = new JButton("Plot Results");
		final JButton simButton = new JButton("Simulation");
		final JButton infButton = new JButton("Protein Info");

		buttonPanel.add(loadButton);
		buttonPanel.add(setButton);
		buttonPanel.add(plotButton);
		buttonPanel.add(simButton);
		buttonPanel.add(infButton);

		// JTabbedPane tabPane = new JTabbedPane();

		class BtnListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (e.getSource().equals(loadButton)) {
					System.out.println("load button works");
				}
				if (e.getSource().equals(setButton)) {

					leftLayout.show(leftPanel, "parameters");

				}
				if (e.getSource().equals(simButton)) {
					rightLayout.show(rightPanel, "simulation");

				}
				if (e.getSource().equals(plotButton)) {
					leftLayout.show(leftPanel, "dataplot");

				}
				if (e.getSource().equals(infButton)) {

					rightLayout.show(rightPanel, "data");

				}
			}

		}

		BtnListener bls = new BtnListener();

		loadButton.addActionListener(bls);
		simButton.addActionListener(bls);
		infButton.addActionListener(bls);
		setButton.addActionListener(bls);

		rightLayout = new CardLayout();
		rightPanel.setLayout(rightLayout);
		rightPanel.add("simulation", simPanel);
		rightPanel.add("data", dataPanel);
		leftLayout = new CardLayout();
		leftPanel.setLayout(leftLayout);
		leftPanel.add("parameters", paramPanel);
		leftPanel.add("dataplot", plotPanel);
		masterPanel.setLayout(new GridLayout(1, 2, 5, 5));
		masterPanel.add(leftPanel);
		masterPanel.add(rightPanel);
		masterPanel.setPreferredSize(new Dimension(400, 400));
		setLayout(new BorderLayout()); // change
		add("North", buttonPanel);
		add("Center", masterPanel);
		paramPanel.setDefaults();

		tabPane.addTab("1ED", masterPanel);
		tabPane.addTab("Emty", new JPanel());
		add(tabPane);
	}

	public void addStandard() {
		simPanel.addStandard();
	}

	public void stopRun() {
		simPanel.stopRun();
	}

	public void displaySim() {
		rightLayout.show(rightPanel, "Simulation");
	}

	public boolean action(Event event, Object obj) {
		if (event.target instanceof JButton)
			return handleButton(obj);
		else
			return false;
	}

	public boolean handleButton(Object obj) {
		if ("Set Parameters".equals(obj)) {
			leftLayout.show(leftPanel, "parameters");
			return true;
		}
		if ("Protein Info".equals(obj)) {
			rightLayout.show(rightPanel, "data");
			return true;
		}
		if ("Simulation".equals(obj)) {
			rightLayout.show(rightPanel, "simulation");
			return true;
		}
		if ("Plot Results".equals(obj)) {
			leftLayout.show(leftPanel, "dataplot");
			return true;
		} else
			return false; // TODO testing
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
		rightLayout.show(rightPanel, "data");
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

	Parameters paramPanel;
	Simulation simPanel;
	ProteinData dataPanel;
	Plot plotPanel;
	CardLayout leftLayout;
	CardLayout rightLayout;
	JPanel buttonPanel;
	JPanel masterPanel;
	JPanel leftPanel;
	JPanel rightPanel;

}