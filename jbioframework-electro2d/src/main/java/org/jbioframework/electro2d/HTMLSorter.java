package org.jbioframework.electro2d;

/**
 * This class creates the ordered Vector of all of the proteins to be
 * displayed in hte HTML file.
 */

import org.jbioframework.electro2d.HTMLComparator;

import java.util.ArrayList;
import java.util.Vector;
import java.util.TreeSet;

public class HTMLSorter {

    private TreeSet proteinInfo;
    private int compBy;

    public HTMLSorter(int cb, ArrayList t, ArrayList p, ArrayList m, ArrayList f) {

        compBy = cb;
        proteinInfo = new TreeSet(new HTMLComparator(compBy));
        Vector tmp;

        for (int i = 0; i < t.size(); i++) {
            tmp = new Vector();
            tmp.add((String) t.get(i));
            tmp.add((String) p.get(i));
            tmp.add((String) m.get(i));
            tmp.add((String) f.get(i));
            proteinInfo.add(tmp);
        }
    }

    public TreeSet getSorted() {
        return proteinInfo;
    }
}
