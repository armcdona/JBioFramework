
/**
 *
 * @author Amanda Fisher
 */

import javax.swing.*;
import java.awt.event.*;

/**
 * Opens window for user to enter a new protein sequence
 */
public class AddProteinButtonSwingVersion extends JButton implements ActionListener {

    Electro2D electro2D;

    /**
     * Adds a new protein
     *
     * @param e The protein entered
     */
    public AddProteinButtonSwingVersion (Electro2D e) {

        super("Add Proteins");
        addActionListener(this);
        electro2D = e;
}

    public void actionPerformed (ActionEvent e) {

        electro2D.getSequenceData();

    }
}
