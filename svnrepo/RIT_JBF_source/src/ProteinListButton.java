
/**
 * @author Amanda Fisher
 */

import javax.swing.*;
import java.awt.event.*;

/**
 * The type Protein list button swing version.
 */
public class ProteinListButton extends JButton implements ActionListener {

    /**
     * The Electro 2 d.
     */
    Electro2D electro2D;

    /**
     * Instantiates a new Protein list button swing version.
     *
     * @param e the e
     */
    public ProteinListButton(Electro2D e) {

        super("Display Protein List");
        electro2D = e;
        addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {

        electro2D.displayProtList();

    }

}
