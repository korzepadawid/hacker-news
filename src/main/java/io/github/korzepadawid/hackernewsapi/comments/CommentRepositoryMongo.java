package io.github.korzepadawid.hackernewsapi.comments;

import io.github.korzepadawid.hackernewsapi.common.domain.Comment;
import io.github.korzepadawid.hackernewsapi.common.domain.Submission;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

interface CommentRepositoryMongo extends CommentRepository, MongoRepository<Comment, String> {

    List<Comment> findBySubmissionOrderByCreatedAtDesc(final @NotNull Submission submission);
}
