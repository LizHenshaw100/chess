package dataaccess.exceptions;

public class AlreadyTakenException extends DataAccessException {
    public AlreadyTakenException() {
        super("That color is already taken");
    }
}