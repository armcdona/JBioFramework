/**
 * Welcome Tab
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Welcome extends JPanel{
    JLabel headLink = new JLabel();

    public Welcome(){

        super(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

	    /*head Panel*/
        JPanel headP = new JPanel();
        JLabel head = new JLabel("Welcome to JBioframework!");
        head.setFont(new Font("SansSerif", Font.BOLD, 26));
        c.gridy = 0;
        headP.add(head);
        super.add(headP, c);

        JPanel subHeadP = new JPanel();
        String s = "<html>JBioFramework (JBF) is a set of chemical separations simulations frequently used in " +
                "chemistry, biochemistry and proteomics research. It is written in the Java programming language and will " +
                "run on any and all systems that have the JVM installed. A copy of the source code is available "+
                "online on SourceForge, or by clicking 'Contact Us' > 'Source Code'. Click on one of the tabs  "+
                "in the upper left to get started."; //@todo: review opening message
        String html1 = "<html><body style='width: ";
        String html2 = "px'>";
        JLabel head1 = new JLabel(html1+"500"+html2+s);
        String s2 = "<html><a href=https://sourceforge.net/projects/jbf/>here</a>.</body>";
        headLink = new JLabel(s2);
        headLink.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                String url1 = "https://sourceforge.net/projects/jbf/";
                try{
                    BrowserLauncher.openURL(url1);
                }catch(IOException i){
                    System.err.println( i.getMessage());}
            }
            public void mouseReleased(MouseEvent arg0) {}
            public void mousePressed(MouseEvent arg0) {}
            public void mouseExited(MouseEvent arg0) {
                headLink.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            public void mouseEntered(MouseEvent arg0) {
                headLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }});
        head1.setFont(new Font("SansSerif", Font.PLAIN, 14));
        c.gridy = 1;
        subHeadP.add(head1,c);
//        subHeadP.add(headLink);

        super.add(subHeadP,c);

        /*body panel*/
        JPanel body = new JPanel();

        JButton help = new JButton("Help");
        help.setToolTipText("Opens help files in browser");
        help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File f = new File( "HTML Files" + File.separator + "Help" + File.separator + "help.html" );
                try{
                    BrowserLauncher.openHTMLFile(f);
                } catch(IOException i){
                    System.err.println( i.getMessage());
                }
            }
        });
        c.gridy = 2;
        body.add(help,c);

        JButton about = new JButton("About");
        about.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                File f = new File( "HTML Files" + File.separator + "about.html" );
                try{
                    BrowserLauncher.openHTMLFile(f);
                }catch(IOException i){
                    System.err.println(i.getMessage());
                    i.printStackTrace();
                }
            }
        });
//        c.gridy = 2;
//        body.add(about,c);

        JButton prev = new JButton("Previous");
        c.gridy = 3;
//        body.add(prev,c);

        JButton next = new JButton("Next");
        c.gridy = 3;
//        body.add(next,c);

        JTextField search = new JTextField("[Search Help Files]"); //exists currently as a text field
        c.gridy = 2;
//        body.add(search,c);

        JButton searchButton = new JButton("Search");
        c.gridy = 2;

