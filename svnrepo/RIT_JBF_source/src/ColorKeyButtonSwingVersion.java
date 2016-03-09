
/**
 *
 * @author Amanda Fisher
 */
import javax.swing.*;
import java.awt.event.*;

/**
 * TDisplays the Color Window
 */
public class ColorKeyButtonSwingVersion extends JButton implements ActionListener {
    ColorFrame key;

    public ColorKeyButtonSwingVersion () {

        super("Color Key");
        addActionListener(this);
        key = new ColorFrame();
}

    public void actionPerformed (ActionEvent e) {

        key.showKey();

    }
}
