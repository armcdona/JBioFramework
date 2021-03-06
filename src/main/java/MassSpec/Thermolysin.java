package main.java.MassSpec; /**
 * @author Amanda Fisher
 */

import main.java.Electro2D.Protease;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Cuts proteins in a similar/same way that Thermolysin does ( before Isoleucine(I), Leucine(L),
 * Methionine(M), or Valine(V))
 */
public class Thermolysin extends Protease {

    public Thermolysin() {
        cutAminoAcids = new ArrayList<>(Arrays.asList('I', 'L', 'M', 'V')); //Thermolysin cuts at these proteins
        cutsBefore = true;
    }

}
