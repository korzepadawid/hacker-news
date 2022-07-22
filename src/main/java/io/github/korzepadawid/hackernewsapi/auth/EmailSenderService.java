package io.github.korzepadawid.hackernewsapi.auth;

import io.github.korzepadawid.hackernewsapi.common.domain.EmailVerificationToken;

interface EmailSenderService {

    void sendEmailConfirmationEmail(EmailVerificationToken emailVerificationToken);
}
