
/**
 *
 * @author Amanda Fisher
 */

import javax.swing.*;
import java.awt.event.*;

/**
 * Button to activate the protein comparison.  This is not used at this time.
 */
public class CompareProteinsButton extends JButton implements ActionListener {

    Electro2D electro2D;

    public CompareProteinsButton(Electro2D e) {

        super("Compare Proteins");
        electro2D = e;
        addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {

	    electro2D.getSequenceData2();
	    PlayButton.setCompare(true);

    }
}
