package org.jbioframework.electro2d;

/**
 * Electro2D.ProteinFrame.java
 * <p>
 * This class encapsulates all the functionality required to pop up a frame
 * and display information about a particular protein.
 *
 * @author Jill Zapoticznyj
 * @author Adam Bazinet
 * @author Aidan Sawyer
 * <p>
 * <p>
 * 10/17/2011: Added functionality to let a protein's sequence be given to the
 * tandem mass spec simulation. @author Amanda Fisher
 */

import org.jbioframework.library.gui.MarvinTab;
import org.jbioframework.library.utilities.AminoAcidTranslator;
import org.jbioframework.library.utilities.BrowserLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


public class ProteinFrame extends JFrame {

    final Logger logger = LoggerFactory.getLogger(ProteinFrame.class);
    private Electro2D electro2D;           //reference to calling applet
    private String proteinTitle;           //name of the protein
    private String ptTruncated;            //name truncated
    private JPanel proteinInfoPanel;       //panel to add components to
    private JPanel searchPanel;
    private JLabel titleLabel;             //holds protein name
    private JLabel mwLabel;                //protein MW
    private JLabel piLabel;                //protein pI
    private String searchID = null;       // the id for the GenBank search
    private String swsSearchID = null;    // id for the SwissProt search
    private int fileNum;                  // while file the proteins came from
    private String sequenceString; // the sequence of amino acids for
    // the protein
    private String proteinFunction = "";  // the function of the protein
    private JLabel function;               // protein function
    private JLabel functionLabel;
    private ArrayList<JLabel> functionList;

    /**
     * Constructor - creates the Electro2D.ProteinFrame object as well as initializes
     * all of the data members
     *
     * @param e a reference to the Electro2D.Electro2D class
     * @param pt the title of the protein whose information is displayed
     */
    public ProteinFrame(Electro2D e, String pt, int filenum) {

        //give the frame a reference to Electro2D.Electro2D
        electro2D = e;
        //set the title of the protein
        proteinTitle = pt;
        ptTruncated = proteinTitle;
        fileNum = filenum;
        setTitle("Protein Information");

        proteinInfoPanel = new JPanel();                  //init panel
        searchPanel = new JPanel();
        proteinInfoPanel.setLayout(new BoxLayout(proteinInfoPanel, BoxLayout.Y_AXIS));
        searchPanel.setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);

        //get protein function
        proteinFunction = electro2D.getFunctionbyTitle(proteinTitle);
        proteinFunction = proteinFunction.trim();
        sequenceString = electro2D.getSequencebyTitle(proteinTitle);


        /*Blast Search Button*/
        JButton blstSearch = new JButton("Blast Search");
        blstSearch.setToolTipText("Performs BLAST search for the protein sequence");
        blstSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String urlPre = "http://web.expasy.org/cgi-bin/blast/blast.pl?sequence=";
                String url = urlPre + sequenceString;
                try {
                    BrowserLauncher.openURL(url);
                } catch (IOException ex) {
                    System.out.println("URL did not work");
                }
            }
        });

        //depending on the database file used, extract the name of the protein
        // and create a button to allow a GenBank search for that protein
        int index = proteinTitle.indexOf("|");
        if (index != -1) {
            //if the protein is from a fasta file, the title is an abbreviated
            //form of the proteinTitle string
            searchID = proteinTitle.substring(index + 1);
            index = searchID.indexOf("|");
            while (index != -1 && index != searchID.lastIndexOf("|")) {
                searchID = searchID.substring(index + 1,
                        searchID.lastIndexOf("|"));
                index = searchID.indexOf("|");
            }
            if (index != -1) {
                searchID = searchID.substring(index + 1);
            }
        } else if (index == -1) {
            //if the protein is not from a fasta file...

            index = proteinTitle.indexOf("\u003B");
            if (index != -1) {
                //...but from a protein databank file, the id is the protein
                // title with the semicolon truncated
                searchID = proteinTitle.substring(0, index);
            } else {
                //...or from a protein databank file, the id is the protein
                // title.
                searchID = proteinTitle;
            }
        }

        /*NCBI search button*/
        JButton ncbiSearch = new JButton("NCBI Search");
        ncbiSearch.setToolTipText("Performs NCBI search for the protein sequence");
        ncbiSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String urlPre = "http://www.ncbi.nlm.nih.gov/entrez/query.fcgi?db=protein&cmd=search&term=";
                ;
                String url = urlPre + searchID;
                try {
                    BrowserLauncher.openURL(url);
                } catch (IOException ex) {
                    logger.info("URL did not work");
                }
            }
        });

        //using the file type to determine the string to assign it,
        // create a button to allow a SwissProt/TrEMBL search for the protein

        if (proteinTitle.indexOf("|") < 0) {
            swsSearchID = proteinTitle;
        } else {
            swsSearchID = proteinTitle.substring(4,
                    proteinTitle.lastIndexOf("|"));
            swsSearchID = swsSearchID.substring(
                    swsSearchID.lastIndexOf("|") + 1);
        }

        /*Uniprot search button*/
        JButton uniSearch = new JButton("Uniprot Search");
        uniSearch.setToolTipText("Performs Uniprot search for the protein sequence");
        uniSearch.addActionListener(e13 -> {
            String urlPre = "http://www.uniprot.org/uniprot/?query=";
            String url = urlPre + swsSearchID;
            try {
                BrowserLauncher.openURL(url);
            } catch (IOException ex) {
                System.out.println("URL did not work");
            }
        });

        JButton marvinButton = new JButton("Show in MarvinSketch");
        marvinButton.setToolTipText("Shows this protein in marvin sketch");
        marvinButton.addActionListener(e12 -> {
            MarvinTab.getSketchPane().setMol(AminoAcidTranslator.translate(sequenceString));
            MainWindow.getTabs().setSelectedIndex(MainWindow.getTabs().indexOfTab("Mass Spectrometer"));
        });

        JButton copyButton = new JButton("Copy protein sequence");
        copyButton.setToolTipText("Copies the protein sequence to the clipboard");
        copyButton.addActionListener(e1 -> Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new StringSelection(sequenceString), null));

        //create labels to display the protein information
        logger.error("Protein Title: "+proteinTitle);
        titleLabel = new JLabel(ptTruncated);
        mwLabel = new JLabel("Molecular Weight (MW): " +
                electro2D.getMWbyTitle(proteinTitle));
        piLabel = new JLabel("pI Value (pI): " +
                electro2D.getPIbyTitle(proteinTitle));

        functionLabel = new JLabel("Function: " + proteinFunction);

        add(proteinInfoPanel);
        proteinInfoPanel.add(titleLabel);
        proteinInfoPanel.add(mwLabel);
        proteinInfoPanel.add(piLabel);
        proteinInfoPanel.add(functionLabel);
        proteinInfoPanel.add(marvinButton);
        proteinInfoPanel.add(copyButton);
        searchPanel.add(blstSearch);
        searchPanel.add(ncbiSearch);
        searchPanel.add(uniSearch);
        //searchPanel.add(sendToSpec);
        this.setLayout(new BorderLayout());
        this.add(proteinInfoPanel, BorderLayout.NORTH);
        this.add(searchPanel, BorderLayout.CENTER);

        pack();
        //setSize(420, 150);
    }

}