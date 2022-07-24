package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.domain.EmailVerificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Optional;


interface EmailVerificationTokenRepositoryMongo extends EmailVerificationTokenRepository, MongoRepository<EmailVerificationToken, String> {

    Optional<EmailVerificationToken> findByToken(final @NotBlank @Size(max = 60) String token);
}
