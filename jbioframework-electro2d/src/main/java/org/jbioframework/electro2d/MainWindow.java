package org.jbioframework.electro2d;


import org.apache.log4j.BasicConfigurator;
import org.jbioframework.library.gui.MarvinTab;
import org.jbioframework.library.gui.WelcomeWindow;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        super("2D Electrophoresis");
        this.setName("JBioFramework - 2D Electrophoresis");
        BasicConfigurator.configure();
        JTabbedPane window = new JTabbedPane();
        window.add("Welcome", new WelcomeWindow());
        window.add("2D Electrophoresis", new Electro2D());
        window.add("Marvin Sketch", new MarvinTab().createMainPanel());

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        add(window);
        super.pack();
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        super.setVisible(true);

    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
