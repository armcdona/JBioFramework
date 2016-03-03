/*
 * This class is an exception thrown by one of the Protease classes whenever
 * an incorrect input is given to the cut method.
 *
 * version 2
 */

/**
 * The type Protease exception.
 *
 * @author Amanda Fisher
 */
public class ProteaseException extends Exception {
    /**
     * Instantiates a new Protease exception.
     */
    public ProteaseException() {}

    /**
     * Instantiates a new Protease exception.
     *
     * @param message the message
     */
    public ProteaseException(String message) {
        super(message);
    }
}
