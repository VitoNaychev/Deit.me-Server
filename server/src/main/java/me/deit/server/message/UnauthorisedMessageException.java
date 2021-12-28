package me.deit.server.message;

public class UnauthorisedMessageException extends Throwable {
    public UnauthorisedMessageException(String reason) {
        super(reason);
    }
}
