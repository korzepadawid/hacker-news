package io.github.korzepadawid.hackernewsapi.comment;

import io.github.korzepadawid.hackernewsapi.common.domain.Comment;
import io.github.korzepadawid.hackernewsapi.common.domain.Submission;

import java.util.List;

public interface CommentRepository {

    void deleteAllBySubmission(final Submission submission);

    List<Comment> findCommentsBySubmissionOrderByCreatedAtDesc(final Submission submission);
}
