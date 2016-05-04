package Ionex;

//******************************************************************************
// Ionex.java:	Applet
//
//******************************************************************************

import java.applet.Applet;
import java.awt.*;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Vector;

//==============================================================================
// Main Class for applet Ionex
//
//==============================================================================
public class Ionex extends Applet //implements Runnable
{
    // THREAD SUPPORT:
    //		m_ionex	is the Thread object for the applet
    //--------------------------------------------------------------------------
//	private Thread	m_ionex = null;
    Panel m_controlPanel;
    IDD_DIALOG1 controls;
    ImageCanvas m_imageCanvas;

    // variables to hold the experiment definition
    double m_dConc1 = 0.0;
    double m_dConc2 = 0.0;
    int m_nBuffer = 0;
    boolean m_bPositiveResin = true;
    CProtein[] m_arrProteins;

    Vector m_arrSelProteins;    //the currently selected proteins

    //	CProtein[]	m_arrSelProteins;	//the currently selected proteins
//	Vector		m_vAvailProteins;
    int[] m_arrProteinIndex = {-1, -1, -1, -1, -1};
    char m_cState = 'S';   // the process state ([R]UN, [P]AUSE or [S]TOP)
    int m_nCharge = 0;
    int m_nSolSpeed = 0;
    Color[] m_colors = {Color.red, Color.green, Color.blue, Color.cyan, Color.magenta, Color.gray};

    final int COLLOY = 25;
    final int COLHIY = 226;


    ProteinFile[] m_arrAvailProteins;


    // Ionex Class Constructor
    //--------------------------------------------------------------------------
    public Ionex() {
        m_arrProteins = new CProtein[5];
//		m_arrSelProteins = new CProtein[5];
        m_arrSelProteins = new Vector(5);
//		m_vAvailProteins = new Vector( 10, 10);

        m_arrAvailProteins = new ProteinFile[100];
    }

    // APPLET INFO SUPPORT:
    //		The getAppletInfo() method returns a string describing the applet's
    // author, copyright date, or miscellaneous information.
    //--------------------------------------------------------------------------
    public String getAppletInfo() {
        return "Name: Ionex\r\n" +
                "Author: Kristen Cotton\r\n" +
                "Created with Microsoft Visual J++ Version 1.1";
    }


    // The init() method is called by the AWT when an applet is first loaded or
    // reloaded.  Override this method to perform whatever initialization your
    // applet needs, such as initializing data structures, loading images or
    // fonts, creating frame windows, setting the layout manager, or adding UI
    // components.
    //--------------------------------------------------------------------------
    public void init() {
        // If you use a ResourceWizard-generated "control creator" class to
        // arrange controls in your applet, you may want to call its
        // CreateControls() method from within this method. Remove the following
        // call to resize() before adding the call to CreateControls();
        // CreateControls() does its own resizing.
        //----------------------------------------------------------------------
        Image imageBkgd;
        setLayout(new BorderLayout());

        //get the image, wait until it's loaded
        imageBkgd = getImage(getCodeBase(), "macro.gif");
        MediaTracker tracker = new MediaTracker(this);
        tracker.addImage(imageBkgd, 0);
        try {
            tracker.waitForID(0);
        } catch (InterruptedException e) {
        }

        //Initialize the canvas
        m_imageCanvas = new ImageCanvas(this);
        m_imageCanvas.setImage(imageBkgd);

        //add them to the main window layout
        add("Center", m_imageCanvas);

        // initialize the controls in a panel
        m_controlPanel = new Panel();
        add("East", m_controlPanel);
        Font f = getFont();
        m_controlPanel.setFont(f);
        controls = new IDD_DIALOG1(m_controlPanel);
//		controls = new IDD_DIALOG1 (this);
        controls.CreateControls();

        //initialize the controls
        initControls();
        resetControls();
        setState('S');

        //don't let the process begin until settings have been saved
        controls.IDC_START.setEnabled(false);

    }

    // Place additional applet clean up code here.  destroy() is called when
    // when you applet is terminating and being unloaded.
    //-------------------------------------------------------------------------
    public void destroy() {
        // TODO: Place applet cleanup code here
    }

    // Ionex Paint Handler
    //--------------------------------------------------------------------------
    public void paint(Graphics g) {
        if (this.m_imageCanvas != null)
            this.m_imageCanvas.paint(g);
        else
            System.out.println("imagecanvas Doesn't exist!");
    }

    //	The start() method is called when the page containing the applet
    // first appears on the screen. The AppletWizard's initial implementation
    // of this method starts execution of the applet's thread.
    //--------------------------------------------------------------------------
    public void start() {
/*		if (m_ionex == null)
        {
			m_ionex = new Thread(this);
			m_ionex.start();
		}
		// TODO: Place additional applet start code here
*/
//		m_imageCanvas.start();
    }

