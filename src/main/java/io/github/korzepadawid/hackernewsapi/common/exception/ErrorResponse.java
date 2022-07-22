package io.github.korzepadawid.hackernewsapi.common.exception;

class ErrorResponse {

    private String message;
    private String status;
    private Integer code;

    ErrorResponse(final HackerNewsError hackerNewsError) {
        message = hackerNewsError.getMessage();
        status = hackerNewsError.getHttpStatus().toString();
        code = hackerNewsError.getHttpStatus().value();
    }

    String getMessage() {
        return message;
    }

    void setMessage(String message) {
        this.message = message;
    }

    String getStatus() {
        return status;
    }

    void setStatus(String status) {
        this.status = status;
    }

    Integer getCode() {
        return code;
    }

    void setCode(Integer code) {
        this.code = code;
    }
}
