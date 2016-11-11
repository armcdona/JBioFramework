package Protein;

import org.biojava.nbio.aaproperties.Constraints;

public class AminoAcid {
    private double molecularWeight;
    private double pKaCOOH;
    private double pKaNH2;
    private double pKaSideChain;
    Character aminoAcid;

    public AminoAcid (Character aminoAcidInput) {

        aminoAcid = aminoAcidInput;
    }

    private void determineProperties() {
        pKaCOOH = Constraints.aa2CTerminalPka.get(aminoAcid);
        pKaNH2 = Constraints.aa2NTerminalPka.get(aminoAcid);
        if (Constraints.aa2PKa.containsKey(aminoAcid)) {
            pKaSideChain = Constraints.aa2PKa.get(aminoAcid);
        } else {
            pKaSideChain = -1;
        }
        molecularWeight = Constraints.aa2MolecularWeight.get(aminoAcid);
    }

}
