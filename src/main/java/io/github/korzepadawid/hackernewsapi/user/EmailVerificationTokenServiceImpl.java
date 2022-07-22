package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.domain.EmailVerificationToken;
import io.github.korzepadawid.hackernewsapi.common.domain.User;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsError;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EmailVerificationTokenServiceImpl implements EmailVerificationTokenService {

    public static final Long TOKEN_EXPIRES_IN_HOURS = 5L;

    private final EmailVerificationTokenRepository emailVerificationTokenRepository;

    public EmailVerificationTokenServiceImpl(final EmailVerificationTokenRepository emailVerificationTokenRepository) {
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
    }

    @Override
    public EmailVerificationToken createTokenForUser(final User user) {
        if (user == null) {
            throw new HackerNewsException(HackerNewsError.EMAIL_VERIFICATION_TOKEN_MUST_HAVE_ASSIGNED_USER);
        }

        final EmailVerificationToken emailVerificationToken = new EmailVerificationToken();

        emailVerificationToken.setToken(UUID.randomUUID().toString());
        emailVerificationToken.setUser(user);
        emailVerificationToken.setExpiringAt(LocalDateTime.now().minusHours(TOKEN_EXPIRES_IN_HOURS));

        return emailVerificationTokenRepository.save(emailVerificationToken);
    }
}
