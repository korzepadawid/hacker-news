package io.github.korzepadawid.hackernewsapi.common.projection;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SubmissionWrite {

    @URL
    @NotBlank
    private String url;

    @NotBlank
    @Size(max = 60)
    private String title;

    public SubmissionWrite() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }
}
