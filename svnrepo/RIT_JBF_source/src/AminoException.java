/**
 * An exception thrown by Amino Acid when it is gives an incorrect input by one of its constructors
 * @author Amanda Fisher
 */
public class AminoException extends Exception {

    public AminoException() {}

    public AminoException(String message) {
        super(message);
    }
}
