package io.github.korzepadawid.hackernewsapi.comment;

import io.github.korzepadawid.hackernewsapi.common.domain.Submission;

public interface CommentRepository {

    void deleteAllBySubmission(final Submission submission);
}
