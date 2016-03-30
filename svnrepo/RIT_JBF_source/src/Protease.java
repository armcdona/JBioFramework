/*
 * Protease is the abstract class inherited by all classes that are responsible for
 * cutting a protein's sequence at specific points. Only method they are required
 * to have is cut.
 *
 */

/**
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
        for (Character s : sequence.toCharArray()) {
            buildingIons.add(s);
            for (Character c : cutAminoAcids) {
                System.out.println(c + " = " + s + " = " + (s == c));
                if (s == c) {
                    makeIon();
                }
            }
        }
        return cutSequence;
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
        /**
         Character[] characterIon = new Character[buildingIons.size()];//makes a character array from building Ions
         characterIon = buildingIons.toArray(characterIon);//sets it to buildingions
         Character[] charIon = new Character[characterIon.length];//new charion for the ion
         for(int j = 0; j < characterIon.length; j++) {
            charIon[j] = characterIon[j].charValue();
         }
         */
        String ion = new String();
        for (Character c : buildingIons) {
            ion+=c.toString();
        }
        //String ion = new String(charIon);
        System.out.println(ion);
        cutSequence.add(ion);
        buildingIons.clear();
    }
    /** example of the cut method
     char[] charSequence = sequence.toCharArray();
     for(int i = 0; i < charSequence.length; i++) {
        buildingIons.add(charSequence[i]);
        if(charSequence[i] == 'Y' || charSequence[i] == 'W' || charSequence[i] == 'F') {
            makeIon();
        }
     }
     makeIon();
     return cutSequence;
     */


}
