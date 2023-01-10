package io.github.korzepadawid.hackernewsapi.submission.comment;

interface CommentService {

    CommentRead addCommentToSubmission(String email, String submissionId, CommentWrite commentWrite);

    void deleteCommentById(String email, String commentId);

    void updateCommentById(String email, String commentId, CommentWrite commentWrite);
}
