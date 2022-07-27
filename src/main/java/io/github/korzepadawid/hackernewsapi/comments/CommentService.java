package io.github.korzepadawid.hackernewsapi.comments;

import io.github.korzepadawid.hackernewsapi.common.projection.CommentRead;
import io.github.korzepadawid.hackernewsapi.common.projection.CommentWrite;

interface CommentService {

    CommentRead addCommentToSubmission(String email, String submissionId, CommentWrite commentWrite);
}
