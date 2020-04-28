package src.files.Exceptions;

public class MissingParameterException extends Exception {
    public MissingParameterException(String message) {
        super("Missing parameter " + message);
    }
}
