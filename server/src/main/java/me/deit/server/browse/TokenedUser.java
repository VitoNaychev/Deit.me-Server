package me.deit.server.browse;

public class TokenedUser {
    private String token;
    private BrowseUser user;

    public TokenedUser(String token, BrowseUser user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public BrowseUser getUser() {
        return user;
    }

    public void setUser(BrowseUser user) {
        this.user = user;
    }
}
