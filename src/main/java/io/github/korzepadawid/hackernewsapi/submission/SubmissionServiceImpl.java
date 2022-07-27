package io.github.korzepadawid.hackernewsapi.submission;

import io.github.korzepadawid.hackernewsapi.common.domain.Submission;
import io.github.korzepadawid.hackernewsapi.common.domain.Url;
import io.github.korzepadawid.hackernewsapi.common.domain.User;
import io.github.korzepadawid.hackernewsapi.common.projection.SubmissionRead;
import io.github.korzepadawid.hackernewsapi.common.projection.SubmissionWrite;
import io.github.korzepadawid.hackernewsapi.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class SubmissionServiceImpl implements SubmissionService {

    private final UserService userService;
    private final SubmissionRepository submissionRepository;

    SubmissionServiceImpl(final UserService userService,
                          final SubmissionRepository submissionRepository) {
        this.userService = userService;
        this.submissionRepository = submissionRepository;
    }

    @Override
    public SubmissionRead save(final String email, final SubmissionWrite submissionWrite) {
        final User user = userService.findUserByEmail(email);
        final Submission submission = mapDtoToEntity(submissionWrite, user);
        final Submission savedSubmission = submissionRepository.save(submission);
        return new SubmissionRead(savedSubmission);
    }

    @Override
    public void deleteSubmissionById(final String email, final String id) {

    }

    private Submission mapDtoToEntity(final SubmissionWrite submissionWrite, final User user) {
        final Submission submission = new Submission();
        submission.setAuthor(user);
        submission.setTitle(submissionWrite.getTitle());
        submission.setVoteSum(0);
        submission.setUrl(new Url(submissionWrite.getUrl()));
        return submission;
    }

    @Override
    public List<SubmissionRead> findLatestSubmissions() {
        return null;
    }

    @Override
    public void findSubmissionByIdWithComments(final String id) {

    }
}
