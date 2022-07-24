package io.github.korzepadawid.hackernewsapi.common.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class HackerNewsExceptionHandler {

    @ExceptionHandler(HackerNewsException.class)
    public ResponseEntity<ErrorResponse> handleHackerNewsException(final HackerNewsException hackerNewsException) {
        final HackerNewsError hackerNewsError = hackerNewsException.getHackerNewsError();
        final ErrorResponse errorResponse = new ErrorResponse(hackerNewsError);
        return new ResponseEntity<>(errorResponse, HttpHeaders.EMPTY, hackerNewsError.getHttpStatus());
    }
}
