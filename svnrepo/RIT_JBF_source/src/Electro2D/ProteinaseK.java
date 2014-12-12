package Electro2D;/*
 * Copyright (C) 2013 Rochester Institute of Technology
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU General Public License for more details.
 */

/**
 * Proteinase K is a /Electro2D.Protease/ object which "cuts" a sequence.
 * |proteins|
 * @author Amanda Fisher
 */

import java.util.ArrayList;

/**
 * Proteinase K cuts a peptide sequence after (C-terminal side) Alanine(A),
 * Phenylalanine(F), Isoleucine(I), Leucine(L), Valine(V), Tryptophan(W),
 * and Tyrosine(Y).
 */
public class ProteinaseK extends Protease {

    ArrayList<Character> buildingIons = new ArrayList<Character>();
    ArrayList<String> cutSequence = new ArrayList<String>();

    /**
     * The cut method takes an input sequence and cuts it into different Strings
     * at points dependent on the type of Electro2D.Protease using the method. It uses
     * the makeIon method to turn the ArrayList of collectd characters in to
     * a String.
     *
     * @param sequence String sequence representing an amino acid chain.
     * @return ArrayList of Strings, the cut sequence.
     * @throws ProteaseException When given inappropriate input.
     */
    public ArrayList<String> cut(String sequence) throws ProteaseException {
        if (sequence.contains(" ")) {
            throw new ProteaseException("Sequence to be cut must not contain spaces.");
        } else if (sequence.matches(".*\\d.*")) {
            throw new ProteaseException("Sequence to be cut must not contain numbers.");
        } else if (sequence.matches(".*[a-z].*")) {
            throw new ProteaseException("Sequence to be cut must contain all upper case letters.");
        }

        char[] charSequence = sequence.toCharArray();
        for(int i = 0; i < charSequence.length; i++) {
            buildingIons.add(charSequence[i]);
            if(charSequence[i] == 'A' || charSequence[i] == 'F'
                    || charSequence[i] == 'I' || charSequence[i] == 'L'
                    || charSequence[i] == 'V' || charSequence[i] == 'W'
                    || charSequence[i] == 'Y') {
                makeIon();
            }
        }
        if (!buildingIons.isEmpty()) {
            makeIon();
        }
        return cutSequence;
    }

    /**
     * makeIon takes the characters collected by cut and turns them in to a String
     * representing an MassSpec.Ion's sequence.
     */
    private void makeIon() {
        Character[] characterIon = new Character[buildingIons.size()];
        characterIon = buildingIons.toArray(characterIon);
        char[] charIon = new char[characterIon.length];
        for(int j = 0; j < characterIon.length; j++) {
            charIon[j] = characterIon[j].charValue();
        }
        String ion = new String(charIon);
        cutSequence.add(ion);
        buildingIons.clear();
    }
}
