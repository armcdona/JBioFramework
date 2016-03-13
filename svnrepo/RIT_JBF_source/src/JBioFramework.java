/*
 * The main frame for the JBioFramework program
 * Adds Electro2D, Electrophoresis, and Ionex applications to itself
 * with a JTabbedPane.
 */

/**
 * @author Amanda Fisher
 */

import javax.swing.*;
import java.awt.*;

/**
 * Main class which starts the program and opens and initializes the main window
 */
public class JBioFramework extends JFrame {

    public static final long serialVersionUID = 1L;
    private JTabbedPane tabbedPane;
    private Electro2D electro2D;
    private MainPanelGUI spectrometer;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        JBioFramework jbfSwing = new JBioFramework();
    }

    /**
     * Initializes a new JBioFramework Window
     */
    public JBioFramework() {
        super();

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        electro2D = new Electro2D();
        spectrometer = new MainPanelGUI();

        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Electro2D", electro2D);
        tabbedPane.addTab("Mass Spectrometer", spectrometer);

        add(tabbedPane);

        /**
         * Use a toolit to find the screen size of the user's monitor and set
         * the window size to it.
         */

        setSize(Toolkit.getDefaultToolkit().getScreenSize());

        this.setMinimumSize(new Dimension(1024, 600));//set the Minimum size of the window because the window will not display all of the required information below this size
        this.setMaximumSize(new Dimension(1024, 768));//set the Maximim size of the window because automatic scaling has not been implemented yet
        this.pack();
    }
}
