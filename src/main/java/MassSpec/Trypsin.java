package MassSpec; /**
 * @author Amanda Fisher
 */

import Electro2D.Protease;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Cuts proteins in a similar/same way that Trypsin does (after Arginine(R) or Lysine(K), except when either is followed by a Proline(P))
 */
public class Trypsin extends Protease {

    public Trypsin() {
        cutAminoAcids = new ArrayList<>(Arrays.asList('R', 'K')); //Chymotrypsin cuts at these proteins
        cutsBefore = false;
    }

    @Override
    public boolean shouldCutHere(Character currentAA, Character currentCutPoint, Character afterAA) {
        boolean shouldCut = false;
        if ((currentAA == currentCutPoint) && (afterAA != 'P')) { //only cuts if A or K are not followed by P
            shouldCut = true;
        }
        return shouldCut;
    }

    /**
     public ArrayList<String> cut(String sequence) throws ProteaseException {//Must override default method because Trypsin has an extra requirement
     ArrayList<String> cutSequence = new ArrayList<>();
     ArrayList<Character> buildingIons = new ArrayList<>();
     sequence = checkSequence(sequence);
     char[] charSequence = sequence.toCharArray();
     for (int i=0; i < charSequence.length; i++) {
     buildingIons.add(charSequence[i]);
     for (Character c : cutAminoAcids) {
     if ((charSequence[i] == c) && (i <  charSequence.length - 1) && (charSequence[i+1] != 'P')) {
     makeIon(buildingIons,cutSequence);
     }
     }
     }
     return cutSequence;
     } */

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
