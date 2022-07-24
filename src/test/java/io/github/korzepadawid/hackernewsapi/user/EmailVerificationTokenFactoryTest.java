package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.domain.EmailVerificationToken;
import io.github.korzepadawid.hackernewsapi.common.domain.User;

import java.util.UUID;

abstract class EmailVerificationTokenFactoryTest {

    static EmailVerificationToken createToken(final User user) {
        return new EmailVerificationToken(
                UUID.randomUUID().toString(),
                user
        );
    }
}
