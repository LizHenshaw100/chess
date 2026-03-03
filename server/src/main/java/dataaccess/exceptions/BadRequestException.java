package dataaccess.exceptions;

public class BadRequestException extends DataAccessException {
    public BadRequestException() {
        super("Bad Request");
    }
}