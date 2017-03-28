package org.jbioframework.library.protein;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProteinList {

    private ArrayList<Protein> proteins;

    public ProteinList(ArrayList<Protein> proteins){
        this.proteins=proteins;
    }

    public ArrayList<Protein> getProteins() {
        return this.proteins;
    }
}
