package dataaccess.exceptions;

public class UnauthorizedException extends DataAccessException {
    public UnauthorizedException() {
        super("Unable to authenticate");
    }
}