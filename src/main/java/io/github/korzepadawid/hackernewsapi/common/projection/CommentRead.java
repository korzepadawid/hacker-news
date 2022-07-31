package io.github.korzepadawid.hackernewsapi.common.projection;

import io.github.korzepadawid.hackernewsapi.common.domain.Comment;

import java.time.LocalDateTime;

public class CommentRead {

    private String id;
    private String text;
    private LocalDateTime createdAt;
    private UserRead author;

    public CommentRead() {
    }

    public CommentRead(final Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.createdAt = comment.getCreatedAt();
        this.author = new UserRead(comment.getAuthor());
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserRead getAuthor() {
        return author;
    }

    public void setAuthor(final UserRead author) {
        this.author = author;
    }
}
