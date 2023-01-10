package io.github.korzepadawid.hackernewsapi.submission.vote;

import io.github.korzepadawid.hackernewsapi.submission.Submission;
import io.github.korzepadawid.hackernewsapi.user.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Document(collection = "votes")
public class Vote {

    @Id
    private String id;

    @DBRef(lazy = true)
    @NotNull
    private User author;

    @DBRef(lazy = true)
    @NotNull
    private Submission submission;

    @NotNull
    private VoteType voteType;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public Vote() {
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(final LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(final VoteType voteType) {
        this.voteType = voteType;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote)) return false;
        final Vote vote = (Vote) o;
        return author.equals(vote.author) && submission.equals(vote.submission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, submission);
    }
}
