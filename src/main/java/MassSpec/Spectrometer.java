package main.java.MassSpec;/*
 * This class repsonds when a user wishes to run a sequence. After error checking
 * the sequence, MassSpec.Spectrometer retrieves the protease the user wants to cut with
 * and directs it to chop up the sequence. The sequences are then given to MassSpec.Converter
 * to be turned into instances of the MassSpec.Ion class. Each instance's mass charge
 * ratio and intensity is turned in to a two element array of doubles and given
 * to the outputGraph to be displayed to the user.
 *
 */

/**
 * @author Amanda Fisher
 */

import main.java.Electro2D.Protease;
import main.java.Electro2D.ProteaseException;
import main.java.Electro2D.ProteinaseK;

import java.util.ArrayList;

public class Spectrometer {

    /**
     * runAnalysis directs the simulation in evaluating the protein sequence.
     *
     * @param sequence       The protein sequence to be run.
     * @param outputGraph    The graph on which to display the results of                       evaluating the sequence.
     * @param proteaseChoice The user's choice of which protease they would like                       to digest their sequence with.
     */
    public static void runAnalysis(String sequence, OutputGraphGUI outputGraph,
                                   String proteaseChoice) {
        sequence = sequence.trim();
        sequence = sequence.toUpperCase();
        if (sequence.matches(".*\\d.*")) {
            System.out.println("Protein sequence must not contain digits.");
        } else {
            //Code to get rid of any whitespace in the sequence.
            String[] splitSequence = sequence.split("\\s");
            sequence = new String();
            for (String split : splitSequence) {
                sequence = sequence.concat(split);
            }

            //Figure out which protease the user wants.
            Protease protease;
            if (proteaseChoice.equals("MassSpec.Trypsin")) {
                protease = new Trypsin();
            } else if (proteaseChoice.equals("MassSpec.Chymotrypsin")) {
                protease = new Chymotrypsin();
            } else if (proteaseChoice.equals("Proteinase K")) {
                protease = new ProteinaseK();
            } else if (proteaseChoice.equals("MassSpec.Thermolysin")) {
                protease = new Thermolysin();
            } else {
                System.err.println("Did not recognize protease choice.");
                System.err.println("Defaulting to MassSpec.Trypsin selection.");
                protease = new Trypsin();
            }

            // Begin sequencing. Each fragment must have a charge of one and
            // only one.
            ArrayList<Ion> ions = new ArrayList<Ion>();
            try {
                ArrayList<String> ionStrings = protease.cut(sequence);
                for (String ion : ionStrings) {
                    Ion newIon = Converter.convert(ion);
                    newIon.setCharge(1);
                    newIon.setSequence(ion);
                    if (newIon.getMass() != 0) {
                        ions.add(newIon);
                    }
                }
            } catch (ProteaseException ex) {
                System.out.println(ex.getMessage());
            }

            //Count up the number of ions with the same mass charge rotios to
            //find intensity of each peak.
            int mostHits = 0;
            for (int i = 0; i < ions.size(); i++) {
                int hits = 0;
                for (int j = 0; j < ions.size(); j++) {
                    if (ions.get(i).getMassChargeRatio() == ions.get(j).getMassChargeRatio()) {
                        hits++;
                    }
                }
                ions.get(i).setHits(hits);
                if (mostHits < hits) {
                    mostHits = hits;
                }
            }

            // Have outputGraph draw the peaks.
            outputGraph.setPeaks(ions, mostHits);

        }

    }

}
