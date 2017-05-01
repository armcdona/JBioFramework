package org.jbioframework.electro2d;
/**
 * GUI and main class for 2D Electrophoresis simulation
 *
 * @author Adam Bazinet
 * @author Jill Zapoticznyj
 * @contrib Benjamin Russell (brr1922@rit.edu)
 * @contrib Aidan Sawyer (aks5238@rit.edu)
 */

import java.awt.event.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Component;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.ArrayList;

import org.jbioframework.library.gui.FileFrame;
import org.jbioframework.library.protein.Protein;
import org.jbioframework.library.utilities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main electro2D class.
 */
public class Electro2D extends JPanel implements ActionListener {

    private final Logger logger = LoggerFactory.getLogger(Electro2D.class);
    private FileFrame fileFrame;        //pop up for loading file data
    private SingleProteinListFrame proteinListFrame; //pop up for displaying protein lists

    /** components of the main applet **/
    private GelCanvas gelCanvas;          //area where animation takes place
    private PlayE2AnimationButton playButton;        //starts/pauses animation
    private RestartE2DAnimationButton restartButton;  //restarts animation
    private JButton csvButton;          //writes to csv file
    private java.awt.List proteinList;    //current protein list
    private int[] selectedIndexes;        //selected indexes in the list
    private JLabel animationChooser;      //select animation to control
    private JComboBox rangeChooser;     //select the range for IEF
    private DotThread dotThread;          //thread controlling the SDS-PAGE
    //animation
    private IEFThread iefThread;          //thread controlling IEF animation
    private boolean resetPressed;         //detects whether reset was pressed
    //or not
    private RangeImage rangeImage;    //the disabled image for entering a range
    private Graphics graphics;
    private boolean rangeReload;      //determines whether or not the user 
    //enters a pH range manually or not

    private JComboBox percentAcrylamide; //the Choices for entering the
    //% acrylamide for the gel
    private ArrayList<JLabel> rangeLabels;
    private ArrayList<JLabel> mwLabels;
    private WebGenerator web;               //generates the website
//    private GenerateHTMLButtonSwingVersion webButton;

    /** protein data ArrayLists **/
    private String lastFileLoaded = "";           //name of the last data file loaded
    private double minMW;
    private double maxMW;
    private double minPi;
    private double maxPi;
    private ArrayList<Protein> sequences;                        //sequence data
    private ArrayList sequenceTitles;                   //sequence titles
    private ArrayList molecularWeights;                 //molecular weights of proteins
    private ArrayList piValues;                         //pI values of proteins
    private ArrayList functions;                        //functions of proteins

    private boolean set2ndFile = false;
    private java.awt.List proteinList2;
    private ArrayList<Protein> sequences2;
    private ArrayList sequenceTitles2 = new ArrayList();
    private ArrayList functions2;
    private ArrayList molecularWeights2;
    private ArrayList piValues2;
    private FileFrame fileFrame2;
    private boolean sequencesReady;

    private JLabel header0;
    private JPanel leftPanel;
    private JPanel pHPanel;
    private JPanel mWPanel;

    /**
     * This method initializes all GUI components.
     */
    private Electro2D electro2D;

