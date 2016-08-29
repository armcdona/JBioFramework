package main.java.MassSpec;
/**
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
 * @author Amanda Fisher
 */

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.filechooser.FileNameExtensionFilter;

//Listeners and Event handlers
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

//etc.
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Arranges the gui in the correct layout for the electro2D frame
 */
public class MainPanelGUI extends JPanel {

    private String[] proteaseChoices = {"Trypsin", "Chymotrypsin", "Proteinase K", "Thermolysin"};
    private HelpButton help;
    private AboutButton about;
    private static JTextArea inputArea; // static so ProteinFrame can interact with it.
    private JTextField lowerRange;
    private JTextField upperRange;
    private JComboBox proteaseBox;
    private TandemGraphGUI tandemGraph;
    private JLabel massDisplay;
    private ToggleFragmentButton blueBs;
    private ToggleFragmentButton redYs;
    private OutputGraphGUI outputGraph;
    private Ion ion;

    /**
     * The constructor uses a GridBagLayout to arrange the eight different elements of the GUI- the label explaining the input box, the input box,
     * the label OR, the button to load a sequence, the protease selection drop down box, the info label, the big graph and the small graph.
     */
    public MassSpecMain() {
        //set the layout of the JPanel it's extending.
        super(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(1, 5, 1, 5);

        //Head Panel including JLabel for the name.
        JPanel headP = new JPanel();
        JLabel head = new JLabel("Tandem Mass Spectrometer");
        head.setFont(new Font("SansSerif", Font.BOLD, 18));

        headP.add(head);
        constraints.gridy = 0;
        super.add(headP, constraints);

        //panel for the information buttons (help and about)
        JPanel infoButtonsPanel = new JPanel();
        help = new HelpButton();
        about = new AboutButton();
        infoButtonsPanel.add(help);

	 /*about button which uses /BrowserLauncher/ to open main page for JBF.*/
        about = new JButton("About");

        //add hover text
        about.setToolTipText("About the program");

        //set functionality
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    BrowserLauncher.openURL("https://sourceforge.net/projects/jbf/");
                } catch (IOException i) {
                    System.err.println(i.getMessage());
                }
            }
        });

        //add 'about' to infoButtonPanel
        infoButtonsPanel.add(about);

        //add infoButtonsPanel to main MassSpec panel.
        constraints.gridx = 0;
        constraints.gridy = 1;
        super.add(infoButtonsPanel, constraints);


	/*Loading sequences. allows user to add custom sequence "OR" load one.
     * contains JLabels, JTextArea, . */
        JLabel inputLabel = new JLabel("Input protein sequence to be analyzed: ");
        constraints.gridy = 2;
//        grid.setConstraints(inputLabel, constraints);
        add(inputLabel, constraints);

        inputArea = new JTextArea(7, 20);
        inputArea.setToolTipText("type or paste protein sequence here");
        inputArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(inputArea);
        constraints.gridy = 3;
//        grid.setConstraints(scrollPane, constraints);
        add(scrollPane, constraints);

        JLabel orLabel = new JLabel("OR");
        constraints.gridy = 4;
//        grid.setConstraints(orLabel, constraints);
        add(orLabel, constraints);

        JButton loadButton = new JButton("Load Sequence From File");
        loadButton.setToolTipText("Load from protein file"); //@todo: include file types?
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("FASTA files",
                        "fasta");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(chooser);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String parsedSequence = FastaParser.parse(chooser.getSelectedFile());
                    inputArea.setText(parsedSequence);
                }
            }
        });
        constraints.gridy = 5;
//        grid.setConstraints(loadButton, constraints);
        add(loadButton, constraints);

        /*selecting the protease*/
        JLabel proteaseLabel = new JLabel("Select protease: ");
        constraints.gridy = 6;
//        grid.setConstraints(proteaseLabel, constraints);
        add(proteaseLabel, constraints);

        proteaseBox = new JComboBox(proteaseChoices);
        constraints.gridy = 7;
//        grid.setConstraints(proteaseBox, constraints);
        add(proteaseBox, constraints);

	/*setting m/e range*/
        JLabel selectRangeLabel = new JLabel("Enter m/e range: ");
        constraints.gridy = 8;
