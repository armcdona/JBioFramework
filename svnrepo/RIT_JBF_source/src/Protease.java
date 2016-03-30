/**
 * @author Amanda Fisher
 */

import java.util.ArrayList;

/**
 * Contains the methods needed for a Protease who cuts before or after apecific proteins without any other conditions.  Otherwise the method cut needs to be overwritten and new conditions added
 */
public class Protease {

    public ArrayList<Character> cutAminoAcids = new ArrayList<>();
    public boolean cutsBefore;// does it cut before or after the proteins in cutAminoAcids

    /**
     * Cut array list.
     *
     * @param sequence the sequence
     * @return the array list
     * @throws ProteaseException the protease exception
     */
    public ArrayList<String> cut(String sequence) throws ProteaseException {
        ArrayList<Character> buildingIons = new ArrayList<>();
        ArrayList<String> cutSequence = new ArrayList<>();
        sequence = checkSequence(sequence); //runs the sequence through the sequence checker method in order to detect any errors
        for (Character s : sequence.toCharArray()) {
            //System.out.println("Checking character: " + s.toString() + "to " + cutAminoAcids.size());
            if (!cutsBefore) {
                buildingIons.add(s);
            }
            for (Character c : cutAminoAcids) { //should make a new method for determing if the protease should cut here so that more a complex protease only has to override that smaller method instead of the larger cut method
                if (s == c) {
                    makeIon(buildingIons,cutSequence);
                }
            }
            if (cutsBefore) {
                buildingIons.add(s);
            }
        }
        return cutSequence;
    }

    public String checkSequence(String sequence) throws ProteaseException {
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
    public void makeIon(ArrayList<Character> buildingIons,ArrayList<String> cutSequence) {
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
            ion += c.toString();
            System.out.println("Made Ion:" + ion);
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
