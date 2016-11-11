package Electro2D;

/**
 * Electro2D.IEFFrame.java
 * <p>
 * This class encapsulates all the functionality required to pop up a
 * frame and display the range and proteins of an Electro2D.IEFProtein.
 *
 * @author Jill Zapoticznyj
 * <p>
 * This code is based off of the Electro2D.ProteinFrame class
 * @author Adam Bazinet - author of Electro2D.ProteinFrame
 */

import java.awt.Frame;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Label;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class IEFFrame extends Frame {

    private Electro2D electro2D;          //reference to calling applet
    private String ptTruncated = "";           //name truncated
    private String names = "";
    private JPanel IEFPanel;           //panel to add components to
    private Rectangle dimensions;         //dimension holders
    private Label titleLabel;   //holds protein name
    private IEFProtein ief;    // the Electro2D.IEFProtein whose information will
    // be displayed
    private Font theFont;                 //font used in this panel
    private String maxRange;   //the maximum pI value held in the Electro2D.IEFProtein
    private String minRange;   //the minimum pI value held in the Electro2D.IEFProtein
    private JScrollPane scroll; //the pane that allows the user to scroll to
    //view all of the contents
    private Vector labels;     // the labels storing the protein names
    private static int xlocation = 0;  //the x location of this frame
    private static int ylocation = 0;  // the y location of this frame

    /**
     * Constructor for Electro2D.IEFFrame
     *
     * @param i - the Electro2D.IEFProtein being represented
     */
    public IEFFrame(IEFProtein i) {
        labels = new Vector();

        //set the font for the information being displayed
        theFont = new Font("Arial", Font.PLAIN, 12);

        //set ief to the value passed as a parameter to this method
        ief = i;

        //set the max and min pI values stored in ief
        maxRange = Double.toString(ief.getMaxPI());
        minRange = Double.toString(ief.getMinPI());

        //set the title to display the maxRange and minRange
        setTitle(minRange + " - " + maxRange);

        IEFPanel = new JPanel();                  //init panel
        IEFPanel.setLayout(null);                //abs. positioning
        IEFPanel.setBackground(new Color(202, 225, 255));
        this.addWindowListener(new WindowAdapter() { //allow user to close win.
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });


        this.setBounds(xlocation, ylocation, 415, 500);   //set frame position
        dimensions = this.getBounds();       //store frame size

        //set the size of IEFPanel and let it know how far to scroll
        IEFPanel.setBounds(0, 0, dimensions.width, dimensions.height);
        IEFPanel.scrollRectToVisible(dimensions);

        //get the names of the proteins stored in ief
        Vector v = i.getNames();

        int location = 30;
        for (int j = 0; j < v.size(); j++) {
            //create a label for each protein in ief and store it in labels
            labels.add(new Label((String) v.elementAt(j)));

            //set the sizes of each label
            ((Label) labels.elementAt(j)).setBounds(5, location, 390, 20);
            ((Label) labels.elementAt(j)).setFont(theFont);
            location = location + 15;
            IEFPanel.add((Label) labels.elementAt(j));
        }

        //adjusts the size of the panel to reflect the number of labels
        // that it holds
        if (dimensions.height < location) {
            IEFPanel.setPreferredSize(new Dimension(dimensions.width, location));
            dimensions = IEFPanel.getBounds();
            IEFPanel.scrollRectToVisible(dimensions);
        } else {
            IEFPanel.setBounds(0, 0, dimensions.width, dimensions.height);
        }

        //stores the panel in a scrollable panel
        scroll = new JScrollPane(IEFPanel);
        scroll.setPreferredSize(new Dimension(415, 500));
        scroll.setWheelScrollingEnabled(true);


        this.add(scroll);              //add components

        // increment the locations for the next Frame created
        xlocation = xlocation + 10;
        ylocation = ylocation + 20;

        // once the location reaches a certain point, start layering
        // frames on top of old frames
        if (ylocation > 500) {
            ylocation = 0;
        }
        if (xlocation > 500) {
            xlocation = 5;
        }
    }

//	public Electro2D.IEFFrame(IEFProteinSwingVersion elementAt) {
//		// temporary  constructor created for fix compiling error 
//	}

}