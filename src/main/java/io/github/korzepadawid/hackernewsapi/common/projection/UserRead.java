package io.github.korzepadawid.hackernewsapi.common.projection;

import io.github.korzepadawid.hackernewsapi.common.domain.User;

public class UserRead {

    private String id;
    private String username;
    private String email;
    private Boolean verified;
    private String avatarUrl;
    private Integer karma;

    public UserRead() {
    }

    public UserRead(final User user) {
        this.id = user.getId();
        this.username = user.getId();
        this.email = user.getEmail();
        this.verified = user.getVerified();
        this.karma = user.getKarmaPoints();
        this.avatarUrl = user.getAvatarStorageKey();
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(final Boolean verified) {
        this.verified = verified;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(final String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getKarma() {
        return karma;
    }

    public void setKarma(final Integer karma) {
        this.karma = karma;
    }
}
