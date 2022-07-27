package io.github.korzepadawid.hackernewsapi.submission;

import io.github.korzepadawid.hackernewsapi.common.projection.SubmissionPage;
import io.github.korzepadawid.hackernewsapi.common.projection.SubmissionRead;
import io.github.korzepadawid.hackernewsapi.common.projection.SubmissionWrite;

interface SubmissionService {

    SubmissionRead save(String email, SubmissionWrite submissionWrite);

    void deleteSubmissionById(String email, String id);

    SubmissionPage findLatestSubmissions(Integer pageNumber);

    void findSubmissionByIdWithComments(String id);
}
