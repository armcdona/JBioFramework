package org.jbioframework.electro2d;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JOptionPane;


public class SearchProteinFunction implements MouseListener, ActionListener {

    private ArrayList dots1;
    private ArrayList dots2;
    private GelCanvas gel;
    private Electro2D electro2D;
    private JFrame window;
    private JButton search;
    private JButton reset;
    private JTextField searchTerm;
    private JTextField excludeTerm;
    private JPanel buttonPane;
    private JPanel buttonLabelPane;
    private JPanel buttonSelectionPane;
    private JPanel searchLabelPane;
    private JPanel searchFieldPane;
    private JPanel textPane;
    private JLabel includesLabel;
    private JLabel excludesLabel;
    private String searchField;
    private ButtonGroup radioButtons;
    private JRadioButton sequenceButton;
    private JRadioButton titleButton;
    private JRadioButton functionButton;

    public SearchProteinFunction(Electro2D e2D) {

        electro2D = e2D;
        radioButtons = new ButtonGroup();

        gel = electro2D.getGel();
        dots1 = new ArrayList();
        dots2 = new ArrayList();
        excludesLabel = new JLabel("Excludes: ");
        includesLabel = new JLabel("Includes: ");
        searchTerm = new JTextField();
        excludeTerm = new JTextField();
        buttonPane = new JPanel();
        buttonLabelPane = new JPanel();
        buttonSelectionPane = new JPanel();
        textPane = new JPanel();
        searchLabelPane = new JPanel();
        searchFieldPane = new JPanel();
        window = new JFrame();
        window.addWindowListener(new WindowAdapter() {
                                     public void windowClosing(WindowEvent e) {
                                         window.setVisible(false);
                                     }
                                 }
        );

        search = new JButton("Search");
        reset = new JButton("Reset");
        search.addActionListener(new SearchListener());
        reset.addActionListener(new ResetListener());
        sequenceButton = new JRadioButton("Sequence", false);
        titleButton = new JRadioButton("Protein Title", true);
        functionButton = new JRadioButton("Protein Function", false);
        searchField = "function";
        sequenceButton.addActionListener(this);
        titleButton.addActionListener(this);
        functionButton.addActionListener(this);
        sequenceButton.setActionCommand("sequence");
        titleButton.setActionCommand("title");
        functionButton.setActionCommand("function");
        functionButton.setMnemonic(KeyEvent.VK_F);
        sequenceButton.setMnemonic(KeyEvent.VK_S);
        titleButton.setMnemonic(KeyEvent.VK_T);
        radioButtons.add(sequenceButton);
        radioButtons.add(titleButton);
        radioButtons.add(functionButton);

        buttonLabelPane.setLayout(new GridLayout(0, 1));
        JLabel labelPane = new JLabel("Select Search Field");
        buttonLabelPane.add(labelPane);
        buttonSelectionPane.setLayout(new GridLayout(0, 1));
        buttonSelectionPane.add(titleButton);
        buttonSelectionPane.add(functionButton);
        buttonSelectionPane.add(sequenceButton);

        buttonPane.setLayout(new BorderLayout());
        buttonPane.add(buttonLabelPane, BorderLayout.NORTH);
        buttonPane.add(buttonSelectionPane, BorderLayout.CENTER);
        searchLabelPane.setLayout(new GridLayout(0, 1));
        JLabel searchLabel = new JLabel("Enter Search Term");
        searchLabelPane.add(searchLabel);
        searchFieldPane.setLayout(new GridLayout(0, 1));
        searchFieldPane.add(includesLabel);
        searchFieldPane.add(searchTerm);
        searchFieldPane.add(excludesLabel);
        searchFieldPane.add(excludeTerm);
        searchFieldPane.add(search);
        searchFieldPane.add(reset);

        textPane.setLayout(new BorderLayout());
        textPane.add(searchLabelPane, BorderLayout.NORTH);
        textPane.add(searchFieldPane, BorderLayout.CENTER);


        window.setLayout(new BorderLayout());
        window.setTitle("Search Proteins");
        window.add(buttonPane, BorderLayout.WEST);
        window.add(textPane, BorderLayout.EAST);
        window.pack();
        window.setVisible(true);

    }

