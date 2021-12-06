package me.deit.server.browse;

public class MatchResponse {
    boolean matched;

    public MatchResponse(boolean matched) {
        this.matched = matched;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }
}
