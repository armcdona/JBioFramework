package org.jbioframework.massspec;


import org.apache.log4j.BasicConfigurator;
import org.jbioframework.library.gui.MarvinTab;
import org.jbioframework.library.gui.WelcomeWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    static JTabbedPane tabs;
    private final Logger logger = LoggerFactory.getLogger(MainWindow.class);

    public MainWindow() {
        super("2D Electrophoresis");
        this.setName("JBioFramework - 2D Electrophoresis");
        BasicConfigurator.configure();
        tabs = new JTabbedPane();
        tabs.add("Welcome", new WelcomeWindow());
        tabs.add("Mass Spectrometer", new MassSpecMain());
        tabs.add("Marvin Sketch", new MarvinTab().createMainPanel());

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            logger.error(e.getMessage());
        }

        add(tabs);
        super.pack();
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        super.setVisible(true);

    }

    public static JTabbedPane getTabs() {
        return tabs;
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
