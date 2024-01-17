package pap.helpers;

public class InvalidCredentialsException extends Exception{
    /**
     * An exception class that is thrown when the user / employee tries to log in with invalid credentials.
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
