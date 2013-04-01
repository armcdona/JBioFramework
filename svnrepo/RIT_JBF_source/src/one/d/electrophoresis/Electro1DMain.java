package one.d.electrophoresis;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Omega
 * Date: 4/1/13
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
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
