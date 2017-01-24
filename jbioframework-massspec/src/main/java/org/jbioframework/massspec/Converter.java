package main.java.MassSpec;/*
 * This class takes a string that represents an ion and converts it in to an
 * instance of the MassSpec.Ion class by turning each character in the string in to
 * a MassSpec.SpecAminoAcid and adding it to an instance of the ion class. The string given
 * has already been cut by a protease.
 * 
 * version 3
 */

/**
 * Converts an amino acid sequence within a string into the class Ion containing that sequence
 *
 * @author Amanda Fisher
 */
public class Converter {

    /**
     * The convert method takes a String input that represents an amino acid chain
     * and returns a class Ion containing the amino acids the string aminoAcidSequence represented.
     *
     * @param aminoAcidSequence String contain the amino acid aminoAcidSequence
     * @return Ion class containing amino acids from aminoAcidSequence.
     */
    public static Ion convert(String aminoAcidSequence) {
        char[] aminoAcids = aminoAcidSequence.toCharArray();
        Ion ion = new Ion();
        for (char acid : aminoAcids) {
            try {
                SpecAminoAcid newAcid = new SpecAminoAcid(acid);
                ion.add(newAcid);
            } catch (AminoException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return ion;
    }
}
