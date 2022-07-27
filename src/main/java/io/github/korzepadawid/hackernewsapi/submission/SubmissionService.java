package io.github.korzepadawid.hackernewsapi.submission;

import io.github.korzepadawid.hackernewsapi.common.projection.SubmissionRead;
import io.github.korzepadawid.hackernewsapi.common.projection.SubmissionWrite;

import java.util.List;

interface SubmissionService {

    SubmissionRead save(String email, SubmissionWrite submissionWrite);

    void deleteSubmissionById(String email, String id);

    List<SubmissionRead> findLatestSubmissions();

    void findSubmissionByIdWithComments(String id);
}
