
/**
 * Exception thrown when a given line in a line graph isn't recognized
 *
 * @author Kyle Dewey
 */
public class UnknownLineException extends Exception {
    /**
     * Instantiates a new Unknown line exception.
     */
    public UnknownLineException() {
    }

    /**
     * Instantiates a new Unknown line exception.
     *
     * @param message the message
     */
    public UnknownLineException(String message) {
        super(message);
    }
}
