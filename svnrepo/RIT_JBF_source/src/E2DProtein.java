/**
 * This class simulates a protein.  It stores the protein's molecular
 * weight, pI, identity, and links to informative internet sites
 * about it.
 * 
 * @author Jill Zapoticznyj
 */

import java.awt.*;
import java.util.*;

/**
 * The type E 2 d protein.
 */
public class E2DProtein {

    // the color which will be presented by the protein
    // in the simulation

    private static final Color colors[] = {
        Color.YELLOW,
	    new Color( 254, 143, 74 ), //Light Orange
        new Color( 160, 11, 206 ), //Purple
	    new Color( 72, 100, 100 ), //Dark Cyan
        new Color( 0, 95, 95 ),    //Darker Cyan
        Color.CYAN,
        new Color( 158, 49, 49 ),  //Dark Red
        new Color( 0, 135, 16 ),   //Green
	    new Color( 255, 96, 0 )    //Orange
    }; //list of colors for proteins of a type that isn't recognized
    
    private String myID; //protein title
    private double myMolWt; //molecular weight
    private double myPI; //pI value
    private Color myColor; //color
    private String mySequence; //amino acid sequence data
    private static Color rnaColor = Color.BLUE;
    private static Color dnaColor = Color.RED;
    private static Color enzymeColor = Color.GREEN;
    private static Color hypotheticalColor = Color.PINK;
    private static Color transportColor = new Color( 117, 92, 50 ); //Brown
    private static Color receptorColor = new Color( 176, 196, 222 ); //Light Blue
    private static Color transductionColor = new Color( 255, 216, 202 ); //Light Pink
    private String myFunction;

	/**
	 * constructor for protein
	 *
	 * @param id       the type of protein
	 * @param molWt    the molecular weight of the protein
	 * @param pI       the pI value for the protein
	 * @param sequence the sequence
	 * @param fcn      the function of this protein
	 */
	public E2DProtein( String id, double molWt, double pI, String sequence, String fcn ){
	    myID = id;
	    myMolWt = molWt;
	    mySequence = sequence;
	    myFunction = fcn;
	    myPI = pI;
	    if(id.toLowerCase().contains("dna")){
	        myColor = dnaColor;
	    }
	    else if(id.toLowerCase().contains("ribosomal")){
	        myColor = rnaColor;
	    }
	    else if(id.toLowerCase().contains("hypothetical")){
	        myColor = hypotheticalColor;
	    }
	    else if(id.toLowerCase().contains("enzyme")){
	        myColor = enzymeColor;
	    }
	    else if(fcn.toLowerCase().contains("transport")){
	        myColor = transportColor;
	    }
	    else if(fcn.toLowerCase().contains("receptor") || fcn.contains("reception")){
	        myColor = receptorColor;
	    }
	    else if(fcn.toLowerCase().contains("transduction")){
	        myColor = transductionColor;
	    }
	    else{
	        myColor = colors[new Random().nextInt(colors.length)];
	    }
    }

	/**
	 * returns protein title
	 *
	 * @return myID string
	 */
	public String getID(){
        return myID;
    }

	/**
	 * returns molecular weight
	 *
	 * @return myMolWt double
	 */
	public double getMW(){
        return myMolWt;
    }

	/**
	 * returns color
	 *
	 * @return myColor color
	 */
	public Color getColor(){
        return myColor;
    }

	/**
	 * returns pI value
	 *
	 * @return myPI double
	 */
	public double getPI(){
        return myPI;
    }

	/**
	 * returns sequence data
	 *
	 * @return mySequence string
	 */
	public String getSequence(){
        return mySequence;
    }

    /**
     * creates a string representation of this object
     *
     * @return myID
     */
    public String toString(){
	return myID;
    }

	/**
	 * returns protein function
	 *
	 * @return myFunction string
	 */
	public String getFunction(){
	return myFunction;
    }

	/**
	 * returns colors
	 *
	 * @return HashMap of default colors
	 */
	public static String[][] getColorGuide() {
	
	    String[][] retVal = new String[][]{
                {"dna in Title", Integer.toString(dnaColor.getRGB())},
                {"ribosomal in Title", Integer.toString(rnaColor.getRGB())},
                {"Enzyme EC in Function", Integer.toString(enzymeColor.getRGB())},
                {"hypothetical protein", Integer.toString(hypotheticalColor.getRGB())},
                {"transport protein in Function", Integer.toString(transportColor.getRGB())},
                {"receptor in Function", Integer.toString(receptorColor.getRGB())},
                {"transduction in Function", Integer.toString(transductionColor.getRGB())}
        };
	
	    return retVal;
    }

}