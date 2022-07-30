package io.github.korzepadawid.hackernewsapi.submission.vote;

import io.github.korzepadawid.hackernewsapi.common.domain.Submission;
import io.github.korzepadawid.hackernewsapi.common.domain.User;
import io.github.korzepadawid.hackernewsapi.common.domain.Vote;

import java.util.Optional;

interface VoteRepository {

    Vote save(Vote vote);

    Optional<Vote> findVoteByAuthorAndSubmission(final User author, final Submission submission);
}