    public Electro2D() {

        proteinListFrame = new SingleProteinListFrame("Protein Lists", this);
        fileFrame = new FileFrame(1);                            //init frame
        fileFrame2 = new FileFrame(2);
        fileFrame.setResizable(false); //don't allow user to change size
        proteinList = new java.awt.List();
        proteinList2 = new java.awt.List();

        web = new WebGenerator(this);
        JButton webButton = new JButton("Generate HTML Page");
        webButton.setToolTipText("Creates an HTML file of proteins and values");
        webButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new HTMLGenScreen(Electro2D.this);
            }
        });

        //read in deactivated range Image
        rangeImage = new RangeImage(
                Toolkit.getDefaultToolkit().getImage(
                        "rangeSelectDeactivated.jpg"));

        rangeLabels = new ArrayList<JLabel>();
        mwLabels = new ArrayList();
        resetPressed = false;
        rangeReload = false;
        gelCanvas = new GelCanvas(this);
        JButton compareButton = new JButton("Compare Proteins");
        compareButton.setToolTipText("Compares multiple proteins to eacho ther");
        compareButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getSequenceData2();
                PlayE2AnimationButton.setCompare(true);
            }
        });

        csvButton = new JButton("Record to CSV");
        csvButton.setToolTipText("Creates a spreadsheet of proteins and their values");
        csvButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeToCSV(); //really confused as to why we don't put the logic right here
            }
        });

        // Help/About buttons
        JButton aboutButton = new JButton("About");
        aboutButton.setToolTipText("About the program");
        aboutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                File f = new File( "HTML Files" + File.separator + "Help" + File.separator + "help.html" );
                try {
                    BrowserLauncher.openURL("https://github.com/RITJBF/JBioFramework");
                } catch (IOException i) {
                    System.err.println(i.getMessage());
                }
            }
        });

        JButton helpButton = new JButton("Help");
        helpButton.setToolTipText("Opens help wiki for Electro2D.Electro2D");
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                File f = new File( "HTML Files" + File.separator + "Help" + File.separator + "help.html" );
                try {
                    BrowserLauncher.openURL("https://github.com/RITJBF/JBioFramework/wiki/2-Dimensional-Electrophoresis");
                } catch (IOException i) {
                    System.err.println(i.getMessage());
                }
            }
        });

        // Add/Remove/List/Search protein buttons
        // @TODO: Consider consolidating all protein actions into a singular dialog for protein "Management"
        JButton addProteinButton = new JButton("Add Proteins");
        addProteinButton.setToolTipText("Load proteins from file");
        addProteinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                getSequenceData();
            }
        });

        JButton removeProteinButton = new JButton("Remove Proteins");
        removeProteinButton.setToolTipText("Remove unwanted proteins from field by name");
        removeProteinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                removeHighlightedProteins();
            }
        });

        JButton searchButton = new JButton("Search Protein Field");
        searchButton.setToolTipText("Refine protein field with these search options");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                openProteinSearch();
            }
        });

        JButton displayProteinsButton = new JButton("Display Protein List");
        displayProteinsButton.setToolTipText("Display editable list of all the proteins in field");
        displayProteinsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                displayProteinList();
            }
        });

        JButton colorKey = new JButton("Color Key");
        colorKey.setToolTipText("Pop-up color key explaining dots in field");
        colorKey.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new ColorFrame().showKey();
            }
        });

        playButton = new PlayE2AnimationButton(this);
        restartButton = new RestartE2DAnimationButton(this);

        animationChooser = new JLabel("IEF");

        // Add the pH range chooser
        rangeChooser = new JComboBox();
        rangeChooser.addItem("3 - 10");
        rangeChooser.addItem("4- 7");
        rangeChooser.addItem("Enter A Range");
        rangeChooser.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (rangeChooser.getSelectedItem().equals("Enter A Range")) {
                    rangeChooser.setEditable(true);
                } else {
                    rangeChooser.setEditable(false);
                }
            }
        });

        // init %Acrylamide field and set initial value to 15
        percentAcrylamide = new JComboBox();
        percentAcrylamide.addItem("5");
        percentAcrylamide.addItem("7.5");
        percentAcrylamide.addItem("10");
        percentAcrylamide.addItem("15");
        percentAcrylamide.addItem("18");
        percentAcrylamide.addItem("4 - 15");
        percentAcrylamide.addItem("4 - 20");
        percentAcrylamide.addItem("8 - 16");
        percentAcrylamide.addItem("10 - 20");
        percentAcrylamide.setSelectedItem("15");

        sequences = new ArrayList();
        sequenceTitles = new ArrayList();
        molecularWeights = new ArrayList();
        piValues = new ArrayList();
        sequencesReady = false;

        proteinListFrame = new SingleProteinListFrame("Protein Lists", this);


       /*
        * new code for designing a Swing GUI; uses JPanels and layout managers
        * to arrange the buttons and labels to look similar to how the old awt
        * code did it
        */

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());

        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = (new Insets(0, 10, 0, 10));
        this.add(leftPanel, c);

        c.gridx = 1;
        c.gridy = 0;
        this.add(rightPanel, c);

        GridBagConstraints constraint = new GridBagConstraints();

        constraint.gridx = 1;
        constraint.gridy = 0;
        constraint.fill = GridBagConstraints.BOTH;
        constraint.ipady = 50;
        constraint.ipadx = 650;
        pHPanel = new JPanel();
        pHPanel.setLayout(null);
        rightPanel.add(pHPanel, constraint);

        constraint.gridx = 0;
        constraint.gridy = 1;
        constraint.ipady = 10;
        constraint.ipadx = 15;
        mWPanel = new JPanel();
        mWPanel.setLayout(null);
        rightPanel.add(mWPanel, constraint);

        constraint.gridx = 1;
        constraint.gridy = 1;
        constraint.ipady = 450;
        constraint.ipadx = 650;
        rightPanel.add(gelCanvas, constraint);

        //header panel: label
        JPanel header = new JPanel(new GridBagLayout());
        c.gridy = 0;
        header0 = new JLabel("2D Electrophoresis");
        header0.setFont(new Font("SansSerif", Font.BOLD, 18));
        header.add(header0, c);
        leftPanel.add(header);

        //first panel: help and about buttons
        JPanel firstPanel = new JPanel();
        c.gridy = 1;
        firstPanel.add(helpButton);
        firstPanel.add(aboutButton);
        leftPanel.add(firstPanel);

        //second panel: protein buttons
        JPanel secondPanel = new JPanel();
        secondPanel.add(addProteinButton);
        leftPanel.add(secondPanel);

        //third panel: animation information panel
        JPanel thirdPanel = new JPanel();
        thirdPanel.setLayout(new GridLayout(1, 1, 0, 0));
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        thirdPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray), "Current Animation", TitledBorder.CENTER, TitledBorder.TOP));
        innerPanel.add(animationChooser);
        thirdPanel.add(innerPanel);
        leftPanel.add(thirdPanel);

        //fourth panel: animation control buttons
        JPanel fourthPanel = new JPanel();
        fourthPanel.setLayout(new GridLayout(1, 2, 2, 2));
        fourthPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray), "Animation Buttons", TitledBorder.CENTER, TitledBorder.TOP));
        fourthPanel.add(playButton);
