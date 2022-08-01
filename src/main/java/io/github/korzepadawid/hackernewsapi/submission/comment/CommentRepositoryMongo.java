package io.github.korzepadawid.hackernewsapi.submission.comment;

import io.github.korzepadawid.hackernewsapi.common.domain.Comment;
import io.github.korzepadawid.hackernewsapi.common.domain.Submission;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

interface CommentRepositoryMongo extends CommentRepository, MongoRepository<Comment, String> {

    void deleteAllBySubmission(final @NotNull Submission submission);

    List<Comment> findCommentsBySubmissionOrderByCreatedAtDesc(final @NotNull Submission submission);
}
