package io.github.korzepadawid.hackernewsapi.submission.vote;

import io.github.korzepadawid.hackernewsapi.common.projection.VoteWrite;

interface VoteService {

    void putVote(String email, String submissionId, VoteWrite voteWrite);

    void deleteVote(String email, String submissionId);
}
