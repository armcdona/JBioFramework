
/**
 *
 * @author Amanda Fisher
 */

import javax.swing.*;
import java.awt.event.*;

/**
 * When activated a window appears asking the user to select how he wants the proteins sorted, and then generates an html file with the proteins
 */
public class GenerateHTMLButton extends JButton implements ActionListener {

    Electro2D electro2D;

    public GenerateHTMLButton(Electro2D e) {

        super("Generate HTML Page");
        electro2D = e;
        addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {

        new HTMLGenScreen(electro2D);

    }

}
