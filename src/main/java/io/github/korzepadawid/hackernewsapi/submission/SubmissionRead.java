package io.github.korzepadawid.hackernewsapi.submission;

import io.github.korzepadawid.hackernewsapi.user.UserRead;

import java.time.LocalDateTime;

public class SubmissionRead {

    private String id;
    private Url site;
    private String title;
    private LocalDateTime createdAt;
    private Integer votesSum;
    private UserRead author;

    public SubmissionRead() {
    }

    public SubmissionRead(final Submission savedSubmission) {
        this.id = savedSubmission.getId();
        this.title = savedSubmission.getTitle();
        this.site = savedSubmission.getUrl();
        this.createdAt = savedSubmission.getCreatedAt();
        this.votesSum = savedSubmission.getVoteSum();
        this.author = new UserRead(savedSubmission.getAuthor());
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

    public Url getSite() {
        return site;
    }

    public void setSite(final Url site) {
        this.site = site;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getVotesSum() {
        return votesSum;
    }

    public void setVotesSum(final Integer votesSum) {
        this.votesSum = votesSum;
    }

    public UserRead getAuthor() {
        return author;
    }

    public void setAuthor(final UserRead author) {
        this.author = author;
    }
}
