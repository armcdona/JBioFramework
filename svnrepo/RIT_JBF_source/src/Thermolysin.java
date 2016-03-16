/*
 * Thermolysin cuts before (N-terminal) Isoleucine(I), Leucine(L),
 * Methionine(M), or Valine(V).
 *
 */

/**
 *
 * @author Amanda Fisher
 */
import java.util.ArrayList;

/**
 * Cuts proteins in a similar/same way that Thermolysin does
 */
public class Thermolysin extends Protease {

    /**
     * The cut method takes an input sequence and cuts it in to different Strings at points dependent on the type of Protease using the method.
     * It uses the makeIon method to turn the ArrayList of collected characters in to a String
     * @param sequence String sequence representing an amino acid chain
     * @return The sequence after it is cut
     * @throws ProteaseException Thrown when given an input with incorrect formatting

    public ArrayList<String> cut(String sequence) throws ProteaseException {
        sequence = checkSequence(sequence); //runs the sequence through the sequence checker method in order to detect any errors

        char[] charSequence = sequence.toCharArray();
        for(int i = 0; i < charSequence.length; i++) {
            if(charSequence[i] == 'I' || charSequence[i] == 'L' || charSequence[i] == 'M' || charSequence[i] == 'V') {
                makeIon();
            }
            buildingIons.add(charSequence[i]);
        }
        makeIon();
        return cutSequence;
    } */

}
