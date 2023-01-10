package io.github.korzepadawid.hackernewsapi.submission;

public interface SubmissionService {

    Submission findSubmissionById(String id);

    SubmissionRead save(String email, SubmissionWrite submissionWrite);

    void deleteSubmissionById(String email, String id);

    SubmissionPage findLatestSubmissions(Integer pageNumber);

    SubmissionWithComments findSubmissionByIdWithComments(String id);

    void updateVoteSum(Submission submission, Integer value);
}
