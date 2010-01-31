import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JPanel;

public class SearchProteinFunction implements MouseListener, ActionListener{
    
    private Vector dots1;
    private Vector dots2;
    private GelCanvas gel;
    private Electro2D electro2D;
    private Frame window;
    private SearchProteinFunctionButton search;
    private SearchProteinResetButton reset;
    private TextField searchTerm;
    private TextField excludeTerm;
    private Panel buttonPane;
    private Panel buttonLabelPane;
    private JPanel buttonSelectionPane;
    private Panel searchLabelPane;
    private Panel searchFieldPane;
    private Panel textPane;
    private Label proteinTitleLabel;
    private Label aaSequenceLabel;
    private Label proteinFunctionLabel;
    private Label includesLabel;
    private Label excludesLabel;
    private String searchField;
    private static Color background = Color.BLACK;//new Color( 202, 225, 255 );
    private ButtonGroup radioButtons;
    private JRadioButton sequenceButton;
    private JRadioButton titleButton;
    private JRadioButton functionButton;

    public SearchProteinFunction( Electro2D e2D ){
	
	electro2D = e2D;
	radioButtons = new ButtonGroup();
	
	gel = electro2D.getGel();
	dots1 = new Vector();
	dots2 = new Vector();
	excludesLabel = new Label( "Excludes: " );
	includesLabel = new Label( "Includes: " );
	excludesLabel.setForeground( Color.WHITE );
	includesLabel.setForeground( Color.WHITE );
	searchTerm = new TextField();
	excludeTerm = new TextField();
	buttonPane = new Panel();
	buttonLabelPane = new Panel();
	buttonSelectionPane = new JPanel();
	textPane = new Panel();
	searchLabelPane = new Panel();
	searchFieldPane = new Panel();
	window = new Frame();
	window.addWindowListener( new WindowAdapter(){
		public void windowClosing( WindowEvent e ) {
					  window.hide();
				      }
				  }
				  );

	search = new SearchProteinFunctionButton( searchTerm, excludeTerm,
						  this );
	reset = new SearchProteinResetButton( this );
	sequenceButton = new JRadioButton( "Sequence" );
	titleButton = new JRadioButton( "Protein Title" );
	functionButton = new JRadioButton( "Protein Function" );
	sequenceButton.addActionListener( this );
	titleButton.addActionListener( this );
	functionButton.addActionListener( this );
	sequenceButton.setActionCommand( "sequence" );
	titleButton.setActionCommand( "title" );
	functionButton.setActionCommand( "function" );
	functionButton.setMnemonic( KeyEvent.VK_F );
	sequenceButton.setMnemonic( KeyEvent.VK_S );
	titleButton.setMnemonic( KeyEvent.VK_T );
	titleButton.setBackground( background );
	titleButton.setForeground( Color.WHITE );
	sequenceButton.setBackground( background );
	sequenceButton.setForeground( Color.WHITE );
	functionButton.setBackground( background );
	functionButton.setForeground( Color.WHITE );
	radioButtons.add( sequenceButton );
	radioButtons.add( titleButton );
	radioButtons.add( functionButton );

	buttonLabelPane.setLayout( new GridLayout(0,1) );
	buttonLabelPane.setBounds( 0, 0, 200, 50 );
	buttonLabelPane.setBackground( background );
	Label labelPane = new Label( "Select Search Field" );
	labelPane.setForeground( Color.WHITE );
	buttonLabelPane.add( labelPane );
	buttonSelectionPane.setLayout( new GridLayout( 0, 1 ) );
	buttonSelectionPane.setBounds( 0, 0, 200, 150 );
	buttonSelectionPane.setBackground( background );
	buttonSelectionPane.add( titleButton );
	buttonSelectionPane.add( functionButton );
	buttonSelectionPane.add( sequenceButton );
	
	buttonPane.setLayout( new BorderLayout() );
	buttonPane.setBounds( 0, 0, window.getWidth()/2, window.getHeight() );
	buttonPane.add( buttonLabelPane, BorderLayout.NORTH );
	buttonPane.add( buttonSelectionPane, BorderLayout.CENTER );
	
	includesLabel.setBounds( 25, 12, 75, 20 );
	searchTerm.setBounds( 40, 33, 100, 20 );
	excludesLabel.setBounds( 25, 52, 75, 20 );
	excludeTerm.setBounds( 40, 72, 100, 20 );
	search.setBounds( 30, 100, 50, 20 );
	reset.setBounds( 100, 100, 50, 20 );
	searchLabelPane.setLayout( new FlowLayout() );
	searchLabelPane.setBounds( 0, 0, 200, 50 );
	searchLabelPane.setBackground( background );
	Label searchLabel = new Label( "Enter Search Term" );
	searchLabel.setForeground( Color.WHITE );
	searchLabelPane.add( searchLabel );
	searchFieldPane.setLayout( null );
	searchFieldPane.setBounds( 0, 0, 200, 150 );
	searchFieldPane.setBackground( background );
	searchFieldPane.add( includesLabel );
	searchFieldPane.add( searchTerm );
	searchFieldPane.add( excludesLabel );
	searchFieldPane.add( excludeTerm );
	searchFieldPane.add( search );
	searchFieldPane.add( reset );
	
	textPane.setLayout( new BorderLayout() );
	textPane.setBounds( 0, 0, window.getWidth()/2, window.getHeight() );
	textPane.setBackground( new Color( 202, 225, 225 ) );
	textPane.add( searchLabelPane, BorderLayout.NORTH );
	textPane.add( searchFieldPane, BorderLayout.CENTER );
	

	window.setLayout( new BorderLayout() );
	window.setTitle( "Search Proteins" );
	window.add( buttonPane, BorderLayout.WEST );
	window.add( textPane, BorderLayout.EAST );
	window.setBounds( 0, 0, 400, 300 );
	window.pack();
	window.setResizable( false );
	window.setVisible( true );
	
    }
    public void searchFor( String fcnName, String limitations ){
	dots1 = gel.getDots();
	dots2 = gel.getDots2();

	ProteinDot prot = null;
	if( /*function.getSelected()*/ searchField.equals( "function" ) ){
	    for( int i = 0; i < dots1.size(); i++ ){
		prot = (ProteinDot)dots1.elementAt( i );
		if( ((E2DProtein)prot.getPro()).getFunction().indexOf( fcnName )
		    == -1 ){
		    prot.doNotShowMe();
		}
		else if( ((E2DProtein)prot.getPro()).getFunction().indexOf(
			limitations ) != -1 && !limitations.equals( "" ) ){
		    prot.doNotShowMe();
		}
	    }
	    if( dots2.size() != 0 ){
		for( int j = 0; j < dots2.size(); j++ ){
		    prot = (ProteinDot)dots2.elementAt( j );
		    if( ((E2DProtein)prot.getPro()).getFunction().indexOf(
				      		       fcnName ) == -1 ){
			prot.doNotShowMe();
		    }
		    else if(((E2DProtein)prot.getPro()).getFunction().indexOf(
			    limitations ) != -1 && !limitations.equals( "" ) ){
			prot.doNotShowMe();
		    }
		}
	    }
	}
	else if(/*equence.getSelected()*/searchField.equals( "sequence" )){
	    for( int i = 0; i < dots1.size(); i++ ){
		prot = (ProteinDot)dots1.elementAt( i );
		if(((E2DProtein)prot.getPro()).getSequence().indexOf( fcnName )
		   == -1 ){
		    prot.doNotShowMe();
		}
	    }
	    if( dots2.size() != 0 ){
		for( int j = 0; j < dots2.size(); j++ ){
		    if(((E2DProtein)prot.getPro()).getSequence().indexOf( fcnName)
		       == -1 ){
			prot.doNotShowMe();
		    }
		}
	    }
	}
	else if(/*itle.getSelected()*/searchField.equals( "title" ) ){
	    for( int i = 0; i < dots1.size(); i++ ){
		prot = (ProteinDot)dots1.elementAt( i );
		if(((E2DProtein)prot.getPro()).getID().indexOf( fcnName ) 
		   == -1 ){
		    prot.doNotShowMe();
		}
		else if(((E2DProtein)prot.getPro()).getID().indexOf( 
			   limitations ) != -1 && !limitations.equals("" ) ){
		    prot.doNotShowMe();
		}
	    }
	    if( dots2.size() != 0 ){
		for( int j = 0; j < dots2.size(); j++ ){
		    prot = (ProteinDot)dots2.elementAt( j );
		    if(((E2DProtein)prot.getPro()).getID().indexOf( fcnName)
		       == -1 ){
			prot.doNotShowMe();
		    }
		    else if(((E2DProtein)prot.getPro()).getID().indexOf(
			   limitations ) != -1 && !limitations.equals( "" ) ){
			prot.doNotShowMe();
		    }
		}
	    }
	}
	gel.repaint();
    }
    
