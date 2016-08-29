package main.java.Electro2D;/*
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

import main.java.MainWindows.JBioFrameworkMain;
import main.java.utilities.FastaParser;
import main.java.utilities.GenomeFileParser;
import main.java.MassSpec.MassSpecMain;
import main.java.utilities.BrowserLauncher;

//import GUI components
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;

//import java utilities
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class ProteinFrame extends JFrame {

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
                    System.out.println("URL did not work");
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
        uniSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String urlPre = "http://www.uniprot.org/uniprot/?query=";
                String url = urlPre + swsSearchID;
                try {
                    BrowserLauncher.openURL(url);
                } catch (IOException ex) {
                    System.out.println("URL did not work");
                }
            }
        });

        /*Send to MassSpec button*/
        JButton sendToSpec = new JButton("Run Mass Spectrum");
        sendToSpec.setToolTipText("Send protein sequence to Mass Spec for analysis");
        sendToSpec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextArea input = MassSpecMain.getInputArea();
                input.setText(sequenceString);
                JBioFrameworkMain.getTabs().setSelectedIndex(3);
            }
        });

        //create labels to display the protein information
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
        searchPanel.add(blstSearch);
        searchPanel.add(ncbiSearch);
        searchPanel.add(uniSearch);
        searchPanel.add(sendToSpec);
        this.setLayout(new BorderLayout());
        this.add(proteinInfoPanel, BorderLayout.NORTH);
        this.add(searchPanel, BorderLayout.CENTER);

        pack();
        //setSize(420, 150);
    }

}