    private class SearchListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            searchFor(searchTerm.getText(), excludeTerm.getText());
        }
    }

    private class ResetListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            displayAll();
        }
    }

    public void searchFor(String fcnName, String limitations) {
        dots1 = gel.getDots();
        dots2 = gel.getDots2();

        if (dots1 == null) {
            dots1 = new ArrayList();
        }

        if (dots2 == null) {
            dots2 = new ArrayList();
        }

        ProteinDot prot = null;
        if (searchField.equals("function")) {
            for (int i = 0; i < dots1.size(); i++) {
                prot = (ProteinDot) dots1.get(i);
                if (((E2DProtein) prot.getPro()).getFunction().indexOf(fcnName)
                        == -1) {
                    prot.doNotShowMe();
                } else if (((E2DProtein) prot.getPro()).getFunction().indexOf(
                        limitations) != -1 && !limitations.equals("")) {
                    prot.doNotShowMe();
                }
            }
            if (dots2.size() != 0) {
                for (int j = 0; j < dots2.size(); j++) {
                    prot = (ProteinDot) dots2.get(j);
                    if (((E2DProtein) prot.getPro()).getFunction().indexOf(
                            fcnName) == -1) {
                        prot.doNotShowMe();
                    } else if (((E2DProtein) prot.getPro()).getFunction().indexOf(
                            limitations) != -1 && !limitations.equals("")) {
                        prot.doNotShowMe();
                    }
                }
            }
        } else if (searchField.equals("sequence")) {
            for (int i = 0; i < dots1.size(); i++) {
                prot = (ProteinDot) dots1.get(i);
                if (((E2DProtein) prot.getPro()).getSequence().indexOf(fcnName)
                        == -1) {
                    prot.doNotShowMe();
                }
            }
            if (dots2.size() != 0) {
                for (int j = 0; j < dots2.size(); j++) {
                    if (((E2DProtein) prot.getPro()).getSequence().indexOf(fcnName)
                            == -1) {
                        prot.doNotShowMe();
                    }
                }
            }
        } else if (searchField.equals("title")) {
            for (int i = 0; i < dots1.size(); i++) {
                prot = (ProteinDot) dots1.get(i);
                if (((E2DProtein) prot.getPro()).getID().indexOf(fcnName)
                        == -1) {
                    prot.doNotShowMe();
                } else if (((E2DProtein) prot.getPro()).getID().indexOf(
                        limitations) != -1 && !limitations.equals("")) {
                    prot.doNotShowMe();
                }
            }
            if (dots2.size() != 0) {
                for (int j = 0; j < dots2.size(); j++) {
                    prot = (ProteinDot) dots2.get(j);
                    if (((E2DProtein) prot.getPro()).getID().indexOf(fcnName)
                            == -1) {
                        prot.doNotShowMe();
                    } else if (((E2DProtein) prot.getPro()).getID().indexOf(
                            limitations) != -1 && !limitations.equals("")) {
                        prot.doNotShowMe();
                    }
                }
            }
        }
        int count = 0;
        for (ProteinDot dot : (ArrayList<ProteinDot>) dots1) {
            if (dot.getShowMe()) {
                count++;
            }
        }
        for (ProteinDot dot : (ArrayList<ProteinDot>) dots2) {
            if (dot.getShowMe()) {
                count++;
            }
        }
        JOptionPane.showMessageDialog(null, "Search found " + count + " proteins.");
        count = 0;
        gel.update(gel.getGraphics());
    }

    public void displayAll() {
        dots1 = gel.getDots();
        dots2 = gel.getDots2();
        for (int i = 0; i < dots1.size(); i++) {
            ((ProteinDot) dots1.get(i)).doShowMe();
        }
        if (dots2.size() != 0) {
            for (int j = 0; j < dots2.size(); j++) {
                ((ProteinDot) dots2.get(j)).doShowMe();
            }
        }
//	gel.repaint();
        gel.update(gel.getGraphics());
    }

    public void hideExcludeFields() {
        //if( !title.getSelected() && !function.getSelected() ){
        searchFieldPane.remove(excludesLabel);
        searchFieldPane.remove(excludeTerm);
        window.validate();
        //}
    }

    public void actionPerformed(ActionEvent e) {
        searchField = e.getActionCommand();

    }

    public void mouseClicked(MouseEvent e) {
        searchFieldPane.add(excludesLabel);
        searchFieldPane.add(excludeTerm);
        window.validate();
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    private class HideExclude implements MouseListener {

        private SearchProteinFunction spf;

        public HideExclude(SearchProteinFunction s) {
            spf = s;
        }

        public void mouseClicked(MouseEvent e) {
            spf.hideExcludeFields();
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

}







