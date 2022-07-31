package io.github.korzepadawid.hackernewsapi.testutil;

import io.github.korzepadawid.hackernewsapi.common.domain.Comment;

import java.time.LocalDateTime;
import java.util.UUID;

abstract public class CommentFactoryTest {

    public static Comment create() {
        final Comment comment = new Comment();
        comment.setId(UUID.randomUUID().toString());
        comment.setCreatedAt(LocalDateTime.now().minusDays(10));
        comment.setAuthor(UserFactoryTest.create());
        comment.setSubmission(SubmissionFactoryTest.create());
        comment.setText("jhgsafjsdhgfhasgjf");
        return comment;
    }
}
