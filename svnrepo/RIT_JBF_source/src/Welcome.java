/**
 *  - Welcome - @aidan[aks5238]
 *  # description (goal...in development):
 *  	-main function for "Welcome" tab.
 *  	-creates gui with working buttons (all code native), initializes rP and 
 *  	 calls Tutorial to fill it.
 *  	-contains links and info about JBF and its use
 *  # calls: 
 *  	-/BrowserLauncher/ to open webpage.
 *  	-/Tutorial/ to populate rightPanel (rP) of Welcome. 
 *  # called by: 
 *  	-/JBioFrameworkSwingVersion/ 
 *  # envelope: Welcome
 *  # package: Welcome
 *  |!| extends JFrame and therefore will not merge with JBF
 *  	^extends JPanel and 'super's changed to 'this'. -@aidan[aks5238]
 *  ! formating (layout and appearance) should directly match other simulations 
 *  ! buttons are not linked to any functionality in rightPanel (rP, /Tutorial/)
 *  ! code should be cleaned up after the former is resolved.
 */

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
 
public class Welcome extends JPanel{ 
	
	private JPanel lP; // button panel for general use and 
	private JPanel rP; // where the actual tutorial will be displayed
	
	/*Head panel*/
	private JPanel headP; //head panel with about and problem sheets?
	private JLabel head; // simple welcome text
	private JButton help; // opens up site -- will be outdated eventually %%
	private JButton about; //button 
//	private JButton aboutProc;

	/*Tutorial interface panel*/
	private JPanel tutPan; //can you stack panels like that? // next+previous, search, etc.
	private JPanel tutPanE2D; //panel for the E2D tutorial
	private JPanel tutPanMS; //panel for MassSpec tutorial 
	private JPanel tutPanIonex; //panel for Ionex tutorial
	
	private JLabel e2DtutHead;
	private JLabel MStutHead;
	private JLabel ionexTutHead;
	private JButton prev;
	private JButton next;
	private JTextField search;
	private JButton searchButton;
	
	/*Tutorial itself*/
	private Tutorial tutorial; //for calling /Tutorial/ class
	
	/*Tail panel*/
	private JPanel tailP; //tail panel with contact and credits
	private JButton credits; //
	private JButton contact; // displays email %%not copy-able
	private JButton probSheets; // will eventually 
//	private JFrame welcomeFrame;
	
	//////////////////////////////
	public Welcome(){
	//////////////////////////////
//		super();
		GridBagConstraints c = new GridBagConstraints();
//		GridBagConstraints cons = new GridBagConstraints();

		
		/*left panel*/
		lP = new JPanel(new GridBagLayout());
		lP.setBackground(Color.gray);
		//lP.getSize();

	    c.insets = new Insets(5, 5, 5, 5); //spacing between
//	    c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
//	    c.weighty = 1.0;
//	    c.insets = new Insets(<top>, <bottom>, <left>, <right>)
	    
	    this.add(lP,c);
	    
	    /*headP*/
	    headP = new JPanel();
	    headP.setBackground(Color.gray);

		head = new JLabel("Welcome to JBioFramework!");  //Header
//		c.gridx = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.gridy = 0;
//		headP.add(head,c);
		lP/*headP*/.add(head,c);
	    
		help = new JButton("Help");
		c.gridy = 1;
//		c.anchor = new GridBagConstraints.PAGE_START;
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				File f = new File( "HTML Files" + File.separator + "Help" + File.separator + "help.html" );
				try{
				    BrowserLauncher.openHTMLFile(f);
				} catch(IOException i){
			            System.err.println( i.getMessage());
			        }  
			}
		});
//		headP.add(help,c);
		lP/*headP*/.add(help,c);

		about = new JButton("About");
		c.anchor = GridBagConstraints.CENTER;
		c.gridy = 1;
		about.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				File f = new File( "HTML Files" + File.separator + "about.html" );
				try{
			            BrowserLauncher.openHTMLFile(f);
				}catch(IOException i){
			            System.err.println(i.getMessage());
			            i.printStackTrace();
			    }
			    
				JOptionPane.showMessageDialog(null, "JBioFramework (JBF) is a set of simulations of " +
						" different chemical separations applications frequently used in chemistry, biochemistry, and proteomics research.");

				
//				JOptionPane.showMessageDialog(null, "JBioFramework (JBF) is a set of simulations of " +
//						"three different chemical separations applications " +
//						"(2D electrophoresis, Mass Spectroscopy, ) " /n +
//						"that are frequently used in chemistry, biochemistry and proteomics research.");
			}
		});
		lP/*headP*/.add(about,c);
		
//	    lP.add(headP,c);

		
		/*tutPan*/
		prev = new JButton("Previous");
//		c.gridx = 1;
		c.gridy = 3;
		c.anchor = GridBagConstraints.CENTER;
//		tutPan.add(prev,c);
		lP/*tutPan*/.add(prev,c);
		
		next = new JButton("Next");
//		c.gridx = 1;
		c.gridy = 3;	
		c.anchor = GridBagConstraints.LINE_START;
	    c.insets = new Insets(5, 5, 5, 5); //spacing between
//	    tutPan.add(prev,c);
		lP/*tutPan*/.add(next,c);
		
		search = new JTextField("[Search Help Files]"); //exists currently as a text field
		c.gridy = 2;
//		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
//		tutPan.add(search,c);
		lP/*tutPan*/.add(search,c);
		
		searchButton = new JButton("Search");
//		c.gridx = 2;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_END;
		c.gridy = 2;
//		tutPan.add(searchButton,c);
		lP/*tutPan*/.add(searchButton,c);
		
//		lP.add(tutPan,c);
		
		
		/*tailP*/
		probSheets = new JButton("Problem Sheets");
//		c.gridx = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.gridy = 4;
		probSheets.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null, "opening problem sheets...");
				/**
				 * opens files we tack into the program much like we did with opening the
				 * gene bank files.
				 */
			}
		});
//		tailP.add(probSheets,c);
		lP/*tailP*/.add(probSheets,c);
	
		contact = new JButton("Contact Us");
		contact.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null,"paul.craig.rit.edu");
			}
		});
//		c.gridx = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.gridy = 5;
//		tailP.add(contact,c);
		lP/*tailP*/.add(contact,c);
		
		credits = new JButton("Credits");
		c.anchor = GridBagConstraints.CENTER;
		c.gridy = 6;
		credits.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(null, "matlab and intelliJ");
			}
		});
//		tailP.add(credits,c);
		lP/*tailP*/.add(credits,c);
		
//		lP.add(tailP,c);
 
		/*right panel*/
		rP = new JPanel();
		rP.setBackground(Color.darkGray);
//		rP.setLayout(new BoxLayout(rP, BoxLayout.Y_AXIS));
		rP.setLayout(new GridBagLayout());
		rP.setSize(900,700);
	    
	    c.gridy = 0;
//	    this.add(rP, c);
		
//		tutorial = new JLabel("JBioFramework is an application developed at RIT " +
//				"designed to help educate its users about various" +
//				"scientific procedures. To find out more, click the button below");
		tutorial = new Tutorial(); //JLabel("Main tutorial window");
		c.gridy = 0;
//		c.anchor = GridBagConstraints.CENTER;
		c.ipadx = 105;
		c.ipady = 205;
		rP.add(tutorial,c);
		
		
		/*add panel to frame*/
		this.add(lP);
		this.add(rP);
        this.setVisible(true);
//        this.setSize(550,500);
//        this.setResizable(true);
//        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
//		this.add(lP); //??
//		this.add(rP); //??
	}   
}