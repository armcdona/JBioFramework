
/**
 *
 * @author Amanda Fisher
 */
import javax.swing.*;
import java.awt.event.*;

/**
 * Displays the Color Window (tells the user what a dots color means)
 */
public class ColorKeyButton extends JButton implements ActionListener {
    ColorFrame key;

    public ColorKeyButton() {

        super("Color Key");
        addActionListener(this);
        key = new ColorFrame();
}

    public void actionPerformed (ActionEvent e) {

        key.showKey();

    }
}
