package org.jbioframework.electro2d;/*
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
 * This class compares the information to be displayed in the generated
 * HTML document.
 */

import java.util.Vector;
import java.util.Comparator;

public class HTMLComparator implements Comparator {

    private int compBy;

    public HTMLComparator(int c) {
        compBy = c;
    }

    public int compare(Object a, Object b) {

        Vector objA = (Vector) a;
        Vector objB = (Vector) b;
        if (compBy != 1 && compBy != 2) {
            if (((String) objA.elementAt(compBy)).equals(
                    (String) objB.elementAt(compBy))) {
                for (int i = 0; i < 4; i++) {
                    if (i != 1 && i != 2) {
                        int comp = ((String) objA.elementAt(i)).compareTo(
                                ((String) objB.elementAt(i)));
                        if (comp != 0) {
                            return comp;
                        }
                    } else if (i == 1 || i == 2) {
                        int comp = compareDouble(a, b, i);
                        if (comp != 0) {
                            return comp;
                        }
                    }
                }

            } else {
                return (((String) objA.elementAt(compBy)).compareTo(
                        (String) objB.elementAt(compBy)));
            }

        } else if (compBy == 1 || compBy == 2) {
            if (compareDouble(a, b, compBy) == 0) {
                for (int i = 0; i < 4; i++) {
                    if (i != 1 && i != 2) {

                        int comp = ((String) objA.elementAt(i)).compareTo(
                                ((String) objB.elementAt(i)));

                        if (comp != 0) {
                            return comp;
                        }
                    } else if (i == 1 || i == 2) {

                        int comp = compareDouble(a, b, i);
                        if (comp != 0) {
                            return comp;
                        }
                    }
                }

                return 0;
            }
            return compareDouble(a, b, compBy);
        }

        return -1000;
    }

    public int compareDouble(Object a, Object b, int index) {
        Vector objA = (Vector) a;
        Vector objB = (Vector) b;
        int retVal = -2;

        double dA = Double.parseDouble(((String) objA.elementAt(index)));
        double dB = Double.parseDouble(((String) objB.elementAt(index)));

        if (dA < dB) {
            retVal = -1;
        } else if (dA == dB) {
            retVal = 0;
        } else if (dA > dB) {
            retVal = 1;
        }
        return retVal;
    }
}