//        fourthPanel.add(stopButton   );
        fourthPanel.add(restartButton);
        leftPanel.add(fourthPanel);

        //fifth panel: ph selection
        JPanel fifthPanel = new JPanel();
        fifthPanel.setLayout(new GridLayout(1, 1, 0, 0));
        fifthPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray), "Choose pH", TitledBorder.CENTER, TitledBorder.TOP));
        fifthPanel.add(rangeChooser);
        leftPanel.add(fifthPanel);

        //sixth panel: acrylamide selection
        JPanel sixthPanel = new JPanel();
        sixthPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray), "Choose Acrylamide %", TitledBorder.CENTER, TitledBorder.TOP));
        sixthPanel.setLayout(new GridLayout(1, 1, 0, 0));
        sixthPanel.add(percentAcrylamide);
        leftPanel.add(sixthPanel);

        //seventh panel: additional options label (AO)
        JPanel seventhPanel = new JPanel();
        JLabel additionalOptions = new JLabel("Additional Options");
        additionalOptions.setFont(new Font("SansSerif", Font.BOLD, 16));
        seventhPanel.add(additionalOptions);
        leftPanel.add(seventhPanel);

        //eigth panel: AO 'display proteins' button
        JPanel eighthPanel = new JPanel();
        eighthPanel.add(displayProteinsButton);
        leftPanel.add(eighthPanel);

        //ninth panel: AO 'compare proteins' button [inactive]
        JPanel ninthPanel = new JPanel();
        ninthPanel.add(compareButton);
        //leftPanel.add(ninthPanel); Removed compare protein functionality until it is useful.

        //tenth panel: AO 'search' button
        JPanel tenthPanel = new JPanel();
        tenthPanel.add(searchButton);
        leftPanel.add(tenthPanel);

        //eleventh panel: AO 'generate html page' button
        JPanel eleventhPanel = new JPanel();
        eleventhPanel.add(webButton);
        leftPanel.add(eleventhPanel);

        //twelf panel: AO 'record to csv' button
        JPanel twelfPanel = new JPanel();
        twelfPanel.add(csvButton);
        leftPanel.add(twelfPanel);

        //thirteenth panel: AO 'color key'
        JPanel thirteenthPanel = new JPanel();
        thirteenthPanel.add(colorKey);
        leftPanel.add(thirteenthPanel);

    }

    /**
     * Accessor method for the leftPanel instance variable so that
     * Electro2D.GelCanvas can correctly set its getMinimumSize() method.
     *
     * @return the leftPanel variable that holds all of the buttons
     */
    public JPanel getButtonPanel() {
        return leftPanel;
    }

    /**
     * Any drawing on the applet panel itself is done here.
     */

    public void displayProteinList() {

        proteinListFrame.setVisible(true);
        proteinListFrame.validate();
    }

    /**
     * displays the incrementing pH values above the gel after the IEF
     * animation.
     *
     * [at]param loc - the location of the label
     * [at]param value - the value to be placed on the label
     */
    public ArrayList<Integer> showPH() {

        double minPH = getMinRange();
        double maxPH = getMaxRange();
        ArrayList<Integer> linePositions = new ArrayList<Integer>();

        double pHOffset = (maxPH - minPH) / 7;
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        pHOffset = Double.valueOf(twoDForm.format(pHOffset));

        int labelOffset = pHPanel.getWidth() / 7;
        for (int i = 0; i < 8; i++) {
            JLabel newLabel;
            if (i == 7) {
                newLabel = new JLabel(twoDForm.format(maxPH));
                newLabel.setBounds(pHPanel.getWidth() - 35, pHPanel.getHeight() - 15, 35, 10);
            } else {
                newLabel = new JLabel(twoDForm.format(minPH + i * pHOffset));
                newLabel.setBounds(1 + i * labelOffset, pHPanel.getHeight() - 15, 35, 10);
            }
            pHPanel.add(newLabel);
            rangeLabels.add(newLabel);
            if (i != 0) {
                linePositions.add((gelCanvas.getX() + (i * labelOffset) - 9));
            }
            newLabel.repaint();
        }
        return linePositions;
    }

    /**
     * This method removes the labels that show the pH values.  It is called
     * when the restart button is pressed to clear the canvas.
     */
    public void clearpH() {


        // remove each of the labels from the applet's image
        for (int i = 0; i < rangeLabels.size(); i++) {
            pHPanel.remove((JLabel) rangeLabels.get(i));
        }

        //remove all of the labels from the ArrayList
        rangeLabels.clear();
        //clear the molecular weight labels as well (since this method is
        // only called from the restart button)
        clearMW();
        pHPanel.removeAll();
        //repaint the applet to reflect the change
        this.repaint();
    }

    /**
     * This method removes the labels that show the molecular weight values.
     * It is called every time the dot animation is stopped in order to reflect
     * the change in position of the dots.
     */
    public void clearMW() {
        //remove all of the labels from the applet's image
        for (int i = 0; i < mwLabels.size(); i++) {
            this.remove((JLabel) mwLabels.get(i));
        }
        //remove all of the labels from the ArrayList
        mwLabels.clear();
        //repaint the applet to reflect the change
        mWPanel.removeAll();
        this.repaint();
    }

    /**
     * Creates and displays the labels that reflect the position of a
     * particular molecular weight along the gel.  It is called every time the
     * dot animation is stopped in order to reflect the change in position
     * of the dots.
     *
     * @param loc100 - the location of the 100,000 molecular weight
     * @param loc50 - the location of the 50,000 molecular weight
     * @param loc25 - the location of the 25,000 molecular weight
     * @param loc10 - the location of the 10,000 molecular weight
     */
    public void showMW(int loc100, int loc50, int loc25, int loc10, boolean reMake) {

        JLabel hundredK = new JLabel("100K");
        mwLabels.add(hundredK);
        mWPanel.add(hundredK);
        ((JLabel) mwLabels.get(mwLabels.size() - 1)).setBounds(mWPanel.getX() + 10, loc100, 40, 15);
        ((JLabel) mwLabels.get(mwLabels.size() - 1)).setForeground(Color.BLACK);

        JLabel fiftyK = new JLabel("50K");
        mwLabels.add(fiftyK);
        mWPanel.add(fiftyK);
        ((JLabel) mwLabels.get(mwLabels.size() - 1)).setBounds(mWPanel.getX() + 15, loc50, 30, 15);
        ((JLabel) mwLabels.get(mwLabels.size() - 1)).setForeground(Color.BLACK);

        JLabel twentyfiveK = new JLabel("25K");
        mwLabels.add(twentyfiveK);
        mWPanel.add(twentyfiveK);
        ((JLabel) mwLabels.get(mwLabels.size() - 1)).setBounds(mWPanel.getX() + 15, loc25, 30, 15);
        ((JLabel) mwLabels.get(mwLabels.size() - 1)).setForeground(Color.BLACK);

        JLabel tenK = new JLabel("10K");
        mwLabels.add(tenK);
        mWPanel.add(tenK);
        ((JLabel) mwLabels.get(mwLabels.size() - 1)).setBounds(mWPanel.getX() + 15, loc10, 30, 15);
        ((JLabel) mwLabels.get(mwLabels.size() - 1)).setForeground(Color.BLACK);

    }

    /**
     * Changes the choice selected on the animationChooser Choice box to
     * SDS-PAGE after the IEF animation is completed.
     */
    public void setSDS() {

        //chose the SDS-PAGE value in animationChooser
        animationChooser.setText("SDS-PAGE");
    }

    /**
     * Changes the choice selected on the animationChooser Choice box to
     * IEF after the reset button is pressed.
     */
    public void setIEF() {
        //choose the IEF value in animationChooser
        animationChooser.setText("IEF");
    }

    /**
     * Returns the top-level frame that contains the specified
     * component, or null if there is no top-level frame.
     */
    public Frame getFrame(Component comp) {
        Component theTop = null;
        Component parent = comp;

        while (parent != null) {
            theTop = parent;
            parent = parent.getParent();
        }

        return (theTop instanceof Frame ? (Frame) theTop : null);
    }

    /**
     * Brings up the TextField to enable a user to enter a range for
     * the IEF animation.
     */
    public void allowSelectRange() {
        if (!rangeReload) {

            // remove the image and add the two text fields to allow the user
            // to enter a range

            remove(rangeImage);
            update(graphics);
            rangeReload = true;
        }
    }

    /**
     * Disables the TextField that allows a range to be entered
     */
    public void disableSelectRange() {
        if (rangeReload) {

            // remove the two text fields and add the image to disable the
            // manual entry of a range

            add(rangeImage);
            update(graphics);
            rangeReload = false;
        }
    }

    /**
     *  Retrieves protein sequence information from a file.
     *  This is achieved by popping up a new window with options for
     *  selecting a file from the server, or copying and pasting a file
     *  into a textbox.
     *  Currently, supported file types are .fasta, .faa, .gbk, and .pdb.
     */
    public void getSequenceData() {
        // display the fileFrame
        fileFrame.toFront();
        fileFrame.setVisible(true);
        fileFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                calculateProteinProperties(fileFrame,1);
            }
        });
    }

    public void getSequenceData2() {
        fileFrame2.toFront();
        fileFrame2.setVisible(true);
        calculateProteinProperties(fileFrame2,2);
    }

    private void calculateProteinProperties(FileFrame f, int fileNum) {
        ArrayList piValues = new ArrayList();
        double maxMW = -2;
        double minMW = 16;
        double maxPi = -2;
        double minPi = 16;
        ArrayList sequenceTitles = new ArrayList();
        ArrayList molecularWeights = new ArrayList();
        ArrayList functions = new ArrayList();
        ArrayList<Protein> proteins = f.getProteins();
        if (proteins == null || proteins.size() <= 0) {
            return;
        }
        for (int i=0;i<proteins.size();i++) {
            if (proteins.get(i).getpI() < minPi) {
                minPi = proteins.get(i).getpI();
            }
            if (proteins.get(i).getpI() > maxPi) {
                maxPi = proteins.get(i).getpI();
            }
            if (proteins.get(i).getMolecularWeight() < minMW) {
                minMW = proteins.get(i).getMolecularWeight();
            }
            if (proteins.get(i).getMolecularWeight() > maxMW) {
                maxMW = proteins.get(i).getMolecularWeight();
            }
            piValues.add(proteins.get(i).getpI());
            sequenceTitles.add(proteins.get(i).getName()+(i+1));
            molecularWeights.add(proteins.get(i).getMolecularWeight());
            functions.add(proteins.get(i).getFunctions());
        }

        if (fileNum == 1) {
            setSequences(proteins);
            setFunctionValues(functions);
            setPiValues(piValues);
            setMolecularWeights(molecularWeights);
            setSequenceTitles(sequenceTitles);
        } else {
            setSequences2(proteins);
            setFunctionValues2(functions);
            setPiValues2(piValues);
            setMolecularWeights2(molecularWeights);
            setSequenceTitles2(sequenceTitles);
        }
        setMaxAndMinVals(maxMW,minMW,maxPi,minPi);
        setLastFileLoaded(f.getLastFileName());
    }

    /**
     * Brings up a SwissProt-TrEMBL search for the particular protein
     * id provided
     */
    public void showSwsSearchPage(String id) {

        BufferedReader buffer = null;
        String searchID = new String();

        // Read in the address of Swiss search from the Search Addresses csv file
        // in the HTML files folder. It is on the third line in the csv file.

        try {
            FileInputStream fileStream = new FileInputStream("HTML Files/Search Addresses.csv");
            InputStreamReader inputStream = new InputStreamReader(fileStream);
            buffer = new BufferedReader(inputStream);
        } catch (IOException ex) {
            System.out.println("File not found.");
        }

        if (buffer != null) {
            try {
                String line = buffer.readLine();
                line = buffer.readLine();
                line = buffer.readLine();
                String[] brokenLine = line.split(",");
                searchID = new String(brokenLine[4] + id);
                buffer.close();
            } catch (IOException ex) {
                System.out.println("Problem with reading buffer.");
            }
        }

        URL searchPage = null;

        // Now use the read-in address with the protein sequence as an
        // extension as the URL of a webpage.

        try {
            searchPage = new URL(searchID);
        } catch (MalformedURLException e) {
            System.err.println("The error was " + e);
        }

        if (searchPage != null) {
            try {
                BrowserLauncher.openURL(searchID);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Brings up a Blast search for the particular protein
     * sequence provided
     */
    public void showBlastSearchPage(String seq) {

        BufferedReader buffer = null;
        String searchID = new String();

        // Read in the address of Blast search from the Search Addresses csv file
        // in the HTML files folder. It is on the second line in the csv file.

        try {
            FileInputStream fileStream = new FileInputStream("HTML Files/Search Addresses.csv");
            InputStreamReader inputStream = new InputStreamReader(fileStream);
            buffer = new BufferedReader(inputStream);
        } catch (IOException ex) {
            System.out.println("File not found.");
        }

        if (buffer != null) {
            try {
                String line = buffer.readLine();
                line = buffer.readLine();
                String[] brokenLine = line.split(",");
                searchID = new String(brokenLine[4] + seq);
                buffer.close();
            } catch (IOException ex) {
                System.out.println("Problem with reading buffer.");
            }
        }

        URL searchPage = null;

        // Now use the read-in address with the protein sequence as an
        // extension as the URL of a webpage.

        try {
            searchPage = new URL(searchID);
        } catch (MalformedURLException e) {
            System.err.println("The error was " + e);
        }

        if (searchPage != null) {
            try {
                BrowserLauncher.openURL(searchID);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Brings up GenBank search for the particular protein
     * id provided
     */
    public void showSearchPage(String id) {

        BufferedReader buffer = null;
        String searchID = new String();

        // Read in the address of GenBank search from the Search Addresses csv file
        // in the HTML files folder. It is on the fifth line in the csv file.

        try {
            FileInputStream fileStream = new FileInputStream("HTML Files/Search Addresses.csv");
            InputStreamReader inputStream = new InputStreamReader(fileStream);
            buffer = new BufferedReader(inputStream);
        } catch (IOException ex) {
            System.out.println("File not found.");
        }

        if (buffer != null) {
            try {
                String line = buffer.readLine();
                line = buffer.readLine();
                line = buffer.readLine();
                line = buffer.readLine();
                line = buffer.readLine();
                String[] brokenLine = line.split(",");

                //get the name of the loaded file and figure out the extention
                String filename = getLastFileLoaded();
                String extention = filename.substring(filename.indexOf(".") + 1);

                // if the extention is .pdb, create the proper search string for the
                // URL by searching for the name of the pdb file.
                if (extention.equals("pdb")) {
                    id = filename.substring(0, filename.indexOf("."));
                    searchID = new String(brokenLine[4] + id);
                } else {
                    // otherwise, use the id passed to the method as the name for
                    // the search.
                    searchID = new String(brokenLine[5] + id);
                }
                buffer.close();
            } catch (IOException ex) {
                System.out.println("Problem with reading buffer.");
            }
        }

        URL searchPage = null;

        // Now use the read-in address with the protein sequence as an
        // extension as the URL of a webpage.

        try {
            searchPage = new URL(searchID);
        } catch (MalformedURLException e) {
            System.err.println("The error was " + e);
        }

        if (searchPage != null) {
            try {
                BrowserLauncher.openURL(searchID);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

	/*
    //create a URL object
	URL searchPage = null;
	String searchId = "";  //the name used in the search

	//get the name of the loaded file and figure out the extention
	String filename = getLastFileLoaded();
	String extention = filename.substring( filename.indexOf( "." ) + 1 );

	// if the extention is .pdb, create the proper search string for the
	// URL by searching for the name of the pdb file.
	if( extention.equals( "pdb" ) ){
	    id = filename.substring( 0, filename.indexOf( "." ) );
	    searchId = "http\u003A\u002F\u002Fwww.ncbi.nlm.nih.gov" +
	   "\u002Fentrez\u002Fquery.fcgi?db=structure&" + "cmd=search&term=" +
		id;
	}
	else{

	    // otherwise, use the id passed to the method as the name for
	    // the search.
	    searchId = "http\u003A\u002F\u002Fwww.ncbi.nlm.nih.gov" +
	     "\u002Fentrez\u002Fquery.fcgi?db=protein&" + "cmd=search&term=" +
	     id;
	    }

	try{
	    //assign the search information to the URL
	    searchPage = new URL( searchId );
	}catch( MalformedURLException e ){
	    //catch and display any errors that occurred when assigning the
	    //information to the URL
	    System.err.println( "Bad URL: " + searchPage );
	}

	//if no errors occurred, open a new window with the search results
	if( searchPage != null ){
	    try{
	    BrowserLauncher.openURL( searchId );
	    }catch(IOException e ){System.err.println( e.getMessage() );}
	}
    **/
    }

    /**
     * Brings up Enzyme Commission search for the particular protein
     * id provided
     */
    public void showECSearchPage(String id) {

        int index = 0;
        //create a URL object
        //URL searchPage = null;
        String searchId = "";           //the name used in the search
        ArrayList ecNums = new ArrayList(); //holds the EC numbers contained in the
        // id string

        while (id.length() > 0 && id.indexOf("\u003B") != -1) {
            ecNums.add(id.substring(0, id.indexOf("\u003B")));
            index = id.indexOf("\u003B");
            if (index + 1 == id.length()) {
                id = "";
            } else {
                id = id.substring(id.indexOf("\u003B") + 1);
                id = id.trim();
            }
        }

        searchId = "http\u003A\u002F\u002Fwww.genome.ad.jp\u002Fdbget-bin" +
                "\u002Fwww_bget?enzyme+";
        for (int d = 0; d < ecNums.size(); d++) {
            ecNums.add(d, (searchId +
                    (String) ecNums.get(d)));
            ecNums.remove(d + 1);
        }

        try {
            //assign the search information to the URL
            for (int d = 0; d < ecNums.size(); d++) {
                ecNums.add(d, (new URL((String) ecNums.get(d))));
                ecNums.remove(d + 1);
            }
        } catch (MalformedURLException e) {
            //catch and display any errors that occurred when assigning the
            //information to the URL
            System.err.println("Bad URL: " + searchId);
        } catch (Exception f) {
            System.err.println("The error was " + f.getMessage());
        }

        //if no errors occurred, open a new window with the search results
        if (ecNums.size() != 0) {
            try {
                for (int d = 0; d < ecNums.size(); d++) {
                    BrowserLauncher.openURL(((URL) ecNums.get(d)).toString());
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     *  Cycles through the list and removes any highlighted proteins.
     */
    public void removeHighlightedProteins() {
        selectedIndexes = proteinList.getSelectedIndexes();
        String[] selectedItems = proteinList.getSelectedItems();
        for (int x = 0; x < selectedIndexes.length; x++) {
            removeProteinbyTitle(selectedItems[x]);
            proteinList.remove(selectedIndexes[x] - x);

        }
        if (sequenceTitles2 != null) {
            int[] selectedIndexes2 = proteinList2.getSelectedIndexes();
            selectedItems = proteinList2.getSelectedItems();
            for (int x = 0; x < selectedIndexes2.length; x++) {
                removeProteinbyTitle2(selectedItems[x]);
                proteinList2.remove(selectedIndexes2[x] - x);
            }
        }
    }

    /**
     * Opens the protein search dialog box
     */
    public void openProteinSearch() {
        SearchProteinFunction proteinSearch = new SearchProteinFunction(this);
    }

    /**
     * returns the voltage selected by the user
     *
     * @return a string
     */
    public String getVoltage() {
        return "50 V";
    }

    /**
     * returns the maximum pH range that the user selected
     *
     * @return max the maximum pH to be used in the simulation
     */
    public double getMaxRange() {
        // set up the default maximum range of 10
        Double max = new Double(10);
        String ranges = (String) rangeChooser.getSelectedItem();
        // if the user selected a range from the list, supply the correct max
        if (ranges.equals("3 - 10")) {
            max = 10.0;
            return (double) max;
        } else if (ranges.equals("4 - 7")) {
            max = 7.0;
            return (double) max;
        }
        /**
         * if the user chose to enter their own value, first check to make sure
         * it is in the correct format
         */
        else if (ranges.matches("\\d+.?\\d*-\\d+.?\\d*")) {
            /**
             * split the user's range into a String array so that Scanner can
             * find the correct Double
             */
            String[] lowAndHigh = ranges.split("-");
            Scanner scan = new Scanner(lowAndHigh[1]);
            max = scan.nextDouble();
            /**
             * the maximum pH cannot be below 0 or above 14 because a pH of any
             * other value is a physical impossibility
             */
            if ((max < 0) || (max > 14)) {

                MessageFrame mess = new MessageFrame();
                String m = max + " is not a valid number.  Please " +
                        "press restart and try again using values between 0" +
                        "and 14.";
                mess.setMessage(m);
                mess.setVisible(true);

            } else {
                // return the user's maximum pH
                return (double) max;

            }

        }
        /**
         * if none of the provided choices were selected and the user did not
         * enter a value, then use the default max of 10
         */
        return (double) max;
    }

    /**
     * this method returns the minimum pH range that the user selected
     *
     * @return min the minimum pH to be used in the simulation
     */

    public double getMinRange() {
        // set up the default minimum pH of 3
        Double min = new Double(3);
        String ranges = (String) rangeChooser.getSelectedItem();
        /**
         * if the user selected one of the provided choices set the minimum pH
         * to the appropriate value
         */
        if (ranges.equals("3 - 10")) {
            min = 3.0;
            return (double) min;
        } else if (ranges.equals("4 - 7")) {
            min = 4.0;
            return (double) min;
        }
        /**
         * if the user chose to enter their own value, check to make sure that
         * it is in the correct format
         */
        else if (ranges.matches("\\d+.?\\d*-\\d+.?\\d*")) {
            // make the scanner object to find the correct Double
            String[] lowAndHigh = ranges.split("-");
            Scanner scan = new Scanner(lowAndHigh[0]);
            min = scan.nextDouble();
            /**
             * the minimum pH cannot be below 0 or above 14 because a pH of any
             * other value is a physical impossibility
             */
            if ((min < 0) || (min > 14)) {

                MessageFrame mess = new MessageFrame();
                String m = min + " is not a valid number.  Please " +
                        "press restart and try again using values between 0" +
                        "and 14.";
                mess.setMessage(m);
                mess.setVisible(true);

            } else {
                // return the user's minimum pH value
                return (double) min;

            }

        }
        /**
         * if the user did not select any of the provided choices and did not
         * enter their own pH value, use the default pH value of 3
         */
        return (double) min;

    }

    /**
     * Returns the value stored in the % acrylamide text field
     *
     * @return percent
     */
    public double getLowPercent() {
        // get the value in the text box
        String value = (String) percentAcrylamide.getSelectedItem();
        value = value.trim();
        //return value;
        double percent = -1;

        if (value.indexOf("-") != -1) {
            value = value.substring(0, value.indexOf("-"));
            value = value.trim();
        }
        // try to change the value into a number
        try {
            percent = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            //if the value was not a valid number, display the error
            // message in a new frame
            MessageFrame mess = new MessageFrame();
            String m = value + " is not a valid number.  Please " +
                    "press restart and try again.";
            mess.setMessage(m);
            mess.setVisible(true);

        }

        return percent;
    }

    /**
     * Returns the higher of the values stored in the % acrylamide text field
     *
     * @return percent
     */
    public double getHighPercent() {
        // get the value in the text box
        String value = (String) percentAcrylamide.getSelectedItem();
        value = value.trim();
        //return value;
        double percent = -1;

        if (value.indexOf("-") != -1) {
            value = value.substring(value.indexOf("-") + 1);
            value = value.trim();
        }
        // try to change the value into a number
        try {
            percent = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            //if the value was not a valid number, display the error
            // message in a new frame
            MessageFrame mess = new MessageFrame();
            String m = value + " is not a valid number.  Please " +
                    "press restart and try again.";
            mess.setMessage(m);
            mess.setVisible(true);

        }

        return percent;
    }


    /**
     * returns the animation selected by the user
     *
     * @return a string
     */
    public String getAnimationChoice() {
        return (String) animationChooser.getText();
    }

    /**
     * resets the play button's image to the play image
     */
    public void resetPlay() {
        playButton.resetPlay();
    }

    /**
     *  Refreshes the protein list, called after reading in new proteins
     *  from a file. Currently, the list is cleared and re-filled with
     *  proteins from the latest file read in.
     */
    public void refreshProteinList() {
        // removes everything from the list of proteins
        proteinList.removeAll();
        // refreshes the list with the new protein titles
        for (int x = 0; x < sequenceTitles.size(); x++) {
            proteinList.add((String) sequenceTitles.get(x));
        }
        proteinListFrame.updateSequences(sequenceTitles, sequenceTitles2);
    }

    /**
     * Given a protein title from proteinList, removes the protein's
     * information from the ArrayLists.
     *
     * @param title the title to be removed
     */
    public boolean removeProteinbyTitle(String title) {
        for (int x = 0; x < sequenceTitles.size(); x++) {
            if ((sequenceTitles.get(x)).toString().equals(title)) {
                molecularWeights.remove(x);
                piValues.remove(x);
                sequenceTitles.remove(x);
                return true;
            }
        }

        return false;
    }

    /**
     * Given a protein title from proteinList2, removes the protein's
     * information from the ArrayLists.
     *
     * @param title the title to be removed
     */
    public boolean removeProteinbyTitle2(String title) {
        if (sequenceTitles2 != null) {
            for (int x = 0; x < sequenceTitles2.size(); x++) {
                if (((String) sequenceTitles2.get(x)).equals(title)) {
                    molecularWeights2.remove(x);
                    piValues2.remove(x);
                    sequenceTitles2.remove(x);
                    return true;
                }
            }
        }
        return false;
    }

    /***/
    public void refreshProteinList2() {
        proteinList2.removeAll();
        for (int x = 0; x < sequenceTitles2.size(); x++) {
            proteinList2.add((String) sequenceTitles2.get(x));
        }
        proteinListFrame.updateSequences(sequenceTitles, sequenceTitles2);
    }

    /**
     *  Responds to a double-click in the protein list by bringing up a
     *  new window with information on that protein.
     */
    public void actionPerformed(ActionEvent e) {
        //set up a new frame, give it ref. to the applet and the protein name
        ProteinFrame proteinFrame = new ProteinFrame(this,
                e.getActionCommand(), 1);
        //proteinFrame.setResizable(false);
        proteinFrame.setVisible(true);

        if (playButton.getSdsStatus()) {
            gelCanvas.drawLocation(e.getActionCommand());
        }
    }

    /**
     * Sets the SDS-PAGE status in playButton to false to let the
     * actionListener know that the SDS-PAGE portion of the animation
     * has not been drawn
     */
    public void resetSdsStatus() {
        playButton.resetSdsStatus();
    }

    /**
     * Reinitializes the dotThread
     */
    public void restartThread() {
        //initializes the thread, sets a value, and calls the start method
        dotThread = new DotThread(this);
        dotThread.startDots();
        dotThread.start();
    }

    /**
     * this method is called by the reset button once it is pressed.
     * it lets the applet know it should reload the protein information
     */
    public void setBool() {
        resetPressed = true;
    }

    /**
     * This method lets the applet know that the information has already been
     * loaded and the reset button has not been pressed.
     */
    public void resetBool() {
        resetPressed = false;
    }

    /**
     * This method returns the current state of the reset button.  If it is
     * true, the button has been pressed and false if it has not been pressed
     *
     * @return resetPressed - state of reset button
     */
    public boolean getBool() {
        return resetPressed;
    }

    /**
     * This method reinitializes the Electro2D.IEFThread in charge of the IEF animation.
     */
    public void restartIEF() {
        //initializes the thread, sets a value and calls the start method
        iefThread = new IEFThread(gelCanvas, this);
        iefThread.setIEF();
        iefThread.start();
    }

    /**
     * This method returns an instance of the Electro2D.IEFThread
     *
     * @return iefThread
     */
    public IEFThread getIEFThread() {
        return iefThread;
    }

    /**
     * This method resets a value for the IEF animation in the PlayButton class
     */
    public void resetIEF() {
        playButton.resetIEF();
    }


    /**
     * Stops the dotThread
     */
    public void stopThread() {
        dotThread.stopDots();
    }

    /**
     * Returns the last file loaded
     *
     * @return lastFileLoaded
     */
    public String getLastFileLoaded() {
        return lastFileLoaded;
    }

    /**
     * Returns the gelcanvas object
     *
     * @return gelCanvas
     */
    public GelCanvas getGel() {
        return gelCanvas;
    }

    /**
     * Returns the sequences of amino acids
     *
     * @return sequences
     */
    public ArrayList getSequences() {
        ArrayList<Integer> positionsOne = proteinListFrame.getPositionsOne();
        if (positionsOne.size() > 0) {
            ArrayList<Protein> copySequences = (ArrayList<Protein>) sequences.clone();
            sequences.clear();
            if (positionsOne.get(0) > -1) {
                for (int x = 0; x < positionsOne.size(); x++) {
                    sequences.add(copySequences.get(positionsOne.get(x)));
                }
            }
        }
        return sequences;
    }

    /**
     * Returns the sequence titles
     *
     * @return sequenceTitles
     */
    public ArrayList getSequenceTitles() {
        ArrayList<Integer> positionsOne = proteinListFrame.getPositionsOne();
        if (positionsOne.size() > 0) {
            ArrayList copySequenceTitles = (ArrayList) sequenceTitles.clone();
            sequenceTitles.clear();
            if (positionsOne.get(0) > -1) {
                for (int x = 0; x < positionsOne.size(); x++) {
                    Integer temp = positionsOne.get(x);
                    Object next = copySequenceTitles.get(temp);
                    sequenceTitles.add(next);
                    //sequenceTitles.add(copySequenceTitles.get(positionsOne.get(x)));
                }
            }
        }
        return sequenceTitles;
    }

    /**
     * Returns the Electro2D.DotThread
     *
     * @return dotThread
     */
    public DotThread getThread() {
        return dotThread;
    }

    /**
     * Returns the molecular weights
     *
     * @return molecularWeights
     */
    public ArrayList getMolecularWeights() {
        ArrayList<Integer> positionsOne = proteinListFrame.getPositionsOne();
        if (positionsOne.size() > 0) {
            ArrayList copyMolecularWeights = (ArrayList) molecularWeights.clone();
            molecularWeights.clear();
            if (positionsOne.get(0) > -1) {
                for (int x = 0; x < positionsOne.size(); x++) {
                    molecularWeights.add(copyMolecularWeights.get(positionsOne.get(x)));
                }
            }
        }
        return molecularWeights;
    }

    /**
     * returns the molecular weight of a protein given its name
     *
     * @return a string
     */
    public String getMWbyTitle(String title) {
        for (int x = 0; x < sequenceTitles.size(); x++) {
            if ((sequenceTitles.get(x)).toString().equals(title)) {
                return molecularWeights.get(x).toString();
            }
        }

        if (sequenceTitles2 != null) {
            for (int x = 0; x < sequenceTitles2.size(); x++) {
                if ((sequenceTitles2.get(x)).toString().equals(title)) {
                    return molecularWeights2.get(x).toString();
                }
            }
        }
        return "";
    }

    /**
     * returns the function of a protein given its name
     *
     * @return a string
     */
    public String getFunctionbyTitle(String title) {
        for (int x = 0; x < sequenceTitles.size(); x++) {
            if (((String) sequenceTitles.get(x)).equals(title)) {
                return (String) functions.get(x);
            }
        }

        if (sequenceTitles2 != null) {
            for (int x = 0; x < sequenceTitles2.size(); x++) {
                if (((String) sequenceTitles2.get(x)).equals(title)) {
                    return (String) functions2.get(x);
                }
            }
        }
        return "";
    }

    /**
     * returns the pI value of a protein given its name
     *
     * @return a string
     */
    public String getPIbyTitle(String title) {
        for (int x = 0; x < sequenceTitles.size(); x++) {
            if ((sequenceTitles.get(x)).equals(title)) {
                return piValues.get(x).toString();
            }
        }
        if (sequenceTitles2 != null) {
            for (int x = 0; x < sequenceTitles2.size(); x++) {
                if ((sequenceTitles2.get(x)).equals(title)) {
                    return piValues2.get(x).toString();
                }
            }
        }
        return "";
    }

    /**
     * returns the sequence value of a protein given its name
     *
     * @return a string
     */
    public String getSequencebyTitle(String title) {
        for (int x = 0; x < sequenceTitles.size(); x++) {
            if (((String) sequenceTitles.get(x)).equals(title)) {
                return sequences.get(x).getProteinSequenceAsString();
            }
        }
        if (sequenceTitles2 != null) {
            for (int x = 0; x < sequenceTitles2.size(); x++) {
                if (((String) sequenceTitles2.get(x)).equals(title)) {
                    return sequences2.get(x).getProteinSequenceAsString();
                }
            }
        }
        return "";
    }

    /**
     * sets the values for the max and min pI and MW
     *
     * @param minmw the min MW val
     * @param maxmw the max MW val
     * @param minpi the min pI val
     * @param maxpi the max pI val
     */
    public void setMaxAndMinVals(double maxmw, double minmw, double maxpi,
                                 double minpi) {
        maxMW = maxmw;
        minMW = minmw;
        maxPi = maxpi;
        minPi = minpi;
    }

    public double getMaxPi() {
        return maxPi;
    }

    public double getMinPi() {
        return minPi;
    }

    public double getMaxMW() {
        return maxMW;
    }

    public double getMinMW() {
        return minMW;
    }

    /**
     * returns the pI values
     *
     * @return piValues
     */
    public ArrayList getPiValues() {
        ArrayList<Integer> positionsOne = proteinListFrame.getPositionsOne();
        if (positionsOne.size() > 0) {
            ArrayList copyPiValues = (ArrayList) piValues.clone();
            piValues.clear();
            if (positionsOne.get(0) > -1) {
                for (int x = 0; x < positionsOne.size(); x++) {
                    piValues.add(copyPiValues.get(positionsOne.get(x)));
                }
            }
        }
        return piValues;
    }

    /**
     * returns the protein functions
     *
     * @return functions
     */
    public ArrayList getFunctions() {
        ArrayList<Integer> positionsOne = proteinListFrame.getPositionsOne();
        if (positionsOne.size() > 0) {
            ArrayList copyFunctions = (ArrayList) functions.clone();
            functions.clear();
            if (positionsOne.get(0) > -1) {
                for (int x = 0; x < positionsOne.size(); x++) {
                    functions.add(copyFunctions.get(positionsOne.get(x)));
                }
            }
        }
        return functions;
    }

    public ArrayList getPiValues2() {
        ArrayList<Integer> positionsTwo = proteinListFrame.getPositionsTwo();
        if (positionsTwo.size() > 0) {
            ArrayList copyPiValues2 = (ArrayList) piValues2.clone();
            piValues2.clear();
            if (positionsTwo.get(0) > -1) {
                for (int x = 0; x < positionsTwo.size(); x++) {
                    piValues2.add(copyPiValues2.get(positionsTwo.get(x)));
                }
            }
        }
        return piValues2;
    }

    public ArrayList getSequences2() {
        ArrayList<Integer> positionsTwo = proteinListFrame.getPositionsTwo();
        if (positionsTwo.size() > 0) {
            ArrayList<Protein> copySequences2 = (ArrayList<Protein>) sequences2.clone();
            sequences2.clear();
            if (positionsTwo.get(0) > -1) {
                for (int x = 0; x < positionsTwo.size(); x++) {
                    sequences2.add(copySequences2.get(positionsTwo.get(x)));
                }
            }
        }
        return sequences2;
    }

    public ArrayList getSequenceTitles2() {
        if (sequenceTitles2 != null) {
            ArrayList<Integer> positionsTwo = proteinListFrame.getPositionsTwo();
            if (positionsTwo.size() > 0) {
                ArrayList copySequenceTitles2 = (ArrayList) sequenceTitles2.clone();
                sequenceTitles2.clear();
                if (positionsTwo.get(0) > -1) {
                    for (int x = 0; x < positionsTwo.size(); x++) {
                        sequenceTitles2.add(copySequenceTitles2.get(positionsTwo.get(x)));
                    }
                }
            }
            return sequenceTitles2;
        }
        return new ArrayList();
    }

    public ArrayList getMolecularWeights2() {
        ArrayList<Integer> positionsTwo = proteinListFrame.getPositionsTwo();
        if (positionsTwo.size() > 0) {
            ArrayList copyMolecularWeights2 = (ArrayList) molecularWeights2.clone();
            molecularWeights2.clear();
            if (positionsTwo.get(0) > -1) {
                for (int x = 0; x < positionsTwo.size(); x++) {
                    molecularWeights2.add(copyMolecularWeights2.get(positionsTwo.get(x)));
                }
            }
        }
        return molecularWeights2;
    }

    public ArrayList getFunctions2() {
        ArrayList<Integer> positionsTwo = proteinListFrame.getPositionsTwo();
        if (positionsTwo.size() > 0) {
            ArrayList copyFunctions2 = (ArrayList) functions2.clone();
            functions2.clear();
            if (positionsTwo.get(0) > -1) {
                for (int x = 0; x < positionsTwo.size(); x++) {
                    functions2.add(copyFunctions2.get(positionsTwo.get(x)));
                }
            }
        }
        return functions2;
    }

    /**
     * stores the name of the last file loaded
     */
    public void setLastFileLoaded(String l) {
        lastFileLoaded = l;
    }

    /**
     * stores the ArrayList of sequence data
     */
    public void setSequences(ArrayList s) {
        sequences = s;
        sequencesReady = true;
    }

    /**
     * This method is used by Electro2D.ProteinListFrame to let Electro2D.Electro2D and
     * Electro2D.GelCanvas know whether or not there are sequences available for
     * animiation.
     *
     * @param bool
     */
    public void setSequencesReady(boolean bool) {
        sequencesReady = bool;
    }

    /**
     * This method is used by Electro2D.PlayE2AnimationButton to determine when the user
     * clicks play if there are sequences ready to animate.
     *
     * @return sequencesReady
     */
    public boolean getSequencesReady() {
        return sequencesReady;
    }

    /**
     * sets the ArrayList of sequence titles
     */
    public void setSequenceTitles(ArrayList st) {
        sequenceTitles = st;
    }

    /**
     * sets the ArrayList of molecular weights
     */
    public void setMolecularWeights(ArrayList mw) {
        molecularWeights = mw;
    }

    /**
     * sets the ArrayList of protein functions
     */
    public void setFunctionValues(ArrayList fcn) {
        functions = fcn;
    }

    /**
     * sets the ArrayList of pI values
     */
    public void setPiValues(ArrayList pi) {
        piValues = pi;
    }

    public void setSequences2(ArrayList s) {
        sequences2 = new ArrayList();
        sequences2 = s;
        sequencesReady = true;
    }

    public void setSequenceTitles2(ArrayList st) {
        sequenceTitles2 = new ArrayList();
        sequenceTitles2 = st;
    }

    public void setMolecularWeights2(ArrayList mw) {
        molecularWeights2 = new ArrayList();
        molecularWeights2 = mw;
    }

    public void setPiValues2(ArrayList pi) {
        piValues2 = new ArrayList();
        piValues2 = pi;
    }

    public void setFunctionValues2(ArrayList fcn) {
        functions2 = new ArrayList();
        functions2 = fcn;
    }

    public void writeToCSV() {
        CSVCreator csv = new CSVCreator(this);
        csv.writeToCSV();
    }

    public void generateWebPage() {
        web.genFile(this.getLastFileLoaded());
        JOptionPane.showMessageDialog(null, "Webpage created in 'HTML Files/' subdirectory");
    }

    public void resetBothProteinLists() {
        proteinList = new java.awt.List();
        proteinList2 = new java.awt.List();
        sequencesReady = false;
    }
}

