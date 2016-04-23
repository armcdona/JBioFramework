package MassSpec; /**
 *
 * @author Amanda Fisher
 */
import Electro2D.Protease;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Cuts proteins in a similar/same way that Chymotrypsin does (after Tyrosin(Y), Tryptophan(W), and Phenylalanine(F))
 */
public class Chymotrypsin extends Protease {

    public Chymotrypsin() {
        cutAminoAcids = new ArrayList<>(Arrays.asList('Y','W','F')); //Chymotrypsin cuts at these proteins
        cutsBefore = false;
    }

}
