package io.github.korzepadawid.hackernewsapi.common.projection;

public class AuthDetails {

    private String bearerToken;

    public AuthDetails() {
    }

    public String getBearerToken() {
        return bearerToken;
    }

    public void setBearerToken(final String bearerToken) {
        this.bearerToken = bearerToken;
    }
}
