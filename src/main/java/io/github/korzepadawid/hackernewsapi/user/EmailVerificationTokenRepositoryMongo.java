package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.domain.EmailVerificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;


interface EmailVerificationTokenRepositoryMongo extends EmailVerificationTokenRepository, MongoRepository<EmailVerificationToken, String> {
}
