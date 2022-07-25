package io.github.korzepadawid.hackernewsapi.common.exception;

public class ErrorResponse {

    private String message;
    private String status;
    private Integer code;

    public ErrorResponse(final HackerNewsError hackerNewsError) {
        message = hackerNewsError.getMessage();
        status = hackerNewsError.getHttpStatus().toString();
        code = hackerNewsError.getHttpStatus().value();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
