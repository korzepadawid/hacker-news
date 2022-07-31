package io.github.korzepadawid.hackernewsapi.testutil;

import io.github.korzepadawid.hackernewsapi.common.domain.Submission;
import io.github.korzepadawid.hackernewsapi.common.domain.User;
import io.github.korzepadawid.hackernewsapi.common.domain.Vote;
import io.github.korzepadawid.hackernewsapi.common.domain.VoteType;

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
