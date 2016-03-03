/*
 * This class is an exception thrown by AminoAcid when an incorrect input is
 * given to one of its two constructors.
 * 
 * version 3
 */

/**
 * The type Amino exception.
 *
 * @author Amanda Fisher
 */
public class AminoException extends Exception {
    /**
     * Instantiates a new Amino exception.
     */
    public AminoException() {}

    /**
     * Instantiates a new Amino exception.
     *
     * @param message the message
     */
    public AminoException(String message) {
        super(message);
    }
}
