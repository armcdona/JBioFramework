
/**
 *
 * @author Amanda Fisher
 */
import javax.swing.*;
import java.awt.event.*;

/**
 * The type Color key button swing version.
 */
public class ColorKeyButtonSwingVersion extends JButton implements ActionListener {
    /**
     * The Key.
     */
    ColorFrame key;

    /**
     * Instantiates a new Color key button swing version.
     */
    public ColorKeyButtonSwingVersion () {

        super("Color Key");
        addActionListener(this);
        key = new ColorFrame();
}

    public void actionPerformed (ActionEvent e) {

        key.showKey();

    }
}
