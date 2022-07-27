package io.github.korzepadawid.hackernewsapi.submission;

import io.github.korzepadawid.hackernewsapi.common.domain.Submission;

interface SubmissionRepository {

    Submission save(Submission submission);
}
