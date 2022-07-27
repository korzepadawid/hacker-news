package io.github.korzepadawid.hackernewsapi.common.exception;

import org.springframework.http.HttpStatus;

public enum HackerNewsError {
    URL_PARSER_ERROR("invalid url", HttpStatus.BAD_REQUEST),
    INVALID_FILE_MIME_TYPE("invalid file mime type, please upload png, jpeg or gif", HttpStatus.BAD_REQUEST),
    INVALID_FILE_SIZE("file size must be between 100KB and 2MB", HttpStatus.BAD_REQUEST),
    INVALID_FILE("invalid file", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("access denied", HttpStatus.UNAUTHORIZED),
    USER_ALREADY_VERIFIED("user has been already verified", HttpStatus.CONFLICT),
    INVALID_TOKEN("you've provided invalid token", HttpStatus.NOT_FOUND),
    EMAIL_VERIFICATION_TOKEN_MUST_HAVE_ASSIGNED_USER("email verification token must have a user assigned", HttpStatus.BAD_REQUEST),
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
