package io.github.korzepadawid.hackernewsapi.submission.vote;

import io.github.korzepadawid.hackernewsapi.auth.CurrentUser;
import io.github.korzepadawid.hackernewsapi.common.projection.VoteWrite;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
class VoteController {

    private final VoteService voteService;

    VoteController(final VoteService voteService) {
        this.voteService = voteService;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{submissionId}/votes")
    public void putVote(final @CurrentUser @Parameter(hidden = true) UserDetails userDetails,
                        final @PathVariable String submissionId,
                        final @RequestBody @Valid VoteWrite voteWrite) {
        voteService.putVote(userDetails.getUsername(), submissionId, voteWrite);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{submissionId}/votes")
    public void deleteVote(final @CurrentUser @Parameter(hidden = true) UserDetails userDetails,
                           final @PathVariable String submissionId) {
        voteService.deleteVote(userDetails.getUsername(), submissionId);
    }
}
