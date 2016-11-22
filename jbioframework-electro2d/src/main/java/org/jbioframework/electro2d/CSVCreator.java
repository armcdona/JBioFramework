package org.jbioframework.electro2d; /**
 * Electro2D.CSVCreator ports the contents of a proteome file to a Comma Separated
 * Value file to be viewed in a spreadsheet.  It stores the protein name,
 * sequence, molecular weight, and pI value to the file.
 *
 * @author Jill Zapoticznyj
 */

import javax.swing.*;
import java.io.*;
import java.util.Vector;

public class CSVCreator {

    //Vector of proteins to be written to the csv file
    private Vector proteins;

    //saving the state for electro2D at time of object's creation.
    private Electro2D electro2D;

    //name of directory holding any/all CSV files.
    private final String directoryString = "CSV Files";

    /**
     * Initializes the CSVCreator with the proteins contained in the references Electro2D simulations
     *
     * @param e Electro2D reference used to get the vector of proteins within the simulation.
     */
    public CSVCreator(Electro2D e) {
        electro2D = e;
        proteins = electro2D.getGel().getDots();
    }

    /**
     * Writes the protein vector to a csv file containing all of their information needed by the program.
     */
    public void writeToCSV() {

        String filename = electro2D.getLastFileLoaded();
        if (filename.length() > 0) {
            PrintWriter out = null;
            try {
                File fl = new File(directoryString);
                if (!fl.exists()) {
                    fl.mkdir();
                }
                out = new PrintWriter(new BufferedWriter(new FileWriter(
                        directoryString +
                                File.separator +
                                filename.substring(0,
                                        filename.indexOf("."))
                                + ".csv")));
            } catch (IOException e) {
                System.err.println("Error writing to CSV file");
            }

            E2DProtein p = null;
            ProteinDot d = null;

            out.println("Title" + "," + "Sequence" + "," + "Molecular Weight" +
                    "," + "pI Value" + "," + "X-Coordinate" + "," +
                    "Y-Coordinate");

            if (proteins == null) {
                proteins = new Vector();
            }
            for (int i = 0; i < proteins.size(); i++) {
                d = (ProteinDot) proteins.elementAt(i);
                p = (E2DProtein) d.getPro();

                out.println("\"" + p.getID() + "\"" + "," + p.getSequence() + "," +
                        p.getMW() + "," + p.getPI() + "," +
                        d.returnX() + "," + d.returnY());
            }

            //alert user that the file has been created.
            JOptionPane.showMessageDialog(null, "CSV created at " + directoryString + "/" + filename);

            try {
                out.close();
            } catch (Exception e) {
                System.err.println("Error closing stream");
            }
        }
    }
}
