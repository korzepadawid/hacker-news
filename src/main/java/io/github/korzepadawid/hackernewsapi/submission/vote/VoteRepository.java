package io.github.korzepadawid.hackernewsapi.submission.vote;

import io.github.korzepadawid.hackernewsapi.submission.Submission;
import io.github.korzepadawid.hackernewsapi.user.User;

import java.util.Optional;

interface VoteRepository {

    Vote save(Vote vote);

    Optional<Vote> findVoteByAuthorAndSubmission(final User author, final Submission submission);

    void delete(Vote vote);
}
