package io.github.korzepadawid.hackernewsapi.submission.comment;

import io.github.korzepadawid.hackernewsapi.common.domain.Comment;
import io.github.korzepadawid.hackernewsapi.common.domain.Submission;
import io.github.korzepadawid.hackernewsapi.common.domain.User;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsError;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsException;
import io.github.korzepadawid.hackernewsapi.common.projection.CommentRead;
import io.github.korzepadawid.hackernewsapi.common.projection.CommentWrite;
import io.github.korzepadawid.hackernewsapi.submission.SubmissionService;
import io.github.korzepadawid.hackernewsapi.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public
class CommentServiceImpl implements CommentService {

    private final UserService userService;
    private final SubmissionService submissionService;
    private final CommentRepository commentRepository;

    CommentServiceImpl(final UserService userService,
                       final SubmissionService submissionService,
                       final CommentRepository commentRepository) {
        this.userService = userService;
        this.submissionService = submissionService;
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentRead addCommentToSubmission(final String email, final String submissionId, final CommentWrite commentWrite) {
        final User user = userService.findUserByEmail(email);
        final Submission submission = submissionService.findSubmissionById(submissionId);
        final Comment comment = mapDtoToEntity(commentWrite, user, submission);
        final Comment savedComment = commentRepository.save(comment);
        return new CommentRead(savedComment);
    }

    @Override
    public void deleteCommentById(final String email, final String commentId) {
        final Comment comment = findCommentAndValidate(email, commentId);
        commentRepository.delete(comment);
    }

    @Override
    public void updateCommentById(final String email, final String commentId, final CommentWrite commentWrite) {
        final Comment comment = findCommentAndValidate(email, commentId);

        if (StringUtils.hasText(commentWrite.getText())) {
            comment.setText(commentWrite.getText());
        }

        commentRepository.save(comment);
    }

    private Comment findCommentAndValidate(final String email, final String commentId) {
        final User user = userService.findUserByEmail(email);
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new HackerNewsException(HackerNewsError.COMMENT_NOT_FOUND));

        if (isNotOwnerOfComment(user, comment)) {
            throw new HackerNewsException(HackerNewsError.INSUFFICIENT_PERMISSIONS);
        }
        return comment;
    }

    private boolean isNotOwnerOfComment(final User user, final Comment comment) {
        return !user.equals(comment.getAuthor());
    }

    private Comment mapDtoToEntity(final CommentWrite commentWrite, final User user, final Submission submission) {
        final Comment comment = new Comment();
        comment.setSubmission(submission);
        comment.setAuthor(user);
        comment.setText(commentWrite.getText());
        return comment;
    }
}
