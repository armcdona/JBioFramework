package main.java.Protein;

import org.biojava.nbio.aaproperties.PeptidePropertiesImpl;
import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.ProteinSequence;
import java.util.ArrayList;

public class Protein {

    private ProteinSequence proteinSequence;
    private double pI;
    private double molecularWeight;
    private String name;
    private String functions;
    private String inchiSequence;
    private ArrayList<AminoAcid> aminoAcids;

    public Protein (ProteinSequence sequence) {
        initalizeProtein(sequence.getSequenceAsString(), "", "", "");
    }

    public Protein(String sequence) {
        initalizeProtein(sequence, "", "", "");
    }

    public Protein (ProteinSequence sequence, String inchiSequence) {
        initalizeProtein(sequence.getSequenceAsString(), inchiSequence, "", "");
    }

    public Protein (String sequence, String inchiSequence) {
        initalizeProtein(sequence, inchiSequence, "", "");
    }

    public Protein (ProteinSequence sequence, String inchiSequence, String name, String functions) {
        initalizeProtein(sequence.getSequenceAsString(), inchiSequence, name, functions);
    }

    public Protein (String sequence, String inchiSequence, String name, String functions) {
        initalizeProtein(sequence, inchiSequence, name, functions);
    }

    public Protein (ProteinSequence sequence, String name, String functions) {
        initalizeProtein(sequence.getSequenceAsString(), "", name, functions);
    }

    public Protein (String sequence, String name, String functions) {
        initalizeProtein(sequence, "", name, functions);
    }

    private void initalizeProtein(String sequence, String inchiSequence, String name, String functions) {
        try {
            this.proteinSequence = new ProteinSequence(sequence);
        } catch (CompoundNotFoundException e) {
            e.printStackTrace();
        }
        this.inchiSequence = inchiSequence;
        this.name = name;
        this.functions = functions;
        calculateProperties();
        initializeAminoAcids();
    }

    private void initializeAminoAcids() {
        aminoAcids = new ArrayList<>();
        for (int i=0;i<proteinSequence.getSequenceAsString().length()-1;i++) {
            aminoAcids.add(new AminoAcid(proteinSequence.getSequenceAsString().charAt(i)));
        }
    }

    private void calculateProperties() {
        PeptidePropertiesImpl peptideProperties = new PeptidePropertiesImpl();
        pI = peptideProperties.getIsoelectricPoint(proteinSequence);
        molecularWeight = peptideProperties.getMolecularWeight(proteinSequence);
    }

    public void setName(String nameInput) {
        name = nameInput;
    }

    public void setFunctions(String functionsInput) {
        functions = functionsInput;
    }

    public void setInchiSequence(String inchiSequenceInput) {
        inchiSequence = inchiSequenceInput;
    }

    public void setMolecularWeight(double molecularWeightInput) {
        molecularWeight = molecularWeightInput;
    }

    public void setpI(double pIInput) {
        pI = pIInput;
    }

    public void setProteinSequence(ProteinSequence proteinSequenceInput) {
        proteinSequence = proteinSequenceInput;
        initializeAminoAcids();
    }

    public void setProteinSequence(String sequence) {
        try {
            proteinSequence = new ProteinSequence(sequence);
        } catch (CompoundNotFoundException e) {
            e.printStackTrace();
        }
        initializeAminoAcids();
    }

    public ProteinSequence getProteinSequence() {
        return proteinSequence;
    }

    public String getProteinSequenceAsString() {
        return proteinSequence.getSequenceAsString();
    }

    public double getpI() {
        return pI;
    }

    public double getMolecularWeight() {
        return molecularWeight;
    }

}