    //		The stop() method is called when the page containing the applet is
    // no longer on the screen. The AppletWizard's initial implementation of
    // this method stops execution of the applet's thread.
    //--------------------------------------------------------------------------
    public void stop() {
/**
		if (m_ionex != null)
		{
			m_ionex.stop();
			m_ionex = null;
		}
*/
        m_imageCanvas.stop();
        // TODO: Place additional applet stop code here
    }

    // THREAD SUPPORT
    //		The run() method is called when the applet's thread is started. If
    // your applet performs any ongoing activities without waiting for user
    // input, the code for implementing that behavior typically goes here. For
    // example, for an applet that performs animation, the run() method controls
    // the display of images.
    //--------------------------------------------------------------------------
    public void run() {
/**
		while (true)
		{
			try
			{
				repaint();
				// TODO:  Add additional thread-specific code here
				Thread.sleep(50);
			}
			catch (InterruptedException e)
			{
				// TODO: Place exception-handling code here in case an
				//       InterruptedException is thrown by Thread.sleep(),
				//		 meaning that another thread has interrupted this one
				stop();
			}
		}
*/
    }


    public boolean action(Event evt, Object arg) {
        if (evt.target instanceof Button) {
            if (arg.equals("Reset Settings")) {
                resetControls();
                return true;
            }

            if (arg.equals("Load Experiment")) {
                saveInput();
                return true;
            }

            if (arg.equals("Add Protein")) {
                addProtein();
                return true;
            }

            if (arg.equals("Remove Protein")) {
                removeProtein();
                return true;
            }
            if (arg.equals("Start")) {
                processStart();
                return true;
            }
            if (arg.equals("Stop")) {
                processStop();
                return true;
            }
            if (arg.equals("Pause")) {
                processPause();
                return true;
            }
        }
        return false;
    }

