package io.github.korzepadawid.hackernewsapi.common.exception;

public class HackerNewsException extends RuntimeException {

    private final HackerNewsError hackerNewsError;

    public HackerNewsException(HackerNewsError hackerNewsError) {
        this.hackerNewsError = hackerNewsError;
    }

    HackerNewsError getHackerNewsError() {
        return hackerNewsError;
    }
}
