package one.d.electrophoresis;
  /*
 * Copyright (C) 2013 Rochester Institute of Technology
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU General Public License for more details.
 */

import javax.swing.JFrame;
import java.awt.Dimension;

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
        frame.setPreferredSize(new Dimension(400, 350));
        frame.pack();
        frame.setVisible(true);
    }
}