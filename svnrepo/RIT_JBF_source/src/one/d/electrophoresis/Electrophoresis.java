package one.d.electrophoresis;

/**
 * @author Bader Alharbi
 *The Swing version 1D Electrophoresis simulation,
 *  Desktop application
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

    public void Header(){
    // @TODO: figure out where we want to put the help button on E1D
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

    /**
     * default constructor
     */
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

    /**
     *  addStandard() add standards proteins
     */
    public void addStandard() {
        simPanel.addStandard();
    }

    /**
     * Stop running the simulation on the gel
     */
    public void stopRun() {
        simPanel.stopRun();
    }

    /**
     * display the simulation panel
     */
    public void displaySim() {
        tabPane.setVisible(true);
    }

    /**
     *  set the acrylamide percentage
     * @param acrylamide
     */
    public void setAcrylamide(Acrylamide acrylamide) {
        simPanel.setAcrylamide(acrylamide);
    }

    /**
     * start running the simulation
     * @param aprotein
     * @param protein
     * @param protein1
     * @param protein2
     */
    public void startRun(Protein aprotein[], Protein protein, Protein protein1,
                         Protein protein2) {
        simPanel.startRun(aprotein, protein, protein1, protein2);
    }

    /**
     *  setPlotData() set the plot data
     * @param aprotein
     * @param protein
     * @param protein1
     */
    public void setPlotData(Protein aprotein[], Protein protein,
                            Protein protein1) {
        plotPanel.setResults(aprotein, protein, protein1);
    }

    /**
     * displayData()display the data
     */
    public void displayData() {
        tabPane2.setVisible(true);

    }

    /**
     *  adding the sample
     */
    public void addSample() {
        simPanel.addSample();
    }

    /**
     * configure the animation speed
     * @param s
     */
    public void setAnimationSpeed(String s) {
        simPanel.setPause(s);
    }

    /**
     *    displayProtein(Protein protein)
     *   display protein info on the plotting panel
     * @param protein
     */
    public void displayProtein(Protein protein) {
        dataPanel.displayData(protein);
    }
}