//        grid.setConstraints(selectRangeLabel, constraints);
        add(selectRangeLabel, constraints);

        JPanel rangeSelectionUpperPanel = new JPanel();
        lowerRange = new JTextField("0", 5);
        JLabel lowerRangeLabel = new JLabel("Lower Limit: ");
        rangeSelectionUpperPanel.add(lowerRangeLabel);
        rangeSelectionUpperPanel.add(lowerRange);
        constraints.gridy = 9;
//        grid.setConstraints(rangeSelectionUpperPanel, constraints);
        add(rangeSelectionUpperPanel, constraints);

        JPanel rangeSelectionLowerPanel = new JPanel();
        upperRange = new JTextField("3000", 5);
        JLabel upperRangeLabel = new JLabel("Upper Limit: ");
        rangeSelectionLowerPanel.add(upperRangeLabel);
        rangeSelectionLowerPanel.add(upperRange);
        constraints.gridy = 10;
//        grid.setConstraints(rangeSelectionLowerPanel, constraints);
        add(rangeSelectionLowerPanel, constraints);

	/* Run Spectrum (button) */
        JButton runButton = new JButton("Run Spectrum");
        runButton.setToolTipText("Outputs the mass spectrum data on right panel");
        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Spectrometer.runAnalysis(inputArea.getText(), outputGraph,
                        proteaseBox.getSelectedItem().toString());
            }
        });
        constraints.gridy = 11;
//        grid.setConstraints(runButton, constraints);
        add(runButton, constraints);

        massDisplay = new JLabel("<html> Mass: N/A <P>");
        constraints.gridy = 12;
//        grid.setConstraints(massDisplay, constraints);
        add(massDisplay, constraints);

	/*toggle b/y fragments*/
        blueBs = new ToggleFragmentButton("B fragments", true);
        constraints.gridy = 13;
//        grid.setConstraints(blueBs, constraints);
        add(blueBs, constraints);

        redYs = new ToggleFragmentButton("Y fragments", true);
        constraints.gridy = 14;
//        grid.setConstraints(redYs, constraints);
        add(redYs, constraints);

	/*tandem (top) graph (upper right of MassSpec tab) */
        tandemGraph = new TandemGraphGUI();
        constraints.gridy = 0;
        constraints.gridx = 1;
        constraints.gridheight = 8;
        constraints.weighty = 1.0;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
//        grid.setConstraints(tandemGraph, constraints);
        add(tandemGraph, constraints);

	/*output (bottom) graph (bottom right of MassSpec tab)*/
        outputGraph = new OutputGraphGUI(this);
        constraints.gridy = 8;
        constraints.gridheight = 6;
