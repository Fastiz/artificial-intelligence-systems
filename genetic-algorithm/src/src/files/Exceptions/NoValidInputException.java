package src.files.Exceptions;

public class NoValidInputException extends Exception {
    public NoValidInputException(String input) {
        super("Please select a valid " + input);
    }
}
