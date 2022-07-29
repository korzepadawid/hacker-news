package io.github.korzepadawid.hackernewsapi.comment;

import io.github.korzepadawid.hackernewsapi.common.domain.Comment;
import io.github.korzepadawid.hackernewsapi.common.domain.Submission;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    void deleteAllBySubmission(final Submission submission);

    Comment save(Comment comment);

    Optional<Comment> findById(String id);

    void delete(Comment comment);

    List<Comment> findCommentsBySubmissionOrderByCreatedAtDesc(final Submission submission);
}
