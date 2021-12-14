package me.deit.server.browse;

public class NoUsersLeftException extends Throwable {
    private static final String message = "No users left to browse";

    public NoUsersLeftException() {
        super(message);
    }

    public NoUsersLeftException(Throwable cause) {
        super(message, cause);
    }

    public NoUsersLeftException(Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
