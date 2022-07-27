package io.github.korzepadawid.hackernewsapi.submission;

import io.github.korzepadawid.hackernewsapi.common.domain.Submission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

interface SubmissionRepository {

    Submission save(Submission submission);

    Page<Submission> findByOrderByCreatedAtDesc(Pageable pageable);
}
