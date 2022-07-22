package io.github.korzepadawid.hackernewsapi.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class HackerNewsExceptionHandler {

    @ExceptionHandler(HackerNewsException.class)
    public ResponseEntity<ErrorResponse> handleHackerNewsException(final HackerNewsException hackerNewsException) {
        final HackerNewsError hackerNewsError = hackerNewsException.getHackerNewsError();
        final ErrorResponse errorResponse = new ErrorResponse(hackerNewsError);
        return ResponseEntity.status(hackerNewsError.getHttpStatus()).body(errorResponse);
    }
}
