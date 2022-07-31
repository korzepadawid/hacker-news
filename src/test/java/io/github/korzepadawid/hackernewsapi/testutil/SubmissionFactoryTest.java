package io.github.korzepadawid.hackernewsapi.testutil;

import io.github.korzepadawid.hackernewsapi.common.domain.Submission;
import io.github.korzepadawid.hackernewsapi.common.domain.Url;
import io.github.korzepadawid.hackernewsapi.common.domain.User;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class SubmissionFactoryTest {

    public static Submission create() {
        final Submission submission = new Submission();
        submission.setId(UUID.randomUUID().toString());
        submission.setCreatedAt(LocalDateTime.now().minusDays(3));
        submission.setTitle("a cool title");
        submission.setAuthor(UserFactoryTest.create());
        submission.setVoteSum(100);
        submission.setUrl(new Url("https://www.netflix.com/browse"));
        return submission;
    }

    public static Submission createWithAuthor(final User author) {
        final Submission submission = create();
        submission.setAuthor(author);
        return submission;
    }
}
