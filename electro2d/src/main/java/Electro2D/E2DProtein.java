package main.java.Electro2D; /**
 * This class simulates a protein.  It stores the protein's molecular
 * weight, pI, identity, and links to informative internet sites
 * about it.
 *
 * @author Jill Zapoticznyj
 */

import java.awt.*;
import java.util.Random;

public class E2DProtein {

    // the color which will be presented by the protein
    // in the simulation

    private static final Color colors[] = {
            Color.YELLOW,
            new Color(254, 143, 74),
            new Color(160, 11, 206),
            new Color(72, 100, 100),
            new Color(0, 95, 95),
            Color.CYAN,
            new Color(158, 49, 49),
            new Color(0, 135, 16),
            new Color(255, 96, 0)};

    private String myID; //protein title
    private double myMolWt; //molecular weight
    private double myPI; //pI value
    private Color myColor; //color
    private String mySequence; //amino acid sequence data
    private static Color rnaColor = Color.BLUE;
    private static Color dnaColor = Color.RED;
    private static Color enzymeColor = Color.GREEN;
    private static Color hypotheticalColor = Color.PINK;
    private static Color transportColor = new Color(117, 92, 50);
    private static Color receptorColor = new Color(176, 196, 222);
    private static Color transductionColor = new Color(255, 216, 202);
    private String myFunction;

    /**
     * constructor for protein
     *
     * @param id    the protein's type
     * @param molWt the molecular weight of the protein
     * @param pI    the pI value for the protein
     * @param sequence  the sequence of this protein
     * @param fcn   the function of this protein
     */

    public E2DProtein(String id, double molWt, double pI, String sequence, String fcn) {
        myID = id;
        myMolWt = molWt;
        mySequence = sequence;
        myFunction = fcn;
        myPI = pI;
        if (id.toLowerCase().contains("dna")) {
            myColor = dnaColor;
        } else if (id.toLowerCase().contains("ribosomal")) {
            myColor = rnaColor;
        } else if (id.toLowerCase().contains("hypothetical")) {
            myColor = hypotheticalColor;
        } else if (fcn.toLowerCase().contains("enzyme")) {
            myColor = enzymeColor;
        } else if (fcn.toLowerCase().contains("transport")) {
            myColor = transportColor;
        } else if (fcn.toLowerCase().contains("receptor") || fcn.contains("reception")) {
            myColor = receptorColor;
        } else if (fcn.toLowerCase().contains("transduction")) {
            myColor = transductionColor;
        } else {
            Random r = new Random();
            myColor = colors[r.nextInt(9)];
        }
    }

    /**
     * returns protein title
     *
     * @return myID
     */
    public String getID() {
        return myID;
    }

    /**
     * returns the molecular weight of the protein
     *
     * @return myMolWt
     */
    public double getMW() {
        return myMolWt;
    }

    /**
     * returns the specific color that respesents this protein
     *
     * @return myColor
     */
    public Color getColor() {
        return myColor;
    }

    /**
     * returns the pI value of the protein
     *
     * @return myPI
     */
    public double getPI() {
        return myPI;
    }

    /**
     * returns the sequence of the protein
     *
     * @return mySequence
     */
    public String getSequence() {
        return mySequence;
    }

    /**
     * creates a string representation of this object
     *
     * @return myID
     */
    public String toString() {
        return myID;
    }

    /**
     * returns the function of the protein
     *
     * @return myFunction
     */
    public String getFunction() {
        return myFunction;
    }

    /**
     * returns colors
     *
     * @return String[][] of default colors with the first value being the string representing the string and the 2nd value representing the RGB integer of the color
     */
    public static String[][] getColorGuide() {

        String[][] retVal = new String[][]{
                {"DNA in Title", Integer.toString(dnaColor.getRGB())},
                {"Ribosomal in Title", Integer.toString(rnaColor.getRGB())},
                {"Enzyme EC in Function", Integer.toString(enzymeColor.getRGB())},
                {"Hypothetical protein", Integer.toString(hypotheticalColor.getRGB())},
                {"Transport protein in Function", Integer.toString(transportColor.getRGB())},
                {"Receptor in Function", Integer.toString(receptorColor.getRGB())},
                {"Transduction in Function", Integer.toString(transductionColor.getRGB())}
        };

        return retVal;
    }

}