package dataaccess.exceptions;

public class UserTakenException extends DataAccessException {
    public UserTakenException() {
        super("Sorry, that username is already taken :(");
    }
}