package main.java.Protein;

import org.freehep.graphics2d.font.CharTable;

public class AminoAcid {
    private String name;
    private double molecularWeight;
    private double pKaCOOH;
    private double pKaNH2;
    private double pKaSideChain;
    Character aminoAcid;

    public AminoAcid (Character aminoAcidInput) {
        aminoAcid = aminoAcidInput;
    }

}
