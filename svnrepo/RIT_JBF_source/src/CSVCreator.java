/**
 * CSVCreator ports the contents of a proteome file to a Comma Separated
 * Value file to be viewed in a spreadsheet.  It stores the protein name,
 * sequence, molecular weight, and pI value to the file.
 *
 * @author Jill Zapoticznyj
 */

import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;
import javax.swing.JOptionPane;

public class CSVCreator{

    //
    private Vector proteins;

    //saving the state for electro2D at time of object's creation.
    private Electro2D electro2D;

    //name of directory holding any/all CSV files.
    private final String directoryString = "CSV Files";

    public CSVCreator( Electro2D e ){
	electro2D = e;
	proteins = electro2D.getGel().getDots();
    }

    public void writeToCSV(){

	String filename = electro2D.getLastFileLoaded();
        if (filename.length() > 0) {
            PrintWriter out = null;
            try{
	        File fl = new File( directoryString );
                if( !fl.exists() ){
                    fl.mkdir();
                }
                out = new PrintWriter( new BufferedWriter( new FileWriter(
							   directoryString +
                                                           File.separator +
                                                           filename.substring(0,
                                                           filename.indexOf("."))
                                                           + ".csv" )));
            }catch( IOException e ){
	    System.err.println( "Error writing to CSV file" );
            }

            E2DProtein p = null;
            ProteinDot d = null;

            out.println( "Title" + "," + "Sequence" + "," + "Molecular Weight" +
		     "," + "pI Value" + "," + "X-Coordinate" + "," +
		     "Y-Coordinate" );

            if (proteins == null) {
                proteins = new Vector();
            }
            for( int i = 0; i < proteins.size(); i++ ){
                d = (ProteinDot)proteins.elementAt( i );
                p = (E2DProtein)d.getPro();

                out.println( "\"" + p.getID() + "\"" + "," + p.getSequence() + "," +
			 p.getMW() + "," + p.getPI() + "," +
			 d.returnX() + "," + d.returnY() );
            }

	    //alert user that the file has been created.
	    JOptionPane.showMessageDialog(null, "CSV created at " + directoryString + "/" + filename);

            try{
                out.close();
            }catch( Exception e ){
                System.err.println( "Error closing stream" );
            }
        }
    }
}