    public void displayAll(){
	dots1 = gel.getDots();
	dots2 = gel.getDots2();
	for( int i = 0; i < dots1.size(); i++ ){
	    ((ProteinDot)dots1.elementAt( i )).doShowMe();
	}
	if( dots2.size() != 0 ){
	    for( int j = 0; j < dots2.size(); j++ ){
		((ProteinDot)dots2.elementAt( j )).doShowMe();
	    }
	}
	gel.repaint();
    }
    
    public void hideExcludeFields(){
	//if( !title.getSelected() && !function.getSelected() ){
	    searchFieldPane.remove( excludesLabel );
	    searchFieldPane.remove( excludeTerm );
	    window.validate();
	    //}
    }
    
    public void actionPerformed( ActionEvent e ){
	searchField = e.getActionCommand();
	
    }

    public void mouseClicked( MouseEvent e ){
	//if( title.getSelected() || function.getSelected() ){
	    searchFieldPane.add( excludesLabel );
	    searchFieldPane.add( excludeTerm );
	    window.validate();
	    //}
    }
    public void mousePressed( MouseEvent e ){}
    public void mouseReleased( MouseEvent e ){}
    public void mouseEntered( MouseEvent e ){}
    public void mouseExited( MouseEvent e ){}

    private class HideExclude implements MouseListener {
	
	private SearchProteinFunction spf;

	public HideExclude( SearchProteinFunction s ){
	    spf = s;
	}
	
	public void mouseClicked( MouseEvent e ){
	    spf.hideExcludeFields();
	}
	public void mousePressed( MouseEvent e ){}
	public void mouseReleased( MouseEvent e ){}
	public void mouseEntered( MouseEvent e ){}
	public void mouseExited( MouseEvent e ){}
    
    }

}






