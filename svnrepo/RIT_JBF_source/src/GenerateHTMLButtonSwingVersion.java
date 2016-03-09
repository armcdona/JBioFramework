
/**
 *
 * @author Amanda Fisher
 */

import javax.swing.*;
import java.awt.event.*;

/**
 * The type Generate html button swing version.
 */
public class GenerateHTMLButtonSwingVersion extends JButton implements ActionListener {

    /**
     * The Electro 2 d.
     */
    Electro2D electro2D;

    /**
     * Instantiates a new Generate html button swing version.
     *
     * @param e the e
     */
    public GenerateHTMLButtonSwingVersion (Electro2D e) {

        super("Generate HTML Page");
        electro2D = e;
        addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {

        new HTMLGenScreen(electro2D);

    }

}
