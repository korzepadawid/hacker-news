package io.github.korzepadawid.hackernewsapi.submission;

import io.github.korzepadawid.hackernewsapi.auth.CurrentUser;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/submissions")
class SubmissionController {

    private final SubmissionService submissionService;

    SubmissionController(final SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SubmissionRead saveSubmission(final @CurrentUser @Parameter(hidden = true) UserDetails userDetails,
                                         final @RequestBody @Valid SubmissionWrite submissionWrite) {
        return submissionService.save(userDetails.getUsername(), submissionWrite);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public SubmissionPage getLatestSubmissions(final @RequestParam(name = "page", defaultValue = "0") String page) {
        return submissionService.findLatestSubmissions(Integer.valueOf(page));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{submissionId}")
    public void deleteSubmissionById(final @CurrentUser @Parameter(hidden = true) UserDetails userDetails,
                                     final @PathVariable String submissionId) {
        submissionService.deleteSubmissionById(userDetails.getUsername(), submissionId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{submissionId}")
    public SubmissionWithComments findSubmissionByIdWithComments(final @PathVariable String submissionId) {
        return submissionService.findSubmissionByIdWithComments(submissionId);
    }
}
