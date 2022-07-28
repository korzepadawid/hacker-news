package io.github.korzepadawid.hackernewsapi.testutil;

import io.github.korzepadawid.hackernewsapi.common.domain.Submission;
import io.github.korzepadawid.hackernewsapi.common.domain.Url;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class SubmissionFactoryTest {

    public static Submission create() {
        final Submission submission = new Submission();
        submission.setId(UUID.randomUUID().toString());
        submission.setCreatedAt(LocalDateTime.now().minusDays(3));
        submission.setTitle("a cool title");
        submission.setAuthor(UserFactoryTest.createUser());
        submission.setVoteSum(100);
        submission.setUrl(new Url("https://www.netflix.com/browse"));
        return submission;
    }
}
