
/**
 *
 * @author Amanda Fisher
 */
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

/**
 * The type Help button swing version.
 */
public class HelpButtonSwingVersion extends JButton implements ActionListener {

    /**
     * Instantiates a new Help button swing version.
     */
    public HelpButtonSwingVersion () {

        super("Help");
        addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {

	//create a File object
        //catch and display any errors that occurred while assigning
        //information to the File
	// if no errors occurred, open a new window and display the help page
	File f = new File( "HTML Files" + File.separator + "Help" + File.separator + "help.html" );
	try{
	    BrowserLauncher.openHTMLFile(f);
	} catch(IOException i){
            System.err.println( i.getMessage());
        }
        
    }
}
