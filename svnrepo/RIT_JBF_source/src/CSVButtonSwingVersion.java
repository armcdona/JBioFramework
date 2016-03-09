
/**
 *
 * @author Amanda Fisher
 */
import javax.swing.*;
import java.awt.event.*;

/**
 * The type Csv button swing version.
 */
public class CSVButtonSwingVersion extends JButton implements ActionListener {
    /**
     * The Electro 2 d.
     */
    Electro2D electro2D;

    /**
     * Instantiates a new Csv button swing version.
     *
     * @param e the e
     */
    public CSVButtonSwingVersion (Electro2D e) {

        super("Record to CSV");
        addActionListener(this);
        electro2D = e;
}

    public void actionPerformed (ActionEvent e) {

        electro2D.writeToCSV();

    }
}