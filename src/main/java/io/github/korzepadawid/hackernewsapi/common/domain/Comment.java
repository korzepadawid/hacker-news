package io.github.korzepadawid.hackernewsapi.common.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    @NotBlank
    @Size(max = 250)
    private String text;

    @NotNull
    private Integer voteSum;

    @CreatedDate
    private LocalDateTime createdAt;

    @NotNull
    @DBRef
    private User author;

    @NotNull
    @DBRef(lazy = true)
    private Submission submission;

    public Comment() {
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

    public Integer getVoteSum() {
        return voteSum;
    }

    public void setVoteSum(final Integer voteSum) {
        this.voteSum = voteSum;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(final User author) {
        this.author = author;
    }

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(final Submission submission) {
        this.submission = submission;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        final Comment comment = (Comment) o;
        return Objects.equals(text, comment.text) && Objects.equals(voteSum, comment.voteSum) && Objects.equals(createdAt, comment.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, voteSum, createdAt);
    }
}
