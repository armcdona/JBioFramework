package Electro2D; /**
 * @author Amanda Fisher
 */

import Electro2D.ProteaseException;

import java.util.ArrayList;

/**
 * Contains the methods needed for a Protease who cuts before or after apecific proteins without any other conditions.  Otherwise the method cut needs to be overwritten and new conditions added
 */
public class Protease {

    public ArrayList<Character> cutAminoAcids = new ArrayList<>();
    public boolean cutsBefore;// does it cut before or after the proteins in cutAminoAcids

    /**
     * Cut array list.
     *√
     * @param sequence the sequence
     * @return the array list
     * @throws ProteaseException the protease exception
     */
    public ArrayList<String> cut(String sequence) throws ProteaseException {
        ArrayList<Character> buildingIons = new ArrayList<>();
        ArrayList<String> cutSequence = new ArrayList<>();
        sequence = checkSequence(sequence); //runs the sequence through the sequence checker method in order to detect any errors
        int i = 0; //needed to send what the character after the currentAA is
        for (Character currentAA : sequence.toCharArray()) {

            if (!cutsBefore) {
                buildingIons.add(currentAA);
            }

            for (Character currentCutPoint : cutAminoAcids) { //should make a new method for determing if the protease should cut here so that more a complex protease only has to override that smaller method instead of the larger cut method

                Character afterAA = ' ';
                if ((i+1) <= (sequence.toCharArray().length - 1)) {
                    afterAA = sequence.toCharArray()[i+1];
                }
                if (shouldCutHere(currentAA,currentCutPoint,afterAA)){
                    makeIon(buildingIons,cutSequence);
                }

            }
            if (cutsBefore) {
                buildingIons.add(currentAA);
            }
            i++;
        }
        return cutSequence;
    }

    public boolean shouldCutHere(Character currentAA,Character currentCutPoint,Character afterAA) {
        boolean shouldCut = false;
        if (currentAA == currentCutPoint) {
            shouldCut = true;
        }
        return shouldCut;
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
     *
     * @param buildingIons The current ion
     * @param cutSequence  The sequence to be cut
     */
    public void makeIon(ArrayList<Character> buildingIons,ArrayList<String> cutSequence) {
        String ion = new String();
        for (Character c : buildingIons) {
            ion += c.toString();
        }
        cutSequence.add(ion);
        buildingIons.clear();
    }


}
