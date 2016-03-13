
/**
 *
 * @author Amanda Fisher
 */
import javax.swing.*;
import java.awt.event.*;

/**
 * When pressed, the current array of dots (ProteinDot) in the gel are saved in a csv file
 */
public class CSVButtonSwingVersion extends JButton implements ActionListener {

    Electro2D electro2D;

    public CSVButtonSwingVersion (Electro2D e) {

        super("Record to CSV");
        addActionListener(this);
        electro2D = e;
}

    public void actionPerformed (ActionEvent e) {

        electro2D.writeToCSV();

    }
}