package io.github.korzepadawid.hackernewsapi.comment;

import io.github.korzepadawid.hackernewsapi.common.domain.Comment;
import io.github.korzepadawid.hackernewsapi.common.domain.Submission;
import io.github.korzepadawid.hackernewsapi.common.domain.User;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsException;
import io.github.korzepadawid.hackernewsapi.common.projection.CommentRead;
import io.github.korzepadawid.hackernewsapi.common.projection.CommentWrite;
import io.github.korzepadawid.hackernewsapi.submission.SubmissionService;
import io.github.korzepadawid.hackernewsapi.testutil.CommentFactoryTest;
import io.github.korzepadawid.hackernewsapi.testutil.SubmissionFactoryTest;
import io.github.korzepadawid.hackernewsapi.testutil.UserFactoryTest;
import io.github.korzepadawid.hackernewsapi.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    private static final String COMMENT_TEXT = "commenting a submission";
    private static final String RANDOM_MAIL = "cool@mailer.com";
    private static final String RANDOM_ID = "jhadfjgsajdfasdf";

    @Mock
    private UserService userService;

    @Mock
    private SubmissionService submissionService;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void shouldCreateCommentWhenSubmissionAndUserExist() {
        final User user = UserFactoryTest.create();
        final Submission submission = SubmissionFactoryTest.create();
        final CommentWrite commentWrite = new CommentWrite(COMMENT_TEXT);
        final Comment comment = CommentFactoryTest.create();
        comment.setText(commentWrite.getText());
        comment.setSubmission(submission);
        comment.setAuthor(user);
        when(userService.findUserByEmail(user.getEmail())).thenReturn(user);
        when(submissionService.findSubmissionById(submission.getId())).thenReturn(submission);
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        final CommentRead commentRead = commentService.addCommentToSubmission(user.getEmail(), submission.getId(), commentWrite);

        assertThat(commentRead.getAuthor().getEmail()).isEqualTo(user.getEmail());
        assertThat(commentRead.getText()).isEqualTo(commentWrite.getText());
    }

    @Test
    void shouldThrowExceptionWhenCommentNotFound() {
        final User user = UserFactoryTest.create();
        when(userService.findUserByEmail(user.getEmail())).thenReturn(user);
        when(commentRepository.findById(anyString())).thenReturn(Optional.empty());

        final Throwable throwable = catchThrowable(() -> commentService.deleteCommentById(user.getEmail(), RANDOM_ID));

        assertThat(throwable).isInstanceOf(HackerNewsException.class);
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotOwnerOfComment() {
        final User user = UserFactoryTest.create();
        user.setEmail(RANDOM_MAIL);
        final Comment comment = CommentFactoryTest.create();
        when(userService.findUserByEmail(user.getEmail())).thenReturn(user);
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        final Throwable throwable = catchThrowable(() -> commentService.deleteCommentById(user.getEmail(), comment.getId()));

        assertThat(throwable).isInstanceOf(HackerNewsException.class);
    }

    @Test
    void shouldDeleteCommentWhenUserIsOwnerOfComment() {
        final User user = UserFactoryTest.create();
        final Comment comment = CommentFactoryTest.create();
        comment.setAuthor(user);
        when(userService.findUserByEmail(user.getEmail())).thenReturn(user);
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        commentService.deleteCommentById(user.getEmail(), comment.getId());

        verify(commentRepository, times(1)).delete(comment);
    }
}