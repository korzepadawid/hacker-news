package io.github.korzepadawid.hackernewsapi.comments;

import io.github.korzepadawid.hackernewsapi.common.domain.Comment;
import io.github.korzepadawid.hackernewsapi.common.domain.Submission;

import java.util.List;

interface CommentRepository {

    Comment save(Comment comment);

    List<Comment> findBySubmissionOrderByCreatedAtDesc(Submission submission);
}