    //initialize the controls
    void initControls() {
        String strProtein, strFile;
        URL url;
        InputStream in;
        DataInputStream dis;
        String strLine = null;
        CProtein protein;

        //read in the list of available proteins
        try {
            url = new URL(getCodeBase(), "pdb/pdb.idx");
            in = url.openStream();
            dis = new DataInputStream(in);
            int i = 0;

            while ((strLine = dis.readLine()) != null) {
                int nPos = strLine.indexOf('\t');
                strFile = new String(strLine.substring(nPos + 1));
                strProtein = new String(strLine.substring(0, nPos));
                m_arrAvailProteins[i] = new ProteinFile(strProtein, strFile);
                controls.IDC_SELECTPROTEIN.addItem(strProtein, i++);

//				m_vAvailProteins.addElement( strProtein);
            }
            in.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (SecurityException e) {
        }

/*
		// populate the list of available proteins
		for( int i = 0; i < m_vAvailProteins.size(); i++){
			controls.IDC_SELECTPROTEIN.addItem( (String)m_vAvailProteins.elementAt( i));
		}
*/
/*
		// populate the list of available proteins
		for( int i = 0; i < m_arrAvailProteins.length; i++){
			if( m_arrAvailProteins[i] == null){
				break;
			}
			controls.IDC_SELECTPROTEIN.addItem(m_arrAvailProteins[ i].getName(), i);
		}
*/
        //select the first item
        controls.IDC_SELECTPROTEIN.select(0);
    }


    //reset the controls to the member variable values
    void resetControls() {
        String strTemp;

        controls.IDC_SOLVENTA.setText((new Double(m_dConc1)).toString());
        controls.IDC_SOLVENTB.setText((new Double(m_dConc2)).toString());
        switch (m_nBuffer) {
            case 0:
                controls.IDC_BUFFER1.setState(true);
                break;
            case 1:
                controls.IDC_BUFFER2.setState(true);
                break;
            case 2:
            default:
                controls.IDC_BUFFER3.setState(true);
                break;
        }

        if (m_bPositiveResin) {
            controls.IDC_POS.setState(true);
        } else {
            controls.IDC_NEG.setState(true);
        }

        resetProteinList();
    }

    //
    void resetProteinList() {
        int i;

        //copy the saved protein list to the currently selected protein list
        //and add the proteins to the listbox
        controls.IDC_PROTEINS.clear();
        for (i = 0; i < 5; i++) {
            if (m_arrProteins[i] != null) {
                CProtein protein = new CProtein(m_arrProteins[i].getName(),
                        m_arrProteins[i].getAmount(),
                        m_arrProteins[i].getFile(),
                        getPH(),
                        this);
                m_arrSelProteins.setElementAt(protein, i);
                String strTemp = new String(String.valueOf(m_arrProteins[i].getAmount()) +
                        "mg " + m_arrProteins[i].getName());
                controls.IDC_PROTEINS.addItem(strTemp);
            } else {
                //there isn't anymore proteins, clear out any that are stored
                if (m_arrSelProteins.size() > i) {
                    m_arrSelProteins.removeElementAt(i);
                }
            }
        }

        //enable/disable the Add and Remove controls
        if (controls.IDC_PROTEINS.countItems() == 5) {
            controls.IDC_ADD.disable();
        } else {
            controls.IDC_ADD.enable();
        }
        if (controls.IDC_PROTEINS.countItems() == 0) {
            controls.IDC_REMOVE.disable();
        } else {
            controls.IDC_REMOVE.enable();
        }
    }

    //save the entered values in the member variables
    void saveInput() {
        int nProteins = 0;

        //reload the image canvas
        m_imageCanvas.resetBackground();

        //disable the start button
        controls.IDC_START.disable();
        showStatus("Please wait while the experiment is being loaded...");

        //save the settings
        m_dConc1 = new Double(controls.IDC_SOLVENTA.getText()).doubleValue();
        m_dConc2 = new Double(controls.IDC_SOLVENTB.getText()).doubleValue();
        m_bPositiveResin = controls.IDC_POS.getState();
        if (controls.IDC_BUFFER1.getState()) {
            m_nBuffer = 0;
        }
        if (controls.IDC_BUFFER2.getState()) {
            m_nBuffer = 1;
        }
        if (controls.IDC_BUFFER3.getState()) {
            m_nBuffer = 2;
        }

        //copy the currently selected protein list to the saved protein list
        for (int i = 0; i < 5; i++) {
            if (i < m_arrSelProteins.size()) {
                CProtein p = (CProtein) m_arrSelProteins.elementAt(i);
                CProtein protein = new CProtein(p.getName(),
                        p.getAmount(),
                        p.getFile(),
                        getPH(),
                        this);
                protein.load();
                m_arrProteins[i] = protein;
            } else {
                m_arrProteins[i] = null;
            }
        }

        //load the column
        loadColumn();

        //the process may begin now
        controls.IDC_START.enable();
        showStatus("The experiment has been loaded and is ready to run.");

        //get the image canvas ready to run the experiment
        m_imageCanvas.prepareBackground();

    }

    public void removeProtein() {
        int nSelItem;

        //remove from listbox
        nSelItem = controls.IDC_PROTEINS.getSelectedIndex();
        if ((nSelItem < 0) || (nSelItem > 4)) {
            // we'll just ignore it
            return;
        }
        controls.IDC_PROTEINS.delItem(nSelItem);
        controls.IDC_ADD.enable();

        //remove from protein list
        m_arrSelProteins.removeElementAt(nSelItem);

        //reset the controls
        checkAddRemove();
    }

    void checkAddRemove() {
        if (m_arrSelProteins.size() <= 0) {
            controls.IDC_REMOVE.disable();
        } else {
            controls.IDC_REMOVE.enable();
        }

        if (m_arrSelProteins.size() >= 5) {
            controls.IDC_ADD.disable();
        } else {
            controls.IDC_ADD.enable();
        }

    }

    public void addProtein() {
        int nAmount = 0;
        String strName;
        String strFile;
        int j = 0;

        //get the text of the selected protein item, parse it for the protein name and the file name
        strName = new String(controls.IDC_SELECTPROTEIN.getSelectedItem());
        j = controls.IDC_SELECTPROTEIN.getSelectedIndex();
        strFile = m_arrAvailProteins[j].getFile();

        // get the amount entered
        String strAmount = controls.IDC_AMOUNT.getText();
        if (strAmount.equals("")) {
            nAmount = 0;
        } else {
            nAmount = new Integer(strAmount).intValue();
        }

        // see if this protein is already in the list, if so add the amount
        for (int i = 0; i < m_arrSelProteins.size(); i++) {
            CProtein p = (CProtein) m_arrSelProteins.elementAt(i);
            if (strFile.equals(p.getFile())) {
                int nNewAmount = p.getAmount() + nAmount;
                p.setAmount(nNewAmount);

                //add the new amount to the listbox
                controls.IDC_PROTEINS.delItem(i);
                controls.IDC_PROTEINS.addItem(String.valueOf(nNewAmount) + "mg " + strName, i);
                return;
            }
        }

        m_arrSelProteins.addElement(new CProtein(strName, nAmount, strFile, getPH(), this));

        //add it to the listbox
        controls.IDC_PROTEINS.addItem(strAmount + "mg " + strName);

        //reset the controls
        checkAddRemove();
    }

    public void processStart() {
        // if starting from scratch, reload column
        if (m_cState == 'S') {
            loadColumn();

            //get the image canvas ready to run the experiment
            m_imageCanvas.prepareBackground();

        }

        //set the state
        setState('R');

        showStatus("The experiment is running");

        // start the animation
        m_imageCanvas.start();

    }

    public void processStop() {
        //set the state
        setState('S');
        showStatus("The experiment has stopped");

        // stop the animation
        m_imageCanvas.stop();
    }

    public void processPause() {
        //set the state
        setState('P');
        showStatus("The experiment is paused");

        // pause the animation
        m_imageCanvas.pause();
    }

    public void setState(char cState) {
        m_cState = cState;

        switch (cState) {
            case 'S':
                //stopped
                controls.IDC_PAUSE.setEnabled(false);
                controls.IDC_STOP.setEnabled(false);
                controls.IDC_START.setEnabled(true);
                controls.IDC_UPDATE.setEnabled(true);
                controls.IDC_RESET.setEnabled(true);
                break;

            case 'R':
                // running
                controls.IDC_PAUSE.setEnabled(true);
                controls.IDC_STOP.setEnabled(true);
                controls.IDC_START.setEnabled(false);
                controls.IDC_UPDATE.setEnabled(false);
                controls.IDC_RESET.setEnabled(false);
                break;

            case 'P':
                //paused
                controls.IDC_PAUSE.setEnabled(false);
                controls.IDC_STOP.setEnabled(true);
                controls.IDC_START.setEnabled(true);
                controls.IDC_UPDATE.setEnabled(false);
                controls.IDC_RESET.setEnabled(false);
                break;
        }
    }

    double getPH() {
        switch (m_nBuffer) {
            case 0:
                return 4.8;
            case 1:
                return 7.2;
            case 2:
                return 8.0;
        }

        //should never happen
        return 0.0;
    }

    void loadColumn() {
        CProtein protein;
        int nPos = 1;
        double arrCharge[] = {0, 0, 0, 0, 0};
        int nIndex = 0;
        int nTemp;
        int i, j;

        //first loop through the proteins and determine whether
        //or not they are initially bound to the resin
        for (i = 0; i < m_arrProteins.length; i++) {
            if (m_arrProteins[i] == null) {
                continue;
            }

            protein = m_arrProteins[i];

            // a protein is not bound if it has the same charge as
            // the resin or if its charge is less than the initial
            // salt concentration
            if (((!m_bPositiveResin) && (protein.m_dCharge <= m_dConc1 * 100)) ||
                    ((m_bPositiveResin) && (protein.m_dCharge >= -m_dConc1 * 100))) {
                protein.m_bBound = false;

                //the width of unbound protein band is 3
                protein.m_nBandwidth = 3;
            } else {
                protein.m_bBound = true;

                //calculate the width of the protein band
                protein.m_nBandwidth = protein.m_nAmount / 2;

                // save the charge information of bound proteins
                arrCharge[i] = Math.abs(protein.m_dCharge);
            }
        }

        //put the highest charge proteins first in the column
        for (i = 0; i < m_arrProteins.length; i++) {
            if (m_arrProteins[i] == null) {
                continue;
            }

            // find the highest charge in the array
            nTemp = i;
            for (j = 0; j < m_arrProteins.length; j++) {
                if (arrCharge[j] > arrCharge[nTemp]) {
                    nTemp = j;
                }
            }

            protein = m_arrProteins[nTemp];

            // set the position of the protein within the column
            protein.m_nPos = nPos;

            //if the protein is bound then it occupies space, otherwise not
            if (protein.m_bBound) {
                nPos += protein.m_nBandwidth;

                //if part of the protein doesn't fit in the column, recalculate the
                //bandwidth and the amount
                if (protein.m_nPos + protein.m_nBandwidth > COLHIY - COLLOY) {
                    protein.m_nBandwidth = (COLHIY - COLLOY - 1) - protein.m_nPos;
                    protein.m_nAmount = protein.m_nBandwidth * 2;
                }
            }

            //if the protein is out of the column, then set the amount to 0
            if (protein.m_nPos > COLHIY - COLLOY) {
                protein.m_nBandwidth = 0;
                protein.m_nAmount = 0;
            }

            // indicate that this one has been done
            arrCharge[nTemp] = -1;
        }

        //if more than one protein is not bound, it is mixed with others in the
        //same band, set the member variable
        nTemp = -1;
        for (i = 0; i < m_arrProteins.length; i++) {
            if (m_arrProteins[i] == null) {
                continue;
            }

            protein = m_arrProteins[i];

            if (protein.m_bBound == false) {
                if (nTemp == -1) {
                    nTemp = i;
                } else {
                    protein.m_bMix = true;
                    protein = m_arrProteins[nTemp];
                    protein.m_bMix = true;
                }
            }
        }
    }
}