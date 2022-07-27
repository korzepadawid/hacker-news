package io.github.korzepadawid.hackernewsapi.submission;

import io.github.korzepadawid.hackernewsapi.auth.CurrentUser;
import io.github.korzepadawid.hackernewsapi.common.projection.SubmissionRead;
import io.github.korzepadawid.hackernewsapi.common.projection.SubmissionWrite;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/submissions")
class SubmissionController {

    private final SubmissionService submissionService;

    SubmissionController(final SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SubmissionRead saveSubmission(final @CurrentUser UserDetails userDetails,
                                         final @RequestBody SubmissionWrite submissionWrite) {
        return submissionService.save(userDetails.getUsername(), submissionWrite);
    }
}
