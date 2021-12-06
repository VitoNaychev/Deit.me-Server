package me.deit.server.browse;

public class NoSuchTokenException extends Throwable {
    public NoSuchTokenException() {
        super("No such token");
    }
}
