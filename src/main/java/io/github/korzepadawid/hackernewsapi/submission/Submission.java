package io.github.korzepadawid.hackernewsapi.submission;

import io.github.korzepadawid.hackernewsapi.submission.comment.Comment;
import io.github.korzepadawid.hackernewsapi.submission.vote.Vote;
import io.github.korzepadawid.hackernewsapi.user.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Document(collection = "submissions")
public class Submission {

    @Id
    private String id;

    @NotBlank
    @Size(max = 60)
    private String title;

    @NotNull
    private Url url;

    @DBRef
    private User author;

    @NotNull
    private Integer voteSum;

    @CreatedDate
    private LocalDateTime createdAt;

    @DBRef(lazy = true)
    private Set<Comment> comments;

    @DBRef(lazy = true)
    private Set<Vote> votes;

    public Submission() {
        comments = new HashSet<>();
        votes = new HashSet<>();
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(final Set<Vote> votes) {
        this.votes = votes;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(final Url url) {
        this.url = url;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(final User author) {
        this.author = author;
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

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(final Set<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Submission)) return false;
        final Submission that = (Submission) o;
        return title.equals(that.title) && url.equals(that.url) && author.equals(that.author) && voteSum.equals(that.voteSum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url, author, voteSum);
    }
}
