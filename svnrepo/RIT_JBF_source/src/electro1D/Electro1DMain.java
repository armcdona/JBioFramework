package electro1D;

import javax.swing.JFrame;
import java.awt.Dimension;

/**
 * main class for 1d electro1D
 * @author Bader Alharbi
 */
public class Electro1DMain extends JFrame {
    private Electro1DMain() {
        super("Electrophoresis");
    }

    public static void main(String[] args) {
        // Create and set up the window.
        JFrame frame = new Electro1DMain();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set up the content pane.
        frame.add(new Electrophoresis());
        frame.setPreferredSize(new Dimension(622, 500));
        frame.pack();
        frame.setVisible(true);
    }
}