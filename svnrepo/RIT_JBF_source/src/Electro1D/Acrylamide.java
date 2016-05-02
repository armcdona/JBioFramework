package Electro1D;


/**
 * Acrylamide class
 *
 * @author Bader Alharbi
 * class is responsible for holding the Acclaimed gel parameters (the percentage and the numerical factor that affect the bands speed on)
 */

public class Acrylamide {
    /**
     * default parametrized  constructor that take the percentage & numerical factor
     *
     * @param s the Acrylamide gel concentration percentage
     * @param d numerical factor
     */
    Acrylamide(String s, double d) {
        percentGel = "0.0%";
        suppressor = 1;
        percentGel = s;
        concentration = d;
        setSuppressor(concentration);
    }

    /**
     * \
     * getConc() get the Acrylamide gel concentration
     *
     * @return concentration
     */
    public double getConc() {
        return concentration;
    }

    /**
     * set the numerical factor (suppressor)
     *
     * @param d concentration
     */
    public void setSuppressor(double d) {
        if (d > 12D) {
            suppressor = 6;
            return;
        }
        if (d > 10D) {
            suppressor = 3;
            return;
        }
        if (d > 7.5D) {
            suppressor = 2;
            return;
        } else {
            suppressor = 1;
            return;
        }
    }

    private double concentration; // gel concentration in double
    public String percentGel; // gel percentage
    public int suppressor; // numerical factor
}