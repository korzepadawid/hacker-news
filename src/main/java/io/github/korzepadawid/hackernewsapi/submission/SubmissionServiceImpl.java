package io.github.korzepadawid.hackernewsapi.submission;

import io.github.korzepadawid.hackernewsapi.common.domain.Submission;
import io.github.korzepadawid.hackernewsapi.common.domain.Url;
import io.github.korzepadawid.hackernewsapi.common.domain.User;
import io.github.korzepadawid.hackernewsapi.common.projection.SubmissionPage;
import io.github.korzepadawid.hackernewsapi.common.projection.SubmissionRead;
import io.github.korzepadawid.hackernewsapi.common.projection.SubmissionWrite;
import io.github.korzepadawid.hackernewsapi.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class SubmissionServiceImpl implements SubmissionService {

    private static final int DEFAULT_PAGE_SIZE = 10;

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

    @Override
    public SubmissionPage findLatestSubmissions(final Integer pageNumber) {
        final int parsedPageNumber = Math.max(0, pageNumber - 1); // I prefer starting from 1 rather than 0
        final PageRequest pageRequest = PageRequest.of(parsedPageNumber, DEFAULT_PAGE_SIZE);
        final Page<Submission> results = submissionRepository.findByOrderByCreatedAtDesc(pageRequest);
        return getSubmissionPage(pageNumber, results);
    }

    @Override
    public void findSubmissionByIdWithComments(final String id) {
        // TODO: 27.07.2022 implement as soon as comments will be implemented
    }

    private SubmissionPage getSubmissionPage(final Integer pageNumber, final Page<Submission> results) {
        final SubmissionPage submissionPage = new SubmissionPage();
        final List<SubmissionRead> submissionReads = results.get().map(SubmissionRead::new).collect(Collectors.toList());
        submissionPage.setCurrentPage(pageNumber);
        submissionPage.setItemsPerPage(DEFAULT_PAGE_SIZE);
        submissionPage.setTotalElements(results.getTotalElements());
        submissionPage.setSubmissionReads(submissionReads);
        return submissionPage;
    }

    private Submission mapDtoToEntity(final SubmissionWrite submissionWrite, final User user) {
        final Submission submission = new Submission();
        submission.setAuthor(user);
        submission.setTitle(submissionWrite.getTitle());
        submission.setVoteSum(0);
        submission.setUrl(new Url(submissionWrite.getUrl()));
        return submission;
    }
}
