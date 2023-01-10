package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsError;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailVerificationTokenServiceImpl implements EmailVerificationTokenService {

    private static final Logger log = LoggerFactory.getLogger(EmailVerificationTokenServiceImpl.class);

    private final EmailVerificationTokenRepository emailVerificationTokenRepository;

    public EmailVerificationTokenServiceImpl(final EmailVerificationTokenRepository emailVerificationTokenRepository) {
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
    }

    @Override
    public EmailVerificationToken createTokenForUser(final User user) {
        if (user == null) {
            log.error("User must be assigned to verification token");
            throw new HackerNewsException(HackerNewsError.EMAIL_VERIFICATION_TOKEN_MUST_HAVE_ASSIGNED_USER);
        }

        final EmailVerificationToken emailVerificationToken = createVerificationTokenEntity(user);
        return emailVerificationTokenRepository.save(emailVerificationToken);
    }

    private EmailVerificationToken createVerificationTokenEntity(User user) {
        final EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
        emailVerificationToken.setToken(UUID.randomUUID().toString());
        emailVerificationToken.setUser(user);
        return emailVerificationToken;
    }
}