//        body.add(searchButton,c);
		
		/*tailP*/
        JButton probSheets = new JButton("Problem Sets"); //@todo: standardize file locations for all users
        probSheets.setToolTipText("Open included problem sets");
        probSheets.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                /**
                 * opens files we tack into the program much like we did with opening the
                 * gene bank files.
                 */
                JFrame pSf = new JFrame("Problem Sets");
                JPanel pSp = new JPanel();
                JLabel pSl = new JLabel("Choose a problem set to open [mouse over for details]. \n");
                pSp.add(pSl);

                JButton pSb1 = new JButton("Problem Set 1");
                pSb1.setToolTipText("High School and First Year College level");
                pSb1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String directory  = "C:\\Users\\Aidan\\Desktop\\"; //Research with Paul Craig\\mainstream code\\git repo\\svnrepo\\RIT_JBF_source\\src\\";// folder where word documents are present.
                        String fileName = "RevisedProblemSet1";
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            File f = new File( directory + fileName  +  ".rtf");
                            desktop.open(f);  // opens application (MSWord) associated with .doc file
                        }
                        catch(Exception ex) {
                            // WordDocument.this is to refer to outer class's instance from inner class
                            JOptionPane.showMessageDialog(null,"Error"); }
                    }});
                pSp.add(pSb1);

                JButton pSb2 = new JButton("Problem Set 2");
                pSb2.setToolTipText("Upper Division Bio and Chem Students");
                pSb2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String directory  = "C:\\Users\\Aidan\\Desktop\\"; //Research with Paul Craig\\mainstream code\\git repo\\svnrepo\\RIT_JBF_source\\src\\";// folder where word documents are present.
                        String fileName = "RevisedProblemSet2";
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            File f = new File( directory + fileName  +  ".rtf");
                            desktop.open(f);  // opens application (MSWord) associated with .doc file
                        }
                        catch(Exception ex) {
                            // WordDocument.this is to refer to outer class's instance from inner class
                            JOptionPane.showMessageDialog(null,"Error"); }
                    }});
                pSp.add(pSb2);
                pSf.setVisible(true);
                pSf.setSize(350,100);
                pSf.add(pSp);
            }});
        c.gridy = 3;
        body.add(probSheets,c);

        super.add(body, c);

        /*tail panel*/
        JPanel tail = new JPanel();
        JButton contact = new JButton("Contact Us");
        contact.setToolTipText("Contact, review, or view source code");
        contact.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JFrame f = new JFrame("Contact Us");
                JPanel p = new JPanel();
                JLabel l = new JLabel("Ask a Question, Review the Software, View/Edit the Source");
                p.add(l);
                JButton email = new JButton("Email Address");
                email.setToolTipText("Copy email address to clipboard");
                email.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String emailAddress = "paul.craig@rit.edu";
                        JFrame emailFrame = new JFrame("Email Address");
                        JPanel panel = new JPanel();
                        JLabel label = new JLabel("Please Copy/Paste the following: ");

                        JTextArea email = new JTextArea(emailAddress);

                        panel.add(label);
                        panel.add(email);
                        emailFrame.add(panel);
                        emailFrame.setSize(350,100);
                        emailFrame.setVisible(true);
                    }

                });
                p.add(email);

                JButton review = new JButton("Review JBF");
                review.setToolTipText("Opens review page on sourceforge");
                review.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String url = "https://sourceforge.net/projects/jbf/reviews/?source=navbar";
                        try{
                            BrowserLauncher.openURL(url);
                        } catch(IOException i){
                            System.err.println( i.getMessage());}
                    }});
                p.add(review);

                JButton bug = new JButton("Bug Report");
                bug.setToolTipText("Report an issue with the software");
                bug.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null, "will copy information about program");}});
//                p.add(bug);

                JButton source = new JButton("Source Code");
                source.setToolTipText("Link to available source code");
                source.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String url = "https://sourceforge.net/p/jbf/code/76/tree/";
                        try{
                            BrowserLauncher.openURL(url);
                        }catch (IOException i){
                            System.err.println( i.getMessage());}
                    }
                });
                p.add(source);

                f.setVisible(true);
                f.setSize(400,100);
                f.add(p);
            }
        });
        c.gridy = 4;
        tail.add(contact,c);

        JButton credits = new JButton("Credits");
        credits.setToolTipText("Citations");
        credits.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JFrame frame = new JFrame("Credits"); //@todo: update for marvin usage
                String mrvn = "<html> <u>Marvin</u> was used for drawing, displaying and " +
                        "characterizing chemical structures, substructures and reactions, " +
                        "Marvin 5.11.5, 2013, ChemAxon (http://www.chemaxon.com)</body>";
                String srch = "<html> The <u>Blast</u> [National Library of Medicine], "+
                        "<u>NCBI</u> [National Center for Biotechnology Information], and " +
                        "<u>Uniprot</u> [The Uniprot Consortium] " +
                        "search engines. </body>";
                String rt = "This software was developed at Rochester Institute of Technology (<u>RIT</u>). ";
                String html1 = "<html><body style='width: ";
                String html2 = "px'>";
                JLabel head = new JLabel("..............................................");
                JLabel tail = new JLabel("..............................................");
                JLabel marvin = new JLabel(html1+"250"+html2+'-'+mrvn);
                JLabel search = new JLabel(html1+"250"+html2+'-'+srch);
                JLabel rit = new JLabel(html1+"250"+html2+'-'+rt);
                JPanel panel = new JPanel();
                panel.add(head);
                panel.add(marvin);
                panel.add(search);
                panel.add(rit);
                panel.add(tail);
                frame.add(panel);
                frame.setVisible(true);
                frame.setSize(400,250);
            }
        });
        c.gridy = 5;
        tail.add(credits,c);

        this.add(tail,c);

    }
}