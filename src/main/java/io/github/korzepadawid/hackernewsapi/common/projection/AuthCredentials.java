package io.github.korzepadawid.hackernewsapi.common.projection;

import javax.validation.constraints.NotNull;

public class AuthCredentials {

    @NotNull
    private String email;

    @NotNull
    private String password;

    public AuthCredentials() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
