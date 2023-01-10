package io.github.korzepadawid.hackernewsapi.submission.vote;

import io.github.korzepadawid.hackernewsapi.submission.Submission;
import io.github.korzepadawid.hackernewsapi.user.User;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsError;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsException;
import io.github.korzepadawid.hackernewsapi.submission.SubmissionService;
import io.github.korzepadawid.hackernewsapi.user.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class VoteServiceImpl implements VoteService {

    public static final int REQUIRED_KARMA_POINTS_TO_VOTE = 10;
    public static final int DIFFERENCE_IN_ABS_BETWEEN_SMALLEST_AND_LARGEST_AMOUNT_OF_VOTES_AT_ONCE = 2;
    private final UserService userService;
    private final SubmissionService submissionService;
    private final VoteRepository voteRepository;

    VoteServiceImpl(final UserService userService,
                    final SubmissionService submissionService,
                    final VoteRepository voteRepository) {
        this.userService = userService;
        this.submissionService = submissionService;
        this.voteRepository = voteRepository;
    }

    @Override
    public void putVote(final String email, final String submissionId, final VoteWrite voteWrite) {
        final User user = userService.findUserByEmail(email);

        if (isNotEnoughKarmaToVote(user)) {
            throw new HackerNewsException(HackerNewsError.NOT_ENOUGH_KARMA_TO_VOTE);
        }

        final Submission submission = submissionService.findSubmissionById(submissionId);
        final User submissionAuthor = submission.getAuthor();

        if (isOwnerOfSubmission(user, submissionAuthor)) {
            throw new HackerNewsException(HackerNewsError.VOTE_FOR_OWN_SUBMISSION);
        }

        final Optional<Vote> vote = voteRepository.findVoteByAuthorAndSubmission(user, submission);

        if (vote.isPresent()) {
            final Vote prevVote = vote.get();
            if (isDifferentThanPreviousVote(voteWrite, prevVote)) {
                updatePreviousVote(submission, submissionAuthor, prevVote);
            }
        } else {
            createNewVote(voteWrite, user, submission, submissionAuthor);
        }
    }

    @Override
    public void deleteVote(final String email, final String submissionId) {
        final User user = userService.findUserByEmail(email);
        final Submission submission = submissionService.findSubmissionById(submissionId);
        final Vote vote = voteRepository.findVoteByAuthorAndSubmission(user, submission)
                .orElseThrow(() -> new HackerNewsException(HackerNewsError.VOTE_NOT_FOUND));

        if (isNotVoteOwner(user, vote)) {
            throw new HackerNewsException(HackerNewsError.INSUFFICIENT_PERMISSIONS);
        }

        final int opposite = vote.getVoteType().getValue() * (-1);
        final User submissionAuthor = submission.getAuthor();

        userService.updateKarmaPoints(submissionAuthor, submissionAuthor.getKarmaPoints() + opposite);
        submissionService.updateVoteSum(submission, submission.getVoteSum() + opposite);
        voteRepository.delete(vote);
    }

    private boolean isNotVoteOwner(final User user, final Vote vote) {
        return !(vote.getAuthor().equals(user));
    }

    private void createNewVote(final VoteWrite voteWrite, final User user, final Submission submission, final User submissionAuthor) {
        final Vote newVote = mapDtoToEntity(voteWrite, user, submission);
        final int updatedVoteSum = calculateNewPointsValue(submission.getVoteSum(), voteWrite.getVoteType());
        final int updatedKarmaPoints = calculateNewPointsValue(submissionAuthor.getKarmaPoints(), voteWrite.getVoteType());
        submissionService.updateVoteSum(submission, updatedVoteSum);
        userService.updateKarmaPoints(submissionAuthor, updatedKarmaPoints);
        voteRepository.save(newVote);
    }

    private Vote mapDtoToEntity(final VoteWrite voteWrite, final User user, final Submission submission) {
        final Vote newVote = new Vote();
        newVote.setSubmission(submission);
        newVote.setAuthor(user);
        newVote.setVoteType(voteWrite.getVoteType());
        return newVote;
    }

    private void updatePreviousVote(final Submission submission, final User submissionAuthor, final Vote prevVote) {
        final int oppositeVoteSum = calculateValueWithOppositeVote(submission.getVoteSum(), prevVote);
        final int oppositeKarmaPoints = calculateValueWithOppositeVote(submissionAuthor.getKarmaPoints(), prevVote);
        invertVoteType(prevVote);
        submissionService.updateVoteSum(submission, oppositeVoteSum);
        userService.updateKarmaPoints(submissionAuthor, oppositeKarmaPoints);
        voteRepository.save(prevVote);
    }

    private void invertVoteType(final Vote prevVote) {
        if (VoteType.UP.equals(prevVote.getVoteType())) {
            prevVote.setVoteType(VoteType.DOWN);
        } else {
            prevVote.setVoteType(VoteType.UP);
        }
    }

    private int calculateNewPointsValue(final int prev, final VoteType voteType) {
        return prev + voteType.getValue();
    }

    private int calculateValueWithOppositeVote(final int prev, final Vote vote) {
        return prev + vote.getVoteType().getValue() * -1 * DIFFERENCE_IN_ABS_BETWEEN_SMALLEST_AND_LARGEST_AMOUNT_OF_VOTES_AT_ONCE;
    }

    private boolean isDifferentThanPreviousVote(final VoteWrite voteWrite, final Vote vote) {
        return !vote.getVoteType().equals(voteWrite.getVoteType());
    }


    private boolean isOwnerOfSubmission(final User user, final User submissionAuthor) {
        return submissionAuthor.equals(user);
    }

    private boolean isNotEnoughKarmaToVote(final User user) {
        return REQUIRED_KARMA_POINTS_TO_VOTE > user.getKarmaPoints();
    }
}
