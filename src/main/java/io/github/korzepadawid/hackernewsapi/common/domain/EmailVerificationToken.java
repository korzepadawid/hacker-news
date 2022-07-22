package io.github.korzepadawid.hackernewsapi.common.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Document(collection = "email_verification_tokens")
public class EmailVerificationToken {

    @Id
    private String id;

    @NotBlank
    @Size(max = 60)
    private String token;

    @Future
    @NotNull
    private LocalDateTime expiringAt;

    @DBRef
    private User user;

    public EmailVerificationToken() {
    }

    public EmailVerificationToken(String id, String token, LocalDateTime expiringAt, User user) {
        this.id = id;
        this.token = token;
        this.expiringAt = expiringAt;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public LocalDateTime getExpiringAt() {
        return expiringAt;
    }

    public void setExpiringAt(final LocalDateTime expiringAt) {
        this.expiringAt = expiringAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }
}
