
/**
 *
 * @author Amanda Fisher
 */

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

/**
 * The type About button swing version.
 */
public class AboutButtonSwingVersion extends JButton implements ActionListener {
    /**
     * The Electro 2 d.
     */
    Electro2D electro2D;

    /**
     * Instantiates a new About button swing version.
     */
    public AboutButtonSwingVersion () {
        super("About");
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        // open a new window and display the about page
	File f = new File( "HTML Files" + File.separator + "about.html" );
	try{
            BrowserLauncher.openHTMLFile(f);
	}catch(IOException i){
            System.err.println(i.getMessage());
            i.printStackTrace();
        }
    }
//10.6.8
}