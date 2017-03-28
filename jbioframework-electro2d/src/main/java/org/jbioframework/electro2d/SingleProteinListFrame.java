package org.jbioframework.electro2d;
/**
 * This class constructs a window to display the protein list and
 * allows the manipulation of that list to alter the protein sequence run on
 * the Electro2D.GelCanvas.
 *
 * @author Amanda Fisher
 */

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.util.ArrayList;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * The type Single protein list frame.
 */
public class SingleProteinListFrame extends JFrame {

    /**
     * electro2D reference
     */
    Electro2D electro2D;
    /**
     * The Sequence one list.
     */
    JList sequenceOneList;
    /**
     * The Sequence titles one.
     */
    ArrayList sequenceTitlesOne;
    /**
     * The Positions one.
     */
    ArrayList<Integer> positionsOne;
    /**
     * The Copy sequence one.
     */
    ArrayList copySequenceOne;

    /**
     * Constructor for the window; sets up the instance variables and builds the GUI.
     *
     * @param param     The title of the window.
     * @param electro2D Reference to the electro2D where the protein lists are.
     */
    public SingleProteinListFrame(String param, Electro2D electro2D) {
        super(param);
        this.electro2D = electro2D;
        setLayout(new GridBagLayout());
        JLabel sequenceOneLabel = new JLabel("Sequence");
        sequenceOneList = new JList();
        sequenceTitlesOne = new ArrayList();
        copySequenceOne = (ArrayList) sequenceTitlesOne.clone();
        positionsOne = new ArrayList<Integer>();
        JScrollPane sequenceOneScroll = new JScrollPane(sequenceOneList);
        JButton selectedButton = new JButton("Remove Selected Proteins");

        GridBagConstraints c = new GridBagConstraints();
        c.insets = (new Insets(5, 5, 5, 5));
        c.gridx = 0;
        c.gridy = 0;
        add(sequenceOneLabel, c);

        c.gridy = 2;
        add(selectedButton, c);

        c.gridy = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        add(sequenceOneScroll, c);

        selectedButton.addActionListener(new SelectedListener());

        pack();
        setVisible(false);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    /**
     * This method is called by electro2D's refreshProteinList and
     * refreshProteinList2 method when a new protein sequence is loaded.
     * updateSequences copies the protein sequence titles into its own lists
     * for display.
     *
     * @param pL1 the p l 1
     * @param pL2 the p l 2
     */
    public void updateSequences(ArrayList pL1, ArrayList pL2) {
        sequenceOneList.setListData(new Vector<>(pL1));
        sequenceTitlesOne = new ArrayList(pL1);
        copySequenceOne = (ArrayList) sequenceTitlesOne.clone();
        positionsOne = new ArrayList<>();
    }

    /**
     * This method is called to collect the positions of the elements still within
     * the manipulated sequence into ArrayLists for use with synchronizing Electro2D.Electro2D
     * with the user's manipulations.
     */
    public void updatePositions() {
        positionsOne = new ArrayList<>();
        for (int x = 0; x < copySequenceOne.size(); x++) {
            if (sequenceTitlesOne.contains(copySequenceOne.get(x))) {
                positionsOne.add(x);
            }
        }
        if (positionsOne.size() == 0) {
            positionsOne.add(-1);
        }

        if (positionsOne.size() > 0) {
            electro2D.setSequencesReady(true);
        }

        if (positionsOne.get(0) < 0) {
            electro2D.setSequencesReady(false);
        }
    }

    /**
     * Listener for the remove selected proteins button.
     * Removes all proteins that are selected.
     */
    private class SelectedListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int[] oneIndexes = sequenceOneList.getSelectedIndices();
            ArrayList oneProteins = new ArrayList();
            for (int x = 0; x < oneIndexes.length; x++) {
                oneProteins.add(sequenceTitlesOne.get(oneIndexes[x]));
            }
            sequenceTitlesOne.removeAll(oneProteins);
            // The following line ensures that multiple edits can be made,
            // even before and after a gel run.
            copySequenceOne = (ArrayList) sequenceTitlesOne.clone();

            sequenceOneList.setListData(new Vector<>(sequenceTitlesOne));
            sequenceOneList.validate();
            updatePositions();
        }
    }

    /**
     * The following two methods are accessors for the position ArrayLists.
     * Electro2D.Electro2D will call them in its get methods for the ArrayLists used in
     * gel filtration in order to carry over the manipulations done by the user.
     *
     * @return the positions of each element still in the sequence.
     */
    public ArrayList<Integer> getPositionsOne() {
        return positionsOne;
    }

    /**
     * Gets positions two. Currently unused as protein comparison is currently disabled)
     *
     * @return the positions two
     */
    public ArrayList<Integer> getPositionsTwo() {
        ArrayList<Integer> positionsTwo = new ArrayList<>();
        return positionsTwo;
    }

}

