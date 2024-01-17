package pap.helpers;

public class ConstraintViolationException extends Exception{
    /**
     * An exception class that is thrown when the data to be uploaded to / updated in the database is invalid.
     */
    public ConstraintViolationException(String message) {
        super(message);
    }
}
