package io.github.korzepadawid.hackernewsapi.submission.vote;

public enum VoteType {
    UP(1),
    DOWN(-1);

    private final int value;

    VoteType(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
