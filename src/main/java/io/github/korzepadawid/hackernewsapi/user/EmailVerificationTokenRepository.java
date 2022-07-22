package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.domain.EmailVerificationToken;


interface EmailVerificationTokenRepository {

    EmailVerificationToken save(EmailVerificationToken emailVerificationToken);
}
