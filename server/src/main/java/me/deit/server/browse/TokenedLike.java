package me.deit.server.browse;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Size;

public class TokenedLike {
    @Length(min = 16, max = 16)
    private String token;
    private boolean liked;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
