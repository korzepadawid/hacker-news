package io.github.korzepadawid.hackernewsapi.submission;

import io.github.korzepadawid.hackernewsapi.submission.comment.CommentRepository;
import io.github.korzepadawid.hackernewsapi.common.domain.Submission;
import io.github.korzepadawid.hackernewsapi.common.domain.User;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsException;
import io.github.korzepadawid.hackernewsapi.common.projection.SubmissionPage;
import io.github.korzepadawid.hackernewsapi.testutil.SubmissionFactoryTest;
import io.github.korzepadawid.hackernewsapi.testutil.UserFactoryTest;
import io.github.korzepadawid.hackernewsapi.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.github.korzepadawid.hackernewsapi.testutil.Constants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubmissionServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserService userService;

    @Mock
    private SubmissionRepository submissionRepository;

    @InjectMocks
    private SubmissionServiceImpl submissionService;

    @Test
    void shouldReturnPageNumber1WhenNegativeValue() {
        final Page<Submission> page = new PageImpl<>(getSubmissions(TOTAL_SUBMISSIONS));
        when(submissionRepository.findByOrderByCreatedAtDesc(any(PageRequest.class))).thenReturn(page);

        final SubmissionPage latestSubmissions = submissionService.findLatestSubmissions(NEGATIVE_PAGE_NUMBER);

        assertThat(latestSubmissions.getCurrentPage()).isEqualTo(FIRST_PAGE);
        assertThat(latestSubmissions.getTotalElements()).isEqualTo(TOTAL_SUBMISSIONS);
    }

    @Test
    void shouldReturnPageNumber1When0() {
        final Page<Submission> page = new PageImpl<>(getSubmissions(TOTAL_SUBMISSIONS));
        when(submissionRepository.findByOrderByCreatedAtDesc(any(PageRequest.class))).thenReturn(page);

        final SubmissionPage latestSubmissions = submissionService.findLatestSubmissions(ZERO);

        assertThat(latestSubmissions.getCurrentPage()).isEqualTo(FIRST_PAGE);
        assertThat(latestSubmissions.getTotalElements()).isEqualTo(TOTAL_SUBMISSIONS);
    }

    @Test
    void shouldReturnPageNumber2WhenSecondPageRequested() {
        final Page<Submission> page = new PageImpl<>(getSubmissions(TOTAL_SUBMISSIONS));
        when(submissionRepository.findByOrderByCreatedAtDesc(any(PageRequest.class))).thenReturn(page);

        final SubmissionPage latestSubmissions = submissionService.findLatestSubmissions(SECOND_PAGE);

        assertThat(latestSubmissions.getCurrentPage()).isEqualTo(SECOND_PAGE);
        assertThat(latestSubmissions.getTotalElements()).isEqualTo(TOTAL_SUBMISSIONS);
    }

    @Test
    void shouldThrowExceptionWhenSubmissionNotFound() {
        final User user = UserFactoryTest.create();
        when(userService.findUserByEmail(any(String.class))).thenReturn(user);
        when(submissionRepository.findById(any(String.class))).thenReturn(Optional.empty());

        final Throwable throwable = catchThrowable(() -> submissionService.deleteSubmissionById(user.getEmail(), RANDOM_ID));

        assertThat(throwable).isInstanceOf(HackerNewsException.class);
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotAuthorOfSubmission() {
        final User owner = UserFactoryTest.create();
        final User anotherUser = UserFactoryTest.create();
        anotherUser.setEmail(DIFFERENT_EMAIL);
        final Submission submission = SubmissionFactoryTest.create();
        submission.setAuthor(owner);
        when(userService.findUserByEmail(any(String.class))).thenReturn(anotherUser);
        when(submissionRepository.findById(any(String.class))).thenReturn(Optional.of(submission));

        final Throwable throwable = catchThrowable(() -> submissionService.deleteSubmissionById(anotherUser.getEmail(), RANDOM_ID));

        assertThat(throwable).isInstanceOf(HackerNewsException.class);
    }

    @Test
    void shouldDeleteSubmissionAndItsCommentsWhenUserIsAuthorOfSubmission() {
        final User owner = UserFactoryTest.create();
        final Submission submission = SubmissionFactoryTest.create();
        submission.setAuthor(owner);
        when(userService.findUserByEmail(any(String.class))).thenReturn(owner);
        when(submissionRepository.findById(any(String.class))).thenReturn(Optional.of(submission));

        submissionService.deleteSubmissionById(owner.getEmail(), submission.getId());

        verify(commentRepository, times(1)).deleteAllBySubmission(any(Submission.class));
        verify(submissionRepository, times(1)).delete(any(Submission.class));
    }

    private List<Submission> getSubmissions(final int n) {
        final List<Submission> submissions = new ArrayList<>();

        for (int i = ZERO; i < n; i++) {
            submissions.add(SubmissionFactoryTest.create());
        }

        return submissions;
    }
}