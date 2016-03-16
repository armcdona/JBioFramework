/**
 * This class compares two IEFProteins based on pI representation and
 * x values.
 *
 * @author Jill Zapoticznyj
 */

import java.util.*;

/**
 * Compares two proteins according to pI values and x-coordinates
 */
public class CompIEF implements Comparator {

    private double max;  //the maximum pH value for the IEF
    private double min;  //the minimum pH value for the IEF

    /**
     * Creates the class to compare two IEFProtein Objects with the pH range
     *
     * @param inputMax the maximum pH
     * @param inputMin the minimum pH
     */
    public CompIEF(double inputMax, double inputMin) {
        max = inputMax;
        min = inputMin;
    }

    /**
     * Compares two IEFProtein objects by their pI values and x-coordinate.
     *
     * @param obj1 the first IEFProtein object
     * @param obj2 the second IEFProtein object
     * @return Returns -1 if second object is greater than the first, 0 if they are equal, and 1 is the first object is greater than the first
     */
    public int compare(Object obj1, Object obj2) {

        // cast the objects to IEFProteins
        IEFProtein IEFPro1 = (IEFProtein) obj1;
        IEFProtein IEFPro2 = (IEFProtein) obj2;
        int returnValue = -1;
        // the width of an IEFProtein bar
        double width = IEFProtein.returnWidth();
        // the range each IEFProtein represents
        double range = 1 / (max - min);
        // get the objects' pI values and x coordinates
        double minpI1 = IEFPro1.getMinPI();
        double minpI2 = IEFPro2.getMinPI();
        double maxpI1 = IEFPro1.getMaxPI();
        double maxpI2 = IEFPro2.getMaxPI();

        int x1 = IEFPro1.returnX();
        int x2 = IEFPro2.returnX();

        // if the pI values are both greater than the max value for the range,
        // they are equal
        if ((minpI1 >= max) && (minpI2 >= max)) {
            returnValue = 0;
        }
        // if the pI values are both less than the min value for the range,
        // they are equal
        else if ((minpI1 <= min) && (minpI2 <= min)) {
            returnValue = 0;
        }
        //if the x coordinates are within 1/2 the width of a bar of eachother..
        else if ((x1 + ((3 * width) / 4) >= x2) && (x1 - ((3 * width) / 4) <= x2)) {

            //..and the pI values are within range of eachother, they are equal
            if ((minpI2 <= minpI1 + range) && (minpI2 >= minpI1)) {
                returnValue = 0;
            } else if ((maxpI2 >= maxpI1 - range) && (maxpI2 <= maxpI1)) {
                returnValue = 0;
            }

        }
        // if the x coordinate of the first IEFProtein is greater than that of
        // the second, the first IEFProtein is greater.
        else if (x1 > x2) {
            returnValue = 1;
        }

        //else, the second was greater and returnValue still equals -1.

        return returnValue;
    }

}