//        grid.setConstraints(outputGraph, constraints);
        add(outputGraph, constraints);

    }

    /**
     * runTandem changes the information displayed in the infoScreen Jlabel when a user clicks on a peak in the OutputGraphGUI.
     * It also alerts TandemGraphGUI that there is peptide sequencing to be done.
     *
     * @param selected the selected
     */
    public void runTandem(Ion selected) {
        ion = selected;
        DecimalFormat massFormat = new DecimalFormat("##.##");
        String mass = massFormat.format(ion.getMass());
        massDisplay.setText("<html> Mass: " + mass);
        tandemGraph.drawSequencePeaks(ion);
    }

    /**
     * Inner class that loads a protein file selected by the user.
     */
    private class LoadButton extends JButton implements ActionListener {

        /**
         * Constructor passes the String to be displayed on the button to JButton's constructor and registers itself as its own actionListener.
         */
        public LoadButton() {
            super("Load Sequence From File");
            addActionListener(this);
        }

        /**
         * When the button is clicked it opens a JFileChooser so the user may select which file they would like to obtain sequence information from.
         */
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("FASTA files", "fasta");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String parsedSequence = FastaParser.parse(chooser.getSelectedFile());
                inputArea.setText(parsedSequence);
            }

        }

    } // End of LoadButton

    /**
     * Inner class that repaints the MassSpec.TandemGraphGUI when the user clicks on it.
     */
    private class RunButton extends JButton implements ActionListener {

        /**
         * Constructor passes the String to be displayed on the button to JButton's constructor and registers itself as its own actionListener.
         */
        public RunButton() {
            super("Run Spectrum");
            addActionListener(this);
        }

        /**
         * The actionPerformed method is called when the user clicks on the button. It begins the simulation.
         *
         * @param e Unused.
         */
        public void actionPerformed(ActionEvent e) {
            Spectrometer.runAnalysis(inputArea.getText(), outputGraph,
                    proteaseBox.getSelectedItem().toString());
        }

    } // End of RunButton

    /**
     * Inner class that repaints the TandemGraphGUI when the user clicks on it.
     */
    private class ToggleFragmentButton extends JCheckBox implements ItemListener {

        /**
         * Constructor passes the String to be displayed on the button to JCheckBox's constructor and registers itself as its own actionListener.
         *
         * @param text  the text
         * @param state the state
         */
        public ToggleFragmentButton(String text, boolean state) {
            super(text, state);
            addItemListener(this);
        }

        /**
         * The actionPerformed method is called when the user clicks on the button. It repaints TandemGraphGUI.
         *
         * @param e Unused.
         */
        public void itemStateChanged(ItemEvent e) {
            Object source = e.getItemSelectable();
            if (source == blueBs) {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    tandemGraph.setBlueBs(false);
                } else {
                    tandemGraph.setBlueBs(true);
                }
            } else {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    tandemGraph.setRedYs(false);
                } else {
                    tandemGraph.setRedYs(true);
                }
            }
            if (ion != null) {
                tandemGraph.drawSequencePeaks(ion);
            }
        }

    } // End of p/ToggleFragmentButton/

    /**
     * The ProteinFrame class from the Electro2D simulation of JBioFramework calls this to set the input area's
     * text to the sequence of a protein the user clicked on in the gel canvas.
     *
     * @return The JTextArea that holds a protein's sequence.
     */
    public static JTextArea getInputArea() {
        return inputArea;
    }

    /**
     * Called by OutputGraphGUI's setPeaks method to sort out which ion peaks to display based on the user specified m/e range.
     *
     * @return The lower limit of the user selected range.
     */
    public double getLowerLimit() {
        double lower;
        if (lowerRange.getText().contains(",")) {
            String noComma = lowerRange.getText();
            int index = noComma.indexOf(",");
            noComma = noComma.substring(0, index) +
                    noComma.substring(index + 1, noComma.length());
            try {
                lower = Double.valueOf(noComma);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Did not recognize Lower Limit as a number. Using default lower limit of 0.");
                lowerRange.setText("0");
                return 0;
            }
        } else {
            try {
                lower = Double.valueOf(lowerRange.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Did not recognize Lower Limit as a number. Using default lower limit of 0.");
                lowerRange.setText("0");
                return 0;
            }
        }
        if (lower < 0 || lower > 20000) {
            lower = 0;
            JOptionPane.showMessageDialog(null, "Lower Limit out of bounds (0 to 20,000). Set to default of 0.");
            lowerRange.setText("0");
        }
        if (lower > getUpperLimit()) {
            lower = 0;
            JOptionPane.showMessageDialog(null, "Lower Limit is higher than Upper Limit. Set to default of 0.");
            lowerRange.setText("0");
        }
        return lower;
    }

    /**
     * Called by OutputGraphGUI's setPeaks method to sort out which ion peaks to display based on the user specified m/e range.
     *
     * @return The upper limit of the user selected range.
     */
    public double getUpperLimit() {
        double upper;
        if (upperRange.getText().contains(",")) {
            String noComma = upperRange.getText();
            int index = noComma.indexOf(",");
            noComma = noComma.substring(0, index) +
                    noComma.substring(index + 1, noComma.length());
            try {
                upper = Double.valueOf(noComma);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Did not recognize Upper Limit as a number. Using default upper limit of 3000.");
                upperRange.setText("3000");
                return 0;
            }
        } else {
            try {
                upper = Double.valueOf(upperRange.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Did not recognize Upper Limit as a number. Using default upper limit of 3000.");
                upperRange.setText("3000");
                return 3000;
            }
        }
        if (upper > 20000) {
            upper = 20000;
            JOptionPane.showMessageDialog(null, "Upper Limit too high (0 to 20,000). Set to maximum of 20,000.");
            upperRange.setText("20000");
        } else if (upper < 0 ){
            upper = 0;
            JOptionPane.showMessageDialog(null, "Upper Limit too low (0 to 20,000). Set to maximum of 20,000.");
            upperRange.setText("0");
        }
        return upper;
    }
}
