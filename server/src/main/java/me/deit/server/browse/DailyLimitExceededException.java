package me.deit.server.browse;

public class DailyLimitExceededException extends Throwable {
    private static final String message = "Daily limit exceeded";

    public DailyLimitExceededException() {
        super(message);
    }

    public DailyLimitExceededException(Throwable cause) {
        super(message, cause);
    }

    public DailyLimitExceededException(Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
