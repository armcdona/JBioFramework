package one.d.electrophoresis;

import javax.swing.*;
import java.awt.*;

/**
 * This is a main method for the 1D Electrophoresis. It basically allows for
 * easy way to launch/test the 1D Electrophoresis component
 * @author  Benjamin Russell (brr1922@rit.edu)
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
