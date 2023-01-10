package io.github.korzepadawid.hackernewsapi.user;

import java.util.Optional;


interface EmailVerificationTokenRepository {

    EmailVerificationToken save(EmailVerificationToken emailVerificationToken);

    Optional<EmailVerificationToken> findByToken(String token);
}
