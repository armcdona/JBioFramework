/*
 * Protease is the abstract class inherited by all classes that are responsible for
 * cutting a protein's sequence at specific points. Only method they are required
 * to have is cut.
 *
 */

/**
 *
 * @author Amanda Fisher
 */
import java.util.ArrayList;

/**
 * The type Protease.
 */
public class Protease {

    private ArrayList<Character> buildingIons = new ArrayList<>();
    private ArrayList<String> cutSequence = new ArrayList<>();
    public ArrayList<Character> cutAminoAcids = new ArrayList<>();

    /**
     * Cut array list.
     *
     * @param sequence the sequence
     * @return the array list
     * @throws ProteaseException the protease exception
     */
    public ArrayList<String> cut(String sequence) throws ProteaseException { //will fill in later
        sequence = checkSequence(sequence); //runs the sequence through the sequence checker method in order to detect any errors
        for (Character c : cutAminoAcids) {
            if (sequence.contains(c.toString())) {
                makeIon();
            }
        }
        return null;
    }

    public String checkSequence(String sequence) throws ProteaseException{
        if (sequence.contains(" ")) {
            throw new ProteaseException("Sequence to be cut must not contain spaces.");
        } else if (sequence.matches(".*\\d.*")) {
            throw new ProteaseException("Sequence to be cut must not contain numbers.");
        } else if (sequence.matches(".*[a-z].*")) {
            throw new ProteaseException("Sequence to be cut must contain all upper case letters.");
        }
        return sequence;
    }

    /**
     * Takes the characters collected by cut and turns them in to a string that represents the sequence of the Ion
     */
    public void makeIon() {
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
