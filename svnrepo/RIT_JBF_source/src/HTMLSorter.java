/**
 * This class creates the ordered Vector of all of the proteins to be
 * displayed in hte HTML file.
 */

import java.util.*;

/**
 * Creates a sorted TreeSet of the proteins
 */
public class HTMLSorter {

    private TreeSet proteinInfo;
    private int compBy;

    /**
     *
     * @param cb What to compare the proteins by
     * @param t  Name of the protein
     * @param p  the pI value of the protein
     * @param m  the molecular weight of the protein
     * @param f  the function of the protein
     */
    public HTMLSorter(int cb, Vector t, Vector p, Vector m, Vector f) {

        compBy = cb;
        proteinInfo = new TreeSet(new HTMLComparator(compBy));
        Vector tmp;

        for (int i = 0; i < t.size(); i++) {
            tmp = new Vector();
            tmp.add((String) t.elementAt(i));
            tmp.add((String) p.elementAt(i));
            tmp.add((String) m.elementAt(i));
            tmp.add((String) f.elementAt(i));
            proteinInfo.add(tmp);
        }
    }

    /**
     * Get sorted tree set.
     *
     * @return the tree set
     */
    public TreeSet getSorted() {
        return proteinInfo;
    }
}
