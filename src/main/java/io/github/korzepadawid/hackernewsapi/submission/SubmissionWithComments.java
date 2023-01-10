package io.github.korzepadawid.hackernewsapi.submission;

import io.github.korzepadawid.hackernewsapi.submission.comment.CommentRead;

import java.util.List;

public class SubmissionWithComments {

    private SubmissionRead submission;
    private List<CommentRead> comments;

    public SubmissionWithComments(final SubmissionRead submission, final List<CommentRead> comments) {
        this.submission = submission;
        this.comments = comments;
    }

    public SubmissionRead getSubmission() {
        return submission;
    }

    public void setSubmission(final SubmissionRead submission) {
        this.submission = submission;
    }

    public List<CommentRead> getComments() {
        return comments;
    }

    public void setComments(final List<CommentRead> comments) {
        this.comments = comments;
    }
}
