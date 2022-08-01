package io.github.korzepadawid.hackernewsapi.auth;

import io.github.korzepadawid.hackernewsapi.common.domain.EmailVerificationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
class EmailSenderServiceImpl implements EmailSenderService {

    private static final Logger log = LoggerFactory.getLogger(EmailSenderServiceImpl.class);
    private static final String MAIL_SUBJECT = "Verification email 😊";

    private final JavaMailSender javaMailSender;

    EmailSenderServiceImpl(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    @Override
    public void sendConfirmationEmail(final EmailVerificationToken emailVerificationToken) {
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            prepareMimeMessage(emailVerificationToken, mimeMessage);
            javaMailSender.send(mimeMessage);
            log.info("Verification email has been sent to {}", emailVerificationToken.getUser().getEmail());
        } catch (MessagingException e) {
            log.error("Can't send verification email to {}", emailVerificationToken.getUser().getEmail());
            throw new RuntimeException(e);
        }
    }

    private void prepareMimeMessage(final EmailVerificationToken emailVerificationToken, final MimeMessage mimeMessage) throws MessagingException {
        mimeMessage.setRecipient(Message.RecipientType.TO,
                new InternetAddress(emailVerificationToken.getUser().getEmail()));
        mimeMessage.setSubject(MAIL_SUBJECT);
        mimeMessage.setText(getMessageText(emailVerificationToken));
    }

    private String getMessageText(final EmailVerificationToken emailVerificationToken) {
        return String.format("Hi %s, if you want to verify your email address, please click link below\n" +
                "http://localhost:8080/users/verify/%s", emailVerificationToken.getUser().getUsername(), emailVerificationToken.getToken());
    }
}
