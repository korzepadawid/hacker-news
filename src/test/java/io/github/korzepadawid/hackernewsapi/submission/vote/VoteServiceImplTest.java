package io.github.korzepadawid.hackernewsapi.submission.vote;

import io.github.korzepadawid.hackernewsapi.common.domain.Submission;
import io.github.korzepadawid.hackernewsapi.common.domain.User;
import io.github.korzepadawid.hackernewsapi.common.domain.Vote;
import io.github.korzepadawid.hackernewsapi.common.domain.VoteType;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsException;
import io.github.korzepadawid.hackernewsapi.common.projection.VoteWrite;
import io.github.korzepadawid.hackernewsapi.submission.SubmissionService;
import io.github.korzepadawid.hackernewsapi.testutil.SubmissionFactoryTest;
import io.github.korzepadawid.hackernewsapi.testutil.UserFactoryTest;
import io.github.korzepadawid.hackernewsapi.testutil.VoteFactoryTest;
import io.github.korzepadawid.hackernewsapi.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoteServiceImplTest {

    private static final String RANDOM_SUBMISSION_ID = UUID.randomUUID().toString();

    @Mock
    private UserService userService;

    @Mock
    private SubmissionService submissionService;

    @Mock
    private VoteRepository voteRepository;

    @InjectMocks
    private VoteServiceImpl voteService;

    @Test
    void shouldThrowExceptionWhenNotEnoughKarmaToVote() {
        final User user = UserFactoryTest.create(VoteServiceImpl.REQUIRED_KARMA_POINTS_TO_VOTE - 1);
        when(userService.findUserByEmail(user.getEmail())).thenReturn(user);

        final Throwable throwable = catchThrowable(() -> voteService.putVote(user.getEmail(),
                RANDOM_SUBMISSION_ID,
                new VoteWrite(VoteType.DOWN)));

        assertThat(throwable).isInstanceOf(HackerNewsException.class);
    }

    @Test
    void shouldThrowExceptionWhenVotingForOwnSubmission() {
        final User user = UserFactoryTest.create(VoteServiceImpl.REQUIRED_KARMA_POINTS_TO_VOTE);
        final Submission submission = SubmissionFactoryTest.createWithAuthor(user);
        when(userService.findUserByEmail(user.getEmail())).thenReturn(user);
        when(submissionService.findSubmissionById(submission.getId())).thenReturn(submission);

        final Throwable throwable = catchThrowable(() -> voteService.putVote(user.getEmail(),
                submission.getId(),
                new VoteWrite(VoteType.DOWN)));

        assertThat(throwable).isInstanceOf(HackerNewsException.class);
    }

    @Test
    void shouldCreateNewVoteAndUpdatePointsAndKarmaWhenVoteHaveNotExistedYet() {
        final User user = UserFactoryTest.create(VoteServiceImpl.REQUIRED_KARMA_POINTS_TO_VOTE);
        final User submissionAuthor = UserFactoryTest.create();
        final Submission submission = SubmissionFactoryTest.createWithAuthor(submissionAuthor);
        when(userService.findUserByEmail(user.getEmail())).thenReturn(user);
        when(submissionService.findSubmissionById(submission.getId())).thenReturn(submission);
        when(voteRepository.findVoteByAuthorAndSubmission(user, submission)).thenReturn(Optional.empty());

        voteService.putVote(user.getEmail(), submission.getId(), new VoteWrite(VoteType.DOWN));

        verify(userService).updateKarmaPoints(submissionAuthor, submissionAuthor.getKarmaPoints() + VoteType.DOWN.getValue());
        verify(submissionService).updateVoteSum(submission, submission.getVoteSum() + VoteType.DOWN.getValue());
        verify(voteRepository).save(any(Vote.class));
    }

    @Test
    void shouldDoNothingWhenPreviousVoteIsTheSameAsNewOne() {
        final User user = UserFactoryTest.create(VoteServiceImpl.REQUIRED_KARMA_POINTS_TO_VOTE);
        final User submissionAuthor = UserFactoryTest.create();
        final Submission submission = SubmissionFactoryTest.createWithAuthor(submissionAuthor);
        final VoteType prevVoteType = VoteType.DOWN;
        final Vote vote = VoteFactoryTest.create(submission, user, prevVoteType);
        when(userService.findUserByEmail(user.getEmail())).thenReturn(user);
        when(submissionService.findSubmissionById(submission.getId())).thenReturn(submission);
        when(voteRepository.findVoteByAuthorAndSubmission(user, submission)).thenReturn(Optional.of(vote));

        voteService.putVote(user.getEmail(), submission.getId(), new VoteWrite(prevVoteType));

        verifyNoMoreInteractions(userService, submissionService, voteRepository);
        assertThat(vote.getVoteType()).isEqualTo(prevVoteType);
    }

    @Test
    void shouldDoUpdateToUpVoteAndPointsAndKarmaWhenNewVoteIsDifferentThanPrevious() {
        final User user = UserFactoryTest.create(VoteServiceImpl.REQUIRED_KARMA_POINTS_TO_VOTE);
        final User submissionAuthor = UserFactoryTest.create();
        final Submission submission = SubmissionFactoryTest.createWithAuthor(submissionAuthor);
        final VoteType prevVoteType = VoteType.DOWN;
        final VoteType newVoteType = VoteType.UP;
        final Vote vote = VoteFactoryTest.create(submission, user, prevVoteType);
        when(userService.findUserByEmail(user.getEmail())).thenReturn(user);
        when(submissionService.findSubmissionById(submission.getId())).thenReturn(submission);
        when(voteRepository.findVoteByAuthorAndSubmission(user, submission)).thenReturn(Optional.of(vote));

        voteService.putVote(user.getEmail(), submission.getId(), new VoteWrite(newVoteType));

        verify(userService).updateKarmaPoints(submissionAuthor, submissionAuthor.getKarmaPoints()
                + VoteServiceImpl.DIFFERENCE_IN_ABS_BETWEEN_SMALLEST_AND_LARGEST_AMOUNT_OF_VOTES_AT_ONCE);
        verify(submissionService).updateVoteSum(submission, submission.getVoteSum()
                + VoteServiceImpl.DIFFERENCE_IN_ABS_BETWEEN_SMALLEST_AND_LARGEST_AMOUNT_OF_VOTES_AT_ONCE);
        verify(voteRepository).save(any(Vote.class));
        assertThat(vote.getVoteType()).isEqualTo(newVoteType);
    }

    @Test
    void shouldDoUpdateToDownVoteAndPointsAndKarmaWhenNewVoteIsDifferentThanPrevious() {
        final User user = UserFactoryTest.create(VoteServiceImpl.REQUIRED_KARMA_POINTS_TO_VOTE);
        final User submissionAuthor = UserFactoryTest.create();
        final Submission submission = SubmissionFactoryTest.createWithAuthor(submissionAuthor);
        final VoteType prevVoteType = VoteType.UP;
        final VoteType newVoteType = VoteType.DOWN;
        final Vote vote = VoteFactoryTest.create(submission, user, prevVoteType);
        when(userService.findUserByEmail(user.getEmail())).thenReturn(user);
        when(submissionService.findSubmissionById(submission.getId())).thenReturn(submission);
        when(voteRepository.findVoteByAuthorAndSubmission(user, submission)).thenReturn(Optional.of(vote));

        voteService.putVote(user.getEmail(), submission.getId(), new VoteWrite(newVoteType));

        verify(userService).updateKarmaPoints(submissionAuthor, submissionAuthor.getKarmaPoints()
                - VoteServiceImpl.DIFFERENCE_IN_ABS_BETWEEN_SMALLEST_AND_LARGEST_AMOUNT_OF_VOTES_AT_ONCE);
        verify(submissionService).updateVoteSum(submission, submission.getVoteSum()
                - VoteServiceImpl.DIFFERENCE_IN_ABS_BETWEEN_SMALLEST_AND_LARGEST_AMOUNT_OF_VOTES_AT_ONCE);
        verify(voteRepository).save(any(Vote.class));
        assertThat(vote.getVoteType()).isEqualTo(newVoteType);
    }

    @Test
    void shouldThrowExceptionWhenVoteDoesNotExist() {
        final User user = UserFactoryTest.create();
        final Submission submission = SubmissionFactoryTest.create();
        when(userService.findUserByEmail(user.getEmail())).thenReturn(user);
        when(submissionService.findSubmissionById(submission.getId())).thenReturn(submission);
        when(voteRepository.findVoteByAuthorAndSubmission(user, submission)).thenReturn(Optional.empty());

        final Throwable throwable = catchThrowable(() -> voteService.deleteVote(user.getEmail(), submission.getId()));

        assertThat(throwable).isInstanceOf(HackerNewsException.class);
    }

    @Test
    void shouldRemoveVoteAndUpdatePointsAndKarmaWhenDownVote() {
        final User user = UserFactoryTest.create();
        final User submissionAuthor = UserFactoryTest.create();
        final Submission submission = SubmissionFactoryTest.createWithAuthor(submissionAuthor);
        final VoteType voteType = VoteType.DOWN;
        final Vote vote = VoteFactoryTest.create(submission, user, voteType);
        when(userService.findUserByEmail(user.getEmail())).thenReturn(user);
        when(submissionService.findSubmissionById(submission.getId())).thenReturn(submission);
        when(voteRepository.findVoteByAuthorAndSubmission(user, submission)).thenReturn(Optional.of(vote));

        voteService.deleteVote(user.getEmail(), submission.getId());

        verify(userService).updateKarmaPoints(submissionAuthor, submissionAuthor.getKarmaPoints() + 1);
        verify(submissionService).updateVoteSum(submission, submission.getVoteSum() + 1);
        verify(voteRepository).delete(any(Vote.class));
    }

    @Test
    void shouldRemoveVoteAndUpdatePointsAndKarmaWhenUpVote() {
        final User user = UserFactoryTest.create();
        final User submissionAuthor = UserFactoryTest.create();
        final Submission submission = SubmissionFactoryTest.createWithAuthor(submissionAuthor);
        final VoteType voteType = VoteType.UP;
        final Vote vote = VoteFactoryTest.create(submission, user, voteType);
        when(userService.findUserByEmail(user.getEmail())).thenReturn(user);
        when(submissionService.findSubmissionById(submission.getId())).thenReturn(submission);
        when(voteRepository.findVoteByAuthorAndSubmission(user, submission)).thenReturn(Optional.of(vote));

        voteService.deleteVote(user.getEmail(), submission.getId());

        verify(userService).updateKarmaPoints(submissionAuthor, submissionAuthor.getKarmaPoints() - 1);
        verify(submissionService).updateVoteSum(submission, submission.getVoteSum() - 1);
        verify(voteRepository).delete(any(Vote.class));
    }
}