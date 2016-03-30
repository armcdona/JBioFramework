/**
 *
 * @author Amanda Fisher
 */
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Cuts proteins in a similar/same way that ProteinaseK does (after Alanine(A), Phenylalanine(F), Isoleucine(I), Leucine(L), Valine(V), Tryptophan(W), and Tyrosine(Y))
 */
public class ProteinaseK extends Protease {


    public ProteinaseK() {
        cutAminoAcids = new ArrayList<>(Arrays.asList('A','F','I','L','V','W','Y')); //ProteinaseK cuts at these proteins
        cutsBefore = false;
    }

}