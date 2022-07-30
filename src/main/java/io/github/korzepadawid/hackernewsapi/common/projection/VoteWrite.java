package io.github.korzepadawid.hackernewsapi.common.projection;

import io.github.korzepadawid.hackernewsapi.common.domain.VoteType;

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
