/*
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

/*
 * This class takes a string that represents an ion and converts it in to an
 * instance of the Ion class by turning each character in the string in to
 * a SpecAminoAcid and adding it to an instance of the ion class. The string given
 * has already been cut by a protease.
 * 
 * version 3
 */

/**
 *
 * @author Amanda Fisher
 */
public class Converter {

    /**
     * The convert method takes a String input representing an amino acid chain
     * and returns an Ion containing the amino acids the string sequence
     * represented.
     *
     * @param sequence Amino acid symbols for an ion.
     * @return Ion containing amino acids from sequence.
     */
    public static Ion convert(String sequence) {
        char[] aminoAcids = sequence.toCharArray();
        Ion ion = new Ion();
        for(char acid : aminoAcids) {
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
