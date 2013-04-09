/*
 * This class extends ArrayList to function as a 'holder' for AminoAcids.
 * Ion knows the total mass and charge of itself, based on the AminoAcids
 * it contains, and calculates and sets its own mass/charge ratio. Ion also 
 * knows the color it should be when displayed on a graph (black for initial
 * fragmetnation, blue for b fragments and red for y fragmetns in sequencing.
 * The variable hits is set by Spectrometer as it counts how many of each type
 * of ion it has. Hits is used to determine the ion's intensity on the
 * OutputGraphGUI and TandemGraphGUI. Ion also knows its position on the
 * OutputGraphGUI and TandemGraphGUI so the user can click on individual peaks.
 * 
 */

/**
 *
 * @author Amanda Fisher
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.io.*;

import javax.swing.*;

public class Ion extends ArrayList<SpecAminoAcid> {

    private double totalMass = 0;
    private int totalCharge = 0;
    private double hits = 0;
    private int xCoordinate = 0;
    private String sequence;
    Color color = Color.BLACK;

    /**
     * Adds the SpecAminoAcid to the end of the Ion. Ion adds its mass to 
     * the appropriate instance variable. Charge of an Ion will always be one in
     * peptide sequencing.
     *
     * @param a SpecAminoAcid to be added to the end of the Ion.
     * @return true if successfully added AmmionAcid.
     */
    @Override
    public boolean add(SpecAminoAcid a) {
        super.add(a);
        // add the mass of the amino acid to the ion
        setMass(totalMass + a.getMass());
        // if this is the second or greater amino acid added to the chain;
        if(this.size() > 1) {
            // subtract the mass in Daltons of H2O as the amino acids dehydrate
            // together to form the chain
            setMass(totalMass - 18.01528);
        }
        return true;
    }
    /**
     *  This is used to get the pop-up window to display the
     *  amino acid sequence that is translated from the Amino Acid Translator 
     *  after it is sent from the ion setters and getters
     * 
     */
    
    public void displaySequence() {
    	// this is the code that gives the pop up box for the chemical structure to put in Marvin Sketch
    	// there is a text area inside of a panel and frame that allows the size to be changed.
	    
    	final JFrame frame = new JFrame("Name for MarvinSketch");
    	JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
    	JTextArea proteinSet = new JTextArea("",250,500);
		proteinSet.append(
	       "Instructions: Select the name of the Sequence and Copy and Paste it into MarvinSketch.\n \n" + 
	       AminoAcidTranslator.translate(this.sequence)+
	       " \n \n To paste into MarvinSketch, you will first need to download or open the program.\n" +
	       "Next, Go to Edit > Import Name, there  you can paste in the sequence displayed above.\n " +
	       "To display the structure properly, go to Edit> Transformation > Flip> Flip Horizontally.\n \n" +
	       "Note: The longer the sequence the more likely it is to display diagonally, simply select all the atoms in the structure and move it where you would like it to be."
	       
	    );
        panel.add(proteinSet, BorderLayout.CENTER);

        // Add a button that will load the protein in Marvin Sketch
        JButton marvinButton = new JButton("Show in MarvinSketch");
        marvinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MarvinTab.getSketchPane().setMol(sequence);
                frame.dispose();
            }
        });
        panel.add(marvinButton, BorderLayout.SOUTH);

		frame.add(panel);
		frame.setSize(900,250);
    	frame.setVisible(true);
    	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
   
    	
    private void setSize(int i, int j) {
		// TODO Auto-generated method stub
		
	}
	private Font newFont(String string, int i) {
		// TODO Auto-generated method stub
		return null;
	}
	private Dimension newDimension(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
     * Used by Ion in add method to keep a running total of the mass, and by
     * TandemGraphGUI to adjust the molecular weight of an ion after
     * sequencing fragmentation (single H gets taken away/put on depending on
     * if it is a b or y fragment).
     *
     * @param mass New totalMass.
     */
    public void setMass(double mass) {
        totalMass = mass;
    }
    
    // This will set the sequence given from the ion fragment in the bottom peaks of the graph
    public void setSequence(String sequence) {
    	this.sequence = sequence;
    }
    // Gets the Sequence to be used in the Translator
    public String getSequence() {
    	return sequence;
    }

    /**
     * Used only by Spectrometer in RunAnalysis to set the charge of the ion to
     * one. Look for a better way to ensure that all ions only have a charge of one.
     *
     * @param charge New totalCharge.
     */
    public void setCharge(int charge) {
        totalCharge = charge;
    }

    /**
     * Used by Spectrometer to count how many ions share a specific mass charge
     * ratio.
     *
     * @param i New hits count.
     */
    public void setHits(double i) {
        hits = i;
    }

    /**
     * Used by OutputGraphGUI to let the Ion know where it is on the graph.
     *
     * @param x
     */
    public void setXCoordinate(int x) {
        xCoordinate = x;
    }

    /**
     * Used to return the Ion's mass.
     * 
     * @return totalMass.
     */
    public double getMass() {
        return totalMass;
    }

    /**
     * Used to return the Ion's charge. Should always return one; think about
     * removing this function.
     *
     * @return totalCharge.
     */
    public int getCharge() {
        return totalCharge;
    }

    /**
     * Used to return the Ion's mass charge ratio. Should always just be mass
     * over one. Think about removing this method.
     *
     * @return massChargeRatio.
     */
    public double getMassChargeRatio() {
        return totalMass/(double)totalCharge;
    }

    /**
     * Used to return the Ion's hits count.
     *
     * @return hits.
     */
    public double getHits() {
        return hits;
    }

    /**
     * Used by Spectrometer to determine if a user clicked on this Ion or not.
     *
     * @return xCoordinate.
     */
    public int getXCoordinate() {
        return xCoordinate;
    }

    /**
     * Used by TandemGraphGUI to set b fragments to blue and y fragments to red.
     *
     * @param c desired color for the Ion's peak on a graph
     */
    public void setColor(Color c) {
        color = c;
    }

    /**
     * Used by TandemGraphGUI to draw the peaks on its graph with the
     * appropriate color coding for b and y fragments.
     *
     * @return color this Ion's peak should be on a graph
     */
    public Color getColor() {
        return color;
    }
}
