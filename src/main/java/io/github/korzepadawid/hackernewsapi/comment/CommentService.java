package io.github.korzepadawid.hackernewsapi.comment;

import io.github.korzepadawid.hackernewsapi.common.projection.CommentRead;
import io.github.korzepadawid.hackernewsapi.common.projection.CommentWrite;

interface CommentService {

    CommentRead addCommentToSubmission(String email, String submissionId, CommentWrite commentWrite);

    void deleteCommentById(String email, String commentId);
}
