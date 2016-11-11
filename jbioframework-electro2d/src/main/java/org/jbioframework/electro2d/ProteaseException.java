package Electro2D;

/**
 * This class is an exception thrown by one of the Electro2D.Protease classes whenever
 * an incorrect input is given to the cut method.
 * <p>
 * |proteins|
 *
 * @author Amanda Fisher
 */
public class ProteaseException extends Exception {
    //
    public ProteaseException() {
    }

    public ProteaseException(String message) {
        super(message);
    }
}
