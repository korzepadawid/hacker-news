package io.github.korzepadawid.hackernewsapi.submission.vote;

import io.github.korzepadawid.hackernewsapi.submission.vote.VoteType;

public class VoteWrite {

    private VoteType voteType;

    public VoteWrite() {
    }

    public VoteWrite(final VoteType voteType) {
        this.voteType = voteType;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(final VoteType voteType) {
        this.voteType = voteType;
    }
}
