
/**
 *
 * @author Amanda Fisher
 */

import javax.swing.*;
import java.awt.event.*;

/**
 * The type Add protein button swing version.
 */
public class AddProteinButtonSwingVersion extends JButton implements ActionListener {
    /**
     * The Electro 2 d.
     */
    Electro2D electro2D;

    /**
     * Instantiates a new Add protein button swing version.
     *
     * @param e the e
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
