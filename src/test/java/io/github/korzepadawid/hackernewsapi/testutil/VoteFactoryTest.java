package io.github.korzepadawid.hackernewsapi.testutil;

import io.github.korzepadawid.hackernewsapi.submission.Submission;
import io.github.korzepadawid.hackernewsapi.user.User;
import io.github.korzepadawid.hackernewsapi.submission.vote.Vote;
import io.github.korzepadawid.hackernewsapi.submission.vote.VoteType;

import java.util.UUID;

public abstract class VoteFactoryTest {

    public static Vote create(final Submission submission, final User author, final VoteType voteType) {
        final Vote vote = new Vote();
        vote.setVoteType(voteType);
        vote.setAuthor(author);
        vote.setId(UUID.randomUUID().toString());
        vote.setSubmission(submission);
        return vote;
    }
}
