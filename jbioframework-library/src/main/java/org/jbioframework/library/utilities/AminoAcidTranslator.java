package org.jbioframework.library.utilities;
// This class translates the given sequence letter names of the protein into the full names so it can be used in Marvin Sketch

public class AminoAcidTranslator {

    static public String translate(String input) {
        String output = "";
        /**
         * creates a switch case in which it looks at the output and than
         * will output the names of the amino acids depending on which one letter
         * code they fulfill. To account for marvin, if the acid is not the last in
         * the sequence, it will change to have a -yl at the end
         */
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            switch (c) {

                case 'A':
                    if (i == input.length() - 1) {
                        output += "Alanine";
                    } else {
                        output += "Alanyl-";
                    }
                    break;
                case 'G':
                    if (i == input.length() - 1) {
                        output += "Glycine";
                    } else {
                        output += "Glycyl-";
                    }
                    break;
                case 'V':
                    if (i == input.length() - 1) {
                        output += "Valanine";
                    } else {
                        output += "Valinyl-";
                    }
                    break;
                case 'L':
                    if (i == input.length() - 1) {
                        output += "Leucine";
                    } else {
                        output += "Leucinyl-";
                    }
                    break;
                case 'I':
                    if (i == input.length() - 1) {
                        output += "Isoleucine";
                    } else {
                        output += "Isoleucinyl-";
                    }
                    break;
                case 'M':
                    if (i == input.length() - 1) {
                        output += "Methionine";
                    } else {
                        output += "Methionyl-";
                    }
                    break;
                case 'F':
                    if (i == input.length() - 1) {
                        output += "Phenylalanine";
                    } else {
                        output += "Phenylalaninyl-";
                    }
                    break;
                case 'W':
                    if (i == input.length() - 1) {
                        output += "Tryptophan";
                    } else {
                        output += "Tryptophanyl-";
                    }
                    break;
                case 'S':
                    if (i == input.length() - 1) {
                        output += "Serine";
                    } else {
                        output += "Serinyl-";
                    }
                    break;
                case 'T':
                    if (i == input.length() - 1) {
                        output += "Threonine";
                    } else {
                        output += "Threonyl-";
                    }
                    break;
                case 'C':
                    if (i == input.length() - 1) {
                        output += "Cysteine";
                    } else {
                        output += "Cysteinyl-";
                    }
                    break;
                case 'Y':
                    if (i == input.length() - 1) {
                        output += "Tyrosine";
                    } else {
                        output += "Tyrosinyl-";
                    }
                    break;
                case 'N':
                    if (i == input.length() - 1) {
                        output += "Asparagine";
                    } else {
                        output += "Asparaginyl-";
                    }
                    break;
                case 'Q':
                    if (i == input.length() - 1) {
                        output += "Glutamine";
                    } else {
                        output += "Glutaminyl-";
                    }
                    break;
                case 'D':
                    if (i == input.length() - 1) {
                        output += "Aspartic Acid";
                    } else {
                        output += "Aspartyl-";
                    }
                    break;
                case 'E':
                    if (i == input.length() - 1) {
                        output += "Glutamic Acid";
                    } else {
                        output += "Glutamyl-";
                    }
                    break;
                case 'K':
                    if (i == input.length() - 1) {
                        output += "Lysine";
                    } else {
                        output += "Lysinyl-";
                    }
                    break;
                case 'R':
                    if (i == input.length() - 1) {
                        output += "Arginine";
                    } else {
                        output += "Arginyl-";
                    }
                    break;
                case 'H':
                    if (i == input.length() - 1) {
                        output += "Histidine";
                    } else {
                        output += "Histidinyl-";
                    }
                    break;
            }
        }
        return output;

    }

}

