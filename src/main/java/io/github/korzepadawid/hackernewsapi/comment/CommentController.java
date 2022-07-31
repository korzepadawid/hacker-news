package io.github.korzepadawid.hackernewsapi.comment;

import io.github.korzepadawid.hackernewsapi.auth.CurrentUser;
import io.github.korzepadawid.hackernewsapi.common.projection.CommentRead;
import io.github.korzepadawid.hackernewsapi.common.projection.CommentWrite;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
class CommentController {

    private final CommentService commentService;

    CommentController(final CommentService commentService) {
        this.commentService = commentService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/submissions/{submissionId}/comments")
    public CommentRead addCommentToSubmission(final @CurrentUser @Parameter(hidden = true) UserDetails userDetails,
                                              final @PathVariable String submissionId,
                                              final @RequestBody @Valid CommentWrite commentWrite) {
        return commentService.addCommentToSubmission(userDetails.getUsername(), submissionId, commentWrite);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/comments/{commentId}")
    public void deleteCommentById(final @CurrentUser @Parameter(hidden = true) UserDetails userDetails,
                                  final @PathVariable String commentId) {
        commentService.deleteCommentById(userDetails.getUsername(), commentId);
    }
}
