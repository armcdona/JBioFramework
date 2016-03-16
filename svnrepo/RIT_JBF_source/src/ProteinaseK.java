/*
 * Proteinase K cuts a peptide sequence after (C-terminal side) Alanine(A),
 * Phenylalanine(F), Isoleucine(I), Leucine(L), Valine(V), Tryptophan(W),
 * and Tyrosine(Y).
 */

/**
 *
 * @author Amanda Fisher
 */
import java.util.ArrayList;

/**
 * Cuts proteins in a similar/same way that ProteinaseK does
 */
public class ProteinaseK extends Protease {


    /**
     * The cut method takes an input sequence and cuts it in to different Strings at points dependent on the type of Protease using the method.
     * It uses the makeIon method to turn the ArrayList of collected characters in to a String
     * @param sequence String sequence representing an amino acid chain
     * @return The sequence after it is cut
     * @throws ProteaseException Thrown when given an input with incorrect formatting

    public ArrayList<String> cut(String sequence) throws ProteaseException {
        if (sequence.contains(" ")) {
            throw new ProteaseException("Sequence to be cut must not contain spaces.");
        } else if (sequence.matches(".*\\d.*")) {
            throw new ProteaseException("Sequence to be cut must not contain numbers.");
        } else if (sequence.matches(".*[a-z].*")) {
            throw new ProteaseException("Sequence to be cut must contain all upper case letters.");
        }

        char[] charSequence = sequence.toCharArray();
        for(int i = 0; i < charSequence.length; i++) {
            buildingIons.add(charSequence[i]);
            if(charSequence[i] == 'A' || charSequence[i] == 'F'
                    || charSequence[i] == 'I' || charSequence[i] == 'L'
                    || charSequence[i] == 'V' || charSequence[i] == 'W'
                    || charSequence[i] == 'Y') {
                makeIon();
            }
        }
        if (!buildingIons.isEmpty()) {
            makeIon();
        }
        return cutSequence;
    } */

}