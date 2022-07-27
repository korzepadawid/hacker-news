package io.github.korzepadawid.hackernewsapi.testutil;

import io.github.korzepadawid.hackernewsapi.common.domain.EmailVerificationToken;
import io.github.korzepadawid.hackernewsapi.common.domain.User;

import java.util.UUID;

public abstract class EmailVerificationTokenFactoryTest {

    public static EmailVerificationToken createToken(final User user) {
        return new EmailVerificationToken(
                UUID.randomUUID().toString(),
                user
        );
    }
}
