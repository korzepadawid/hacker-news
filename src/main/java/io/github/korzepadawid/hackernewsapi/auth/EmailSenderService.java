package io.github.korzepadawid.hackernewsapi.auth;

import io.github.korzepadawid.hackernewsapi.user.EmailVerificationToken;

interface EmailSenderService {

    void sendConfirmationEmail(EmailVerificationToken emailVerificationToken);
}
