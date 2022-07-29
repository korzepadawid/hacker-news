package io.github.korzepadawid.hackernewsapi.comment;

import io.github.korzepadawid.hackernewsapi.auth.CurrentUser;
import io.github.korzepadawid.hackernewsapi.common.projection.CommentRead;
import io.github.korzepadawid.hackernewsapi.common.projection.CommentWrite;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CommentController {

    private final CommentService commentService;

    CommentController(final CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/submissions/{submissionId}/comments")
    public CommentRead addCommentToSubmission(final @CurrentUser UserDetails userDetails,
                                              final @PathVariable String submissionId,
                                              final @RequestBody CommentWrite commentWrite) {
        return commentService.addCommentToSubmission(userDetails.getUsername(), submissionId, commentWrite);
    }
}
