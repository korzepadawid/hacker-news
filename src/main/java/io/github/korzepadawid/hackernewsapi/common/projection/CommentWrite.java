package io.github.korzepadawid.hackernewsapi.common.projection;

public class CommentWrite {

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
