
/**
 * Performs a BLAST search for the protein sequence given to it from
 * ProteinFrame.
 *
 * @author Jill Zapoticznyj
 */

import javax.swing.*;
import java.awt.event.*;

/**
 * When pressed opens the protein blast search window
 */
public class BlastSearchButton extends JButton implements ActionListener {

    private Electro2D electro2D;
    private String pro_sequence = "";

    /**
     * Creates the button
     *
     * @param seq the string that will be searched for
     * @param e a reference to Electro2D
     * @param text the text of the button
     */
    public BlastSearchButton(Electro2D e, String seq, String text) {
        super(text);
        electro2D = e;
        pro_sequence = seq;
        this.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        electro2D.showBlastSearchPage(pro_sequence);
    }

}
