package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.domain.EmailVerificationToken;


public interface EmailVerificationTokenService {

    EmailVerificationToken create();
}
