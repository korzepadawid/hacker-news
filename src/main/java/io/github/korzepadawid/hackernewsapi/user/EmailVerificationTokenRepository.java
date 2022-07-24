package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.domain.EmailVerificationToken;

import java.util.Optional;


interface EmailVerificationTokenRepository {

    EmailVerificationToken save(EmailVerificationToken emailVerificationToken);

    Optional<EmailVerificationToken> findByToken(String token);
}
