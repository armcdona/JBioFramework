package Electro2D;/*
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

/**
 * Allows the user to choose the method by which to sort the HTML page to be
 * automatically generated.
 *
 * @author Jill Zapoticznyj
 * <p>
 * Created: 11/06/2003
 */

import Electro2D.Electro2D;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;

public class HTMLGenScreen extends JFrame implements ActionListener,
        MouseListener {

    private static final int PI_VAL = 1;
    private static final int TITLE_VAL = 0;
    private static final int FUNCTION_VAL = 3;
    private static final int MW_VAL = 2;
    private JPanel contentPanel;
    private ButtonGroup buttons;
    private JRadioButton functionButton;
    private JRadioButton titleButton;
    private JRadioButton piButton;
    private JRadioButton mwButton;
    private JButton submit;
    private int sortField;
    private Electro2D electro2D;

    public HTMLGenScreen(Electro2D e) {
        super("Sort Web Page By:");
        setResizable(false);
        electro2D = e;
        sortField = 0;
        functionButton = new JRadioButton("Protein Function");
        functionButton.setActionCommand("function");
        functionButton.addActionListener(this);
        piButton = new JRadioButton("pI Value");
        piButton.setActionCommand("pI");
        piButton.addActionListener(this);
        mwButton = new JRadioButton("Molecular Weight Value");
        mwButton.setActionCommand("mw");
        mwButton.addActionListener(this);
        titleButton = new JRadioButton("Protein Title");
        titleButton.setActionCommand("title");
        titleButton.addActionListener(this);
        titleButton.setSelected(true);

        buttons = new ButtonGroup();
        buttons.add(titleButton);
        buttons.add(piButton);
        buttons.add(mwButton);
        buttons.add(functionButton);

        submit = new JButton("Generate HTML Document");
        submit.addMouseListener(this);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(
                contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(titleButton);
        contentPanel.add(piButton);
        contentPanel.add(mwButton);
        contentPanel.add(functionButton);
        contentPanel.add(submit);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.add(contentPanel);
        this.setVisible(true);
        pack();
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("title")) {
            sortField = TITLE_VAL;
        } else if (cmd.equals("pI")) {
            sortField = PI_VAL;
        } else if (cmd.equals("mw")) {
            sortField = MW_VAL;
        } else if (cmd.equals("function")) {
            sortField = FUNCTION_VAL;
        }
    }

    public void mouseClicked(MouseEvent e) {
        WebGenerator.setSearch(sortField);
        electro2D.generateWebPage();
        this.dispose();
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
