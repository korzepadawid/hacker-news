package io.github.korzepadawid.hackernewsapi.common.projection;

public class AuthDetails {

    private String bearerToken;

    public AuthDetails() {
    }

    public AuthDetails(final String bearerToken) {
        this.bearerToken = bearerToken;
    }

    public String getBearerToken() {
        return bearerToken;
    }

    public void setBearerToken(final String bearerToken) {
        this.bearerToken = bearerToken;
    }
}
