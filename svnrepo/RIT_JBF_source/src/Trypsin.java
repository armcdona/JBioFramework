/*
 * Trypsin cuts a protein sequence at Arginine(R) or Lysine(K), except when either is
 * followed by a Proline(P).
 *
 * version 2
 */

/**
 *
 * @author Amanda Fisher
 */
import java.util.ArrayList;

/**
 * Cuts proteins in a similar/same way that Trypsin does
 */
public class Trypsin extends Protease {

    /**
     * The cut method takes an input sequence and cuts it in to different Strings at points dependent on the type of Protease using the method.
     * It uses the makeIon method to turn the ArrayList of collected characters in to a String
     * @param sequence String sequence representing an amino acid chain
     * @return The sequence after it is cut
     * @throws ProteaseException Thrown when given an input with incorrect formatting

    public ArrayList<String> cut(String sequence) throws ProteaseException {


        char[] charSequence = sequence.toCharArray();
        for(int i = 0; i < charSequence.length; i++) {
            if(charSequence[i] == 'R' || charSequence[i] == 'K') {
                buildingIons.add(charSequence[i]);
                if(i < charSequence.length - 1 && charSequence[i+1] != 'P') {
                    makeIon();
                }
            } else {
                buildingIons.add(charSequence[i]);
            }
        }
        makeIon();
        return cutSequence;
    } */
}
