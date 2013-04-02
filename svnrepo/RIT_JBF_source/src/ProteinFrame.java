/**
 * ProteinFrame.java
 *
 * This class encapsulates all the functionality required to pop up a frame
 * and display information about a particular protein.
 *
 * @author Jill Zapoticznyj
 * @author Adam Bazinet
 *
 *
 * 10/17/2011: Added functionality to let a protein's sequence be given to the
 * tandem mass spec simulation. @author Amanda Fisher
 */

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private JButton search;   // button clicked to search for info
    private JButton swsSearch;//button clicked to search for info
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
     * Constructor - creates the ProteinFrame object as well as initializes
     * all of the data members
     *
     * @param e a reference to the Electro2D class
     * @param pt the title of the protein whose information is displayed
     */
    public ProteinFrame(Electro2D e, String pt, int filenum) {

	//give the frame a reference to Electro2D
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
	proteinFunction = electro2D.getFunctionbyTitle( proteinTitle );
	proteinFunction = proteinFunction.trim();
	
	// Create the text to be put on the search buttons by reading the csv
        // file "SearchAddresses.csv".
        String oneText = "";
        String twoText = "";
        String threeText = "";
        BufferedReader buffer = null;
        try {
            FileInputStream fileStream = new FileInputStream("HTML Files/Search Addresses.csv");
            InputStreamReader inputStream = new InputStreamReader(fileStream);
            buffer = new BufferedReader(inputStream);
        } catch (IOException ex) {
            System.out.println("File not found.");
        }

        if(buffer != null) {
            try {
                String line = buffer.readLine();
                line = buffer.readLine();
                String[] brokenLine = line.split(",");
                oneText = brokenLine[2];
                line = buffer.readLine();
                brokenLine = line.split(",");
                twoText = brokenLine[2];
                line = buffer.readLine();
                line = buffer.readLine();
                brokenLine = line.split(",");
                threeText = brokenLine[2];
                buffer.close();
            } catch (IOException ex) {
                System.out.println("Problem with reading buffer.");
            }
        }

	sequenceString = electro2D.getSequencebyTitle( proteinTitle );


    // Performs a BLAST search for the protein sequence given to it from
    // ProteinFrame.
    JButton blstSearch = new JButton("Blast Search"); //button clicked to search Blast site
    blstSearch.setToolTipText("Performs BLAST search for the protein sequence.");
    blstSearch.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            electro2D.showBlastSearchPage(sequenceString);
        }
    });


	String fileName = electro2D.getLastFileLoaded();

	//depending on the database file used, extract the name of the protein
	// and create a button to allow a GenBank search for that protein
	int index = proteinTitle.indexOf( "|" );
	if( index != -1 ){
	    //if the protein is from a fasta file, the title is an abbreviated
	    //form of the proteinTitle string
	    searchID = proteinTitle.substring( index + 1 );
	    index = searchID.indexOf( "|" );
      	    while( index != -1  && index != searchID.lastIndexOf("|") ){
		    searchID = searchID.substring( index + 1,
					   searchID.lastIndexOf( "|" ) );
			index = searchID.indexOf( "|" );
		}
		if( index != -1 ){
			searchID = searchID.substring( index + 1 );
		}
	    search = new JButton("Search Protein Field"); //SearchProteinButton( electro2D, searchID, twoText);
	    search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                electro2D.showSearchPage(searchID);

	}
        });
    }
	else if( index == -1 ){
	    //if the protein is not from a fasta file...

	    index = proteinTitle.indexOf( "\u003B" );
	    if( index != -1 ){
		//...but from a protein databank file, the id is the protein
		// title with the semicolon truncated
		searchID = proteinTitle.substring( 0, index );
		search = new SearchProteinButton( electro2D, searchID, twoText);
	    }
	    else {
		//...or from a protein databank file, the id is the protein 
		// title.
		searchID = proteinTitle;
		search = new SearchProteinButton( electro2D, searchID, twoText);
	    }
	}

	//using the file type to determine the string to assign it, 
	// create a button to allow a SwissProt/TrEMBL search for the protein
	
	if( proteinTitle.indexOf( "|" ) < 0 ){
	    swsSearchID = proteinTitle;
	    swsSearch = new JButton(threeText); //( electro2D, swsSearchID, threeText);
        swsSearch.setToolTipText("Search for more information on the protein.");
        swsSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                electro2D.showSwsSearchPage(swsSearchID);
	}
        });
	}
	else {
	    swsSearchID = proteinTitle.substring( 4,
				    proteinTitle.lastIndexOf( "|" ));
	    swsSearchID = swsSearchID.substring(
				    swsSearchID.lastIndexOf( "|" ) + 1 );
	    swsSearch = new JButton(threeText); //electro2D, swsSearchID, threeText);
        swsSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                electro2D.showSwsSearchPage(swsSearchID);
	}
        });
	}


	//create labels to display the protein information
	titleLabel = new JLabel(ptTruncated);
	mwLabel = new JLabel("Molecular Weight (MW): " +
			    electro2D.getMWbyTitle(proteinTitle));
	piLabel = new JLabel("pI Value (pI): " +
			    electro2D.getPIbyTitle(proteinTitle));

	functionLabel = new JLabel( "Function: " + proteinFunction);

        add(proteinInfoPanel);
        proteinInfoPanel.add(titleLabel);
        proteinInfoPanel.add(mwLabel);
        proteinInfoPanel.add(piLabel);
        proteinInfoPanel.add(functionLabel);
        searchPanel.add(blstSearch);
        searchPanel.add(search);
        searchPanel.add(swsSearch);
        searchPanel.add(new sendToSpec());
        this.setLayout(new BorderLayout());
        this.add(proteinInfoPanel, BorderLayout.NORTH);
        this.add(searchPanel, BorderLayout.CENTER);

        pack();
        //setSize(420, 150);
    }

    private class sendToSpec extends JButton implements ActionListener {
        public sendToSpec() {
            super("Run Mass Spectrum");
            addActionListener(this);
        }

        /**
         * The actionPerformed method is called when the user clicks on the button.
         * It begins the simulation.
         *
         * @param e Unused.
         */
        public void actionPerformed(ActionEvent e) {
            JTextArea input = MassSpecMain.getInputArea();
            input.setText(sequenceString);
            JOptionPane.showMessageDialog(null, "Protein sequence sent to mass spectrometer simulation. Click mass spectrometer tab.");
        }
    }

}