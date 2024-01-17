package pap.helpers;

public class ConstraintViolationException extends Exception{
    /**
     * An exception class that is thrown when the data given by the user is invalid.
     */
    public ConstraintViolationException(String message) {
        super(message);
    }
}
