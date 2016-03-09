
/**
 *
 * @author Amanda Fisher
 */

import javax.swing.*;
import java.awt.event.*;

/**
 * The type Compare proteins button swing version.
 */
public class CompareProteinsButtonSwingVersion extends JButton implements ActionListener {

    /**
     * The Electro 2 d.
     */
    Electro2D electro2D;

    /**
     * Instantiates a new Compare proteins button swing version.
     *
     * @param e the e
     */
    public CompareProteinsButtonSwingVersion(Electro2D e) {

        super("Compare Proteins");
        electro2D = e;
        addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {

	electro2D.getSequenceData2();
	PlayButtonSwingVersion.setCompare(true);

    }
}
