package io.github.korzepadawid.hackernewsapi.auth;

import io.github.korzepadawid.hackernewsapi.common.domain.EmailVerificationToken;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender javaMailSender;

    EmailSenderServiceImpl(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    @Override
    public void sendEmailConfirmationEmail(final EmailVerificationToken emailVerificationToken) {
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    }
}
