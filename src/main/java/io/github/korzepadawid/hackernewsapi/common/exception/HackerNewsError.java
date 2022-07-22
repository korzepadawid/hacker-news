package io.github.korzepadawid.hackernewsapi.common.exception;

import org.springframework.http.HttpStatus;

public enum HackerNewsError {
    USER_NOT_FOUND("user not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXIST("user already exist", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus httpStatus;

    HackerNewsError(final String message,
                    final HttpStatus httpStatus) {

        this.httpStatus = httpStatus;
        this.message = message;
    }

    String getMessage() {
        return message;
    }

    HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
