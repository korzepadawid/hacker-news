package io.github.korzepadawid.hackernewsapi.comment;

import io.github.korzepadawid.hackernewsapi.auth.CurrentUser;
import io.github.korzepadawid.hackernewsapi.common.projection.CommentRead;
import io.github.korzepadawid.hackernewsapi.common.projection.CommentWrite;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
class CommentController {

    private final CommentService commentService;

    CommentController(final CommentService commentService) {
        this.commentService = commentService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/submissions/{submissionId}/comments")
    public CommentRead addCommentToSubmission(final @CurrentUser UserDetails userDetails,
                                              final @PathVariable String submissionId,
                                              final @RequestBody CommentWrite commentWrite) {
        return commentService.addCommentToSubmission(userDetails.getUsername(), submissionId, commentWrite);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/comments/{commentId}")
    public void deleteCommentById(final @CurrentUser UserDetails userDetails,
                                  final @PathVariable String commentId) {
        commentService.deleteCommentById(userDetails.getUsername(), commentId);
    }
}
