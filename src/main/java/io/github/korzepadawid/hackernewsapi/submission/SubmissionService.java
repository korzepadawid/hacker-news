package io.github.korzepadawid.hackernewsapi.submission;

import io.github.korzepadawid.hackernewsapi.common.domain.Submission;
import io.github.korzepadawid.hackernewsapi.common.projection.SubmissionPage;
import io.github.korzepadawid.hackernewsapi.common.projection.SubmissionRead;
import io.github.korzepadawid.hackernewsapi.common.projection.SubmissionWithComments;
import io.github.korzepadawid.hackernewsapi.common.projection.SubmissionWrite;

public interface SubmissionService {

    Submission findSubmissionById(String id);

    SubmissionRead save(String email, SubmissionWrite submissionWrite);

    void deleteSubmissionById(String email, String id);

    SubmissionPage findLatestSubmissions(Integer pageNumber);

    SubmissionWithComments findSubmissionByIdWithComments(String id);

    void updateVoteSum(Submission submission, Integer value);
}
