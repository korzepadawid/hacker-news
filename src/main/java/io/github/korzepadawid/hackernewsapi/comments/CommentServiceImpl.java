package io.github.korzepadawid.hackernewsapi.comments;

import io.github.korzepadawid.hackernewsapi.common.projection.CommentRead;
import io.github.korzepadawid.hackernewsapi.common.projection.CommentWrite;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Override
    public CommentRead addCommentToSubmission(final String email,
                                              final String submissionId,
                                              final CommentWrite commentWrite) {
        return null;
    }
}
