package io.github.korzepadawid.hackernewsapi.common.domain;

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
