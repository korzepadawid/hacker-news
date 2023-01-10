package io.github.korzepadawid.hackernewsapi.submission.vote;

interface VoteService {

    void putVote(String email, String submissionId, VoteWrite voteWrite);

    void deleteVote(String email, String submissionId);
}
