package io.github.korzepadawid.hackernewsapi.submission;

import io.github.korzepadawid.hackernewsapi.comment.CommentRepository;
import io.github.korzepadawid.hackernewsapi.common.domain.Submission;
import io.github.korzepadawid.hackernewsapi.common.domain.Url;
import io.github.korzepadawid.hackernewsapi.common.domain.User;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsError;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsException;
import io.github.korzepadawid.hackernewsapi.common.projection.*;
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
    private final CommentRepository commentRepository;

    SubmissionServiceImpl(final UserService userService,
                          final SubmissionRepository submissionRepository,
                          final CommentRepository commentRepository) {
        this.userService = userService;
        this.submissionRepository = submissionRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public SubmissionRead save(final String email, final SubmissionWrite submissionWrite) {
        final User user = userService.findUserByEmail(email);
        final Submission submission = mapDtoToEntity(submissionWrite, user);
        final Submission savedSubmission = submissionRepository.save(submission);
        return new SubmissionRead(savedSubmission);
    }

    @Override
    public SubmissionPage findLatestSubmissions(final Integer pageNumber) {
        final int parsedPageNumber = Math.max(0, pageNumber - 1); // I prefer starting from 1 rather than 0
        final PageRequest pageRequest = PageRequest.of(parsedPageNumber, DEFAULT_PAGE_SIZE);
        final Page<Submission> results = submissionRepository.findByOrderByCreatedAtDesc(pageRequest);
        return getSubmissionPage(parsedPageNumber + 1, results);
    }

    @Override
    public void deleteSubmissionById(final String email, final String id) {
        final User user = userService.findUserByEmail(email);
        final Submission submission = findSubmissionById(id);
        final User submissionAuthor = submission.getAuthor();

        if (isNotAuthorOfSubmission(user, submissionAuthor)) {
            throw new HackerNewsException(HackerNewsError.INSUFFICIENT_PERMISSIONS);
        }

        deleteSubmissionWithComments(submission);
    }

    @Override
    public SubmissionWithComments findSubmissionByIdWithComments(final String id) {
        final Submission submission = findSubmissionById(id);
        final List<CommentRead> comments = commentRepository.findCommentsBySubmissionOrderByCreatedAtDesc(submission)
                .stream()
                .map(CommentRead::new)
                .collect(Collectors.toList());
        return new SubmissionWithComments(new SubmissionRead(submission), comments);
    }

    @Override
    public Submission findSubmissionById(final String id) {
        return submissionRepository.findById(id)
                .orElseThrow(() -> new HackerNewsException(HackerNewsError.SUBMISSION_NOT_FOUND));
    }

    private void deleteSubmissionWithComments(final Submission submission) {
        commentRepository.deleteAllBySubmission(submission);
        submissionRepository.delete(submission);
    }

    private boolean isNotAuthorOfSubmission(final User user, final User submissionAuthor) {
        return !user.equals(submissionAuthor);
    }

    private SubmissionPage getSubmissionPage(final Integer pageNumber, final Page<Submission> results) {
        final SubmissionPage submissionPage = new SubmissionPage();
        final List<SubmissionRead> submissionReads = results.getContent()
                .stream()
                .map(SubmissionRead::new)
                .collect(Collectors.toList());
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
