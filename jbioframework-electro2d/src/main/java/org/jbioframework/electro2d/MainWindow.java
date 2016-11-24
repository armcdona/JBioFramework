package org.jbioframework.electro2d;


import org.jbioframework.library.gui.MarvinTab;
import org.jbioframework.library.gui.WelcomeWindow;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        super("2D Electrophoresis");
        JTabbedPane window = new JTabbedPane();
        window.add("Welcome", new WelcomeWindow());
        window.add("2D Electrophoresis", new Electro2D());
        window.add("Marvin Sketch", new MarvinTab().createMainPanel());

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
