package io.github.korzepadawid.hackernewsapi.common.projection;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CommentWrite {

    @NotBlank
    @Size(max = 250)
    private String text;

    public CommentWrite() {
    }

    public CommentWrite(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }
}
