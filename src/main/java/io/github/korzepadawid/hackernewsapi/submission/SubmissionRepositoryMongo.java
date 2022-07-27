package io.github.korzepadawid.hackernewsapi.submission;

import io.github.korzepadawid.hackernewsapi.common.domain.Submission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

interface SubmissionRepositoryMongo extends SubmissionRepository, MongoRepository<Submission, String> {

    Page<Submission> findByOrderByCreatedAtDesc(Pageable pageable);
}
