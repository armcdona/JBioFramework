package main.java.MainWindows; /**
 * Welcome Tab - First tab of JBFSuite.
 * Contains description and contacts, credits, and links to
 * sourceforge main page, help files, review page, etc. as well
 * as contact information for PaulCraig.
 *
 * @author Aidan Sawyer [rit: aks5238 | sf: daniels-ai]
 */

//import GUI components/layouts

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Font;

//import other utilities and listeners
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Cursor;
import java.io.IOException;

//import jbf utilities
import main.java.utilities.*;

/**
 * Welcome Tab - First tab of JBFSuite.
 * -Contains all code for the Welcome tab. (no code in other classes)
 * -Organized into 3 subpanels {Head, Body, Tail}. 
 * -the code is organized in such a way that each subpanel is created independently
 *  in the methods below, and is added to the main 'super' panel it extends
 *  in the constructor itself. this makes the code easier to read and/or modify
 * -extends JPanel to facilitate its addition to main JFrame in /Main.JBioFrameworkMain/
 *
 */
public class Welcome extends JPanel {

    //links to various jbf sites used below
    public static String jbfWebsite = "https://sourceforge.net/projects/jbf/";

    /**
     * Constructor for Welcome class fills main panel with desired subpanels
     * which have been constructed below.
     *
     * main panel uses GridLayout with 3 rows and 1 column.
     */
    public Welcome() {

        //adds GridBagLayout with GridBagConstraints 'c' to the JPanel it's extending
        super(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridy = 0;
        super.add(mkHeadPanel(), c);
        c.gridy = 1;
        super.add(mkBodyPanel(), c);
        c.gridy = 2;
        super.add(mkTailPanel(), c);
    }

    /**
     * Constructs and returns JPanel containing welcome message and description.
     *
     * @return returns the head panel
     */
    public JPanel mkHeadPanel() {
        JPanel headP = new JPanel(new GridBagLayout());
        GridBagConstraints con = new GridBagConstraints();

        //Formatted Header text (JLabel)
        JLabel head = new JLabel("Welcome to JBioframework!");
        head.setFont(new Font("SansSerif", Font.BOLD, 26));
        con.gridy = 0;
        headP.add(head, con);

        //Formatted Description (contained in a smaller panel)
        JPanel subHeadP = new JPanel();

        //java takes in html-style formatting. This increases readbility and aesthetics.
        String s = "<html>JBioFramework (JBF) is a set of chemical separations simulations frequently used in " +
                "chemistry, biochemistry and proteomics research. It is written in the Java programming language and will " +
                "run on any and all systems that have the JVM installed. A copy of the source code is available " +
                "online on Github, or by clicking 'Contact Us' > 'Source Code'. Click on one of the tabs  " +
                "in the upper left to get started.";
        String html1 = "<html><body style='width: ";
        String html2 = "px'>";
        JLabel head1 = new JLabel(html1 + "500" + html2 + s);

        //main webpage for the program
        final String url1 = jbfWebsite;

        String s2 = "<html><a href=" + url1 + ">here</a>.</body>";

        final JLabel headLink = new JLabel(s2);

        //code to make the hyperlink "headLink" respond to mouse events
        headLink.addMouseListener(new MouseListener() {

            //clicking on the link triggers an attempt to use /BrowserLauncher/ to open webpage
            public void mouseClicked(MouseEvent e) {
                try {
                    BrowserLauncher.openURL(url1);
                } catch (IOException i) {
                    System.err.println(i.getMessage());
                }
            }

            //don't do anything special when mouse in pressed or released
            public void mouseReleased(MouseEvent arg0) {
            }

            public void mousePressed(MouseEvent arg0) {
            }

            //when cursor is hovering over the link, switch it to the hand
            public void mouseEntered(MouseEvent arg0) {
                headLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            //when cursor leaves the link (stops hovering over it), return it to default.
            public void mouseExited(MouseEvent arg0) {
                headLink.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        head1.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subHeadP.add(head1);

        con.gridy = 1;
        headP.add(subHeadP, con);

        return headP;
    }

    /**
     * Constructs and returns BodyPanel containing help and about buttons
     *
     * @return returns the body panel
     */
    public JPanel mkBodyPanel() {
        JPanel body = new JPanel();

        //help button uses /BrowserLauncher/ to open our main wiki page on sourceforge.net
        JButton help = new JButton("Help");
        help.setToolTipText("Opens a link to our wiki");
        help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String url = "http://sourceforge.net/p/jbf/wiki/";
                try {
                    BrowserLauncher.openURL(url);
                } catch (IOException i) {
                    System.err.println(i.getMessage());
                }
            }
        });

        body.add(help);

        //about button uses /BrowserLauncher/ to open our main project page on sourceforge.net
        JButton about = new JButton("About");
        about.setToolTipText("Open project page on sourceforge.");
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    BrowserLauncher.openURL("http://www.rit.edu/cos/jbioframework/");
                } catch (IOException i) {
                    System.err.println(i.getMessage());
                }
            }
        });
        body.add(about);

        //probSheets button brings up a small new frame (pop-up) asking the user to click on either of our problem sets
        JButton probSheets = new JButton("Problem Sets");
        probSheets.setToolTipText("Download one of our pre-made problem sets:");
        probSheets.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame pSf = new JFrame("Problem Sets");
                JPanel pSp = new JPanel();
                JLabel pSl = new JLabel("Choose a problem set to open \n");
                pSp.add(pSl);

                //pSb1 is the button for opening Problem Set 1 geared more towards beginners.
                //  it does this by using /BrowserLancher/ to open the .docx page stored with our help files on sourceforge
                //  by default, this should download and open the file in their default word processor.
                JButton pSb1 = new JButton("Problem Set 1");
                pSb1.setToolTipText("High School and First Year College level");
                pSb1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String url = "https://docs.google.com/document/d/1sIvaC-tOh8Dm7c5P7t71OXytmwkna1Yp0zaxXcGsKms/edit?usp=sharing";
                        try {
                            BrowserLauncher.openURL(url);
                        } catch (IOException i) {
                            System.err.println(i.getMessage());
                        }
                    }
                });
                pSp.add(pSb1);

                //pSb2 is the button for opening Problem Set 2 geared toward more advanced users
                //  it does this by using /BrowserLancher/ to open the .docx page stored with our help files on sourceforge
                //  by default, this should download and open the file in their default word processor.
                JButton pSb2 = new JButton("Problem Set 2");
                pSb2.setToolTipText("Upper Division Bio and Chem Students");
                pSb2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String url = "https://docs.google.com/document/d/135rCzggvppfCvhCaAjUzZAB2NTO5sUV6vy_OtPTxeYc/edit?usp=sharing";
                        try {
                            BrowserLauncher.openURL(url);
                        } catch (IOException i) {
                            System.err.println(i.getMessage());
                        }
                    }
                });
                pSp.add(pSb2);

                //pSsurvey is the button for opening the SurveyMonkey survey.
                JLabel pSsl = new JLabel("Then take our brief ");
                JButton pSsurvey = new JButton("Survey.");
                pSsurvey.setToolTipText("Opens brief survey");
                pSsurvey.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String url = "https://www.surveymonkey.com/s/8K7YR2C";
                        try {
                            BrowserLauncher.openURL(url);
                        } catch (IOException i) {
                            System.err.println(i.getMessage());
                        }
                    }
                });
                pSp.add(pSsl);
                pSp.add(pSsurvey);

                pSf.setVisible(true);
                pSf.setSize(350, 125);
                pSf.add(pSp);
            }
        });

        body.add(probSheets);

        return body;
    }


    /**
     * Constructs and returns TailPanel containing contact and credit buttons.
     *
     * @return returns the tail panel
     */
    public JPanel mkTailPanel() {
        JPanel tail = new JPanel();

        //'contact' button opens a new frame (pop-up) containing buttons for review, source, and bug reporting.
        JButton contact = new JButton("Contact Us");
        contact.setToolTipText("Contact, review, or view source code");
        contact.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame("Contact Us");
                JPanel p = new JPanel();
                JLabel l = new JLabel("Ask a Question, Review the Software, View/Edit the Source");
                p.add(l);

                //'email' button opens a new frame (pop-up) with an editable TextArea containing a
                //a copy/paste-able email address.
                JButton emailB = new JButton("Send an Email");

                emailB.setToolTipText("Send email to us from within the program");
                emailB.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        //create instance of /EmailForm/ from |utilities|
                        JFrame emailFrame = new EmailForm();
                        emailFrame.setVisible(true);

                    }
                });

                p.add(emailB);

                //'review' uses /BrowserLauncher/ to open the page for a new, blank review on sourceforge
                JButton review = new JButton("Review JBF");
                review.setToolTipText("Opens review page on sourceforge");
                review.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String url = "https://sourceforge.net/projects/jbf/reviews/?source=navbar";
                        try {
                            BrowserLauncher.openURL(url);
                        } catch (IOException i) {
                            System.err.println(i.getMessage());
                        }
                    }
                });
                p.add(review);

                //'bug' is a currently inactive button I (aks5238) was thinking of making
                // for reporting problems with the software. ideally it would store the most recent
                // error logs in a .txt and contain a /JTextField/ (import) where a user could
                // briefly describe the issue. It would then bundle all of that into one format
                // and send it to jbioframework@gmail.com with a '-BUG-' flag in the subject  and
                // notify us that there was a message waiting

                //@todo implement
                JButton bug = new JButton("Bug Report");
                bug.setToolTipText("Report an issue with the software");
                bug.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null, "will copy information about program");
                    }
                });
                //                 p.add(bug);

                //'source' button uses /BrowserLauncher/ to open up the GIT page on sourceforge
                JButton source = new JButton("Source Code");
                source.setToolTipText("Link to available source code");
                source.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            BrowserLauncher.openURL("https://github.com/RITJBF/JBioFramework/tree/master/");
                        } catch (IOException i) {
                            System.err.println(i.getMessage());
                        }
                    }
                });
                p.add(source);

                f.setVisible(true);
                f.setSize(400, 100);
                f.add(p);
            }
        });

        tail.add(contact);

        //'credits' button opens a new frame (pop-up) containing formatted text for the citations
        JButton credits = new JButton("Credits");
        credits.setToolTipText("Citations");
        credits.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Credits");
                String mrvn = "<html> <u>Marvin</u> was used for drawing, displaying and " +
                        "characterizing chemical structures, substructures and reactions, " +
                        "Marvin 5.11.5, 2013, ChemAxon (http://www.chemaxon.com)</body>";
                String srch = "<html> The <u>Blast</u> [National Library of Medicine], " +
                        "<u>NCBI</u> [National Center for Biotechnology Information], and " +
                        "<u>Uniprot</u> [The Uniprot Consortium] " +
                        "search engines. </body>";
                String nbCE = "NetbeansIDE <u>ContactEditor was used for the creation of some GUIs.";
                String rt = "This software was developed at Rochester Institute of Technology (<u>RIT</u>). ";
                String html1 = "<html><body style='width: ";
                String html2 = "px'>";
                JLabel head = new JLabel("..............................................");
                JLabel tail = new JLabel("..............................................");
                JLabel marvin = new JLabel(html1 + "250" + html2 + '-' + mrvn);
                JLabel search = new JLabel(html1 + "250" + html2 + '-' + srch);
                JLabel rit = new JLabel(html1 + "250" + html2 + '-' + rt);
                JPanel panel = new JPanel();
                panel.add(head);
                panel.add(marvin);
                panel.add(search);
                panel.add(rit);
                panel.add(tail);
                frame.add(panel);
                frame.setVisible(true);
                frame.setSize(400, 250);
            }
        });
        tail.add(credits);

        return tail;

    }
}
