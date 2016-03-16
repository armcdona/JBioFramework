
/**
 * @author Amanda Fisher
 */

import javax.swing.*;
import java.awt.event.*;

/**
 * The type Search protein field button swing version.
 */
public class SearchProteinFieldButton extends JButton implements ActionListener {

    /**
     * The Electro 2 d.
     */
    Electro2D electro2D;
    /**
     * The Spf.
     */
    SearchProteinFunction spf;

    /**
     * Instantiates a new Search protein field button swing version.
     *
     * @param e the e
     */
    public SearchProteinFieldButton(Electro2D e) {

        super("Search Protein Field");
        electro2D = e;
        addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {

        spf = new SearchProteinFunction(electro2D);

    }

}
