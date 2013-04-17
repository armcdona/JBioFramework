package one.d.electrophoresis;

/**
 * @author  updated  by Bader Alharbi
 *The Swing version 1D Electrophoresis simulation, as Desktop application
 */

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTabbedPane;

public class Electrophoresis extends JPanel {
    JTabbedPane tabPane, tabPane2;
    Parameters paramPanel;
    Simulation simPanel;
    ProteinData dataPanel;
    Plot plotPanel;

    public void Header(){ // @TODO: figure out where we want to put the help button on E1D
        JPanel headPanel = new JPanel();
        JButton help = new JButton("Help");
        help.setToolTipText("Open Help wiki for Electro1D");
        help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String url = "http://sourceforge.net/p/jbf/wiki/Electro1D";
                try{BrowserLauncher.openURL(url);} catch(IOException i){System.err.println( i.getMessage());}    }
        });
        headPanel.add(help);
        super.add(headPanel);
    }

    public Electrophoresis() {
        super.setPreferredSize(new Dimension(550, 450));

        paramPanel = new Parameters(this);
        simPanel = new Simulation(this);
        dataPanel = new ProteinData(this);
        plotPanel = new Plot(this);

//        super.setSize(20,35);
        this.setLayout(new GridLayout(0, 2, 5, 0));

        // set up left Panel
        tabPane = new JTabbedPane();
        tabPane.addTab("Parameters", paramPanel);
        tabPane.addTab("ProteinData", dataPanel);

        // set up right Panel
        tabPane2 = new JTabbedPane();

        tabPane2.add("Casting Tray ", simPanel);
        tabPane2.add("Plot", plotPanel);

//        this.add(headPanel);
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