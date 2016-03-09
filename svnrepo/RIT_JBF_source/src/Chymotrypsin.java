/*
 * Chymotrypsin cuts a peptide sequence at Tyrosin(Y), Tryptophan(W), or
 * Phenylalanine(F).
 *
 */

/**
 *
 * @author Amanda Fisher
 */
import java.util.ArrayList;

/**
 * Cuts proteins in a similar/same way that Chymotrypsin does
 */
public class Chymotrypsin extends Protease {

    ArrayList<Character> buildingIons = new ArrayList<Character>();
    ArrayList<String> cutSequence = new ArrayList<String>();

    /**
     * The cut method takes an input sequence and cuts it in to different Strings at points dependent on the type of Protease using the method.
     * It uses the makeIon method to turn the ArrayList of collected characters in to a String
     * @param sequence String sequence representing an amino acid chain
     * @return The sequence after it is cut
     * @throws ProteaseException Thrown when given an input with incorrect formatting
     */
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
            if(charSequence[i] == 'Y' || charSequence[i] == 'W'
                    || charSequence[i] == 'F') {
                makeIon();
            }
        }
        makeIon();
        return cutSequence;
    }

    /**
     * Takes the characters collected by cut and turns them in to a string that represents the sequence of the Ion
     */
    private void makeIon() {
        Character[] characterIon = new Character[buildingIons.size()];
        characterIon = buildingIons.toArray(characterIon);
        char[] charIon = new char[characterIon.length];
        for(int j = 0; j < characterIon.length; j++) {
            charIon[j] = characterIon[j].charValue();
        }
        String ion = new String(charIon);
        cutSequence.add(ion);
        buildingIons.clear();
    }
}
