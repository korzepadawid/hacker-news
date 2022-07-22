package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.domain.EmailVerificationToken;
import io.github.korzepadawid.hackernewsapi.common.domain.User;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationTokenServiceImpl implements EmailVerificationTokenService {

    @Override
    public EmailVerificationToken createTokenForUser(User user) {
        return null;
    }
}
