package io.github.korzepadawid.hackernewsapi.submission.vote;

import io.github.korzepadawid.hackernewsapi.submission.Submission;
import io.github.korzepadawid.hackernewsapi.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

interface VoteRepositoryMongo extends VoteRepository, MongoRepository<Vote, String> {

    Optional<Vote> findVoteByAuthorAndSubmission(final @NotNull User author, final @NotNull Submission submission);
}
