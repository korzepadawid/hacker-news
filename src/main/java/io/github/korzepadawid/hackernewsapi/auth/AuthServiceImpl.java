package io.github.korzepadawid.hackernewsapi.auth;

import io.github.korzepadawid.hackernewsapi.common.projection.AuthCredentials;
import io.github.korzepadawid.hackernewsapi.common.projection.AuthDetails;
import io.github.korzepadawid.hackernewsapi.common.projection.UserRead;
import io.github.korzepadawid.hackernewsapi.common.projection.UserWrite;
import io.github.korzepadawid.hackernewsapi.user.EmailVerificationTokenService;
import io.github.korzepadawid.hackernewsapi.user.UserService;
import org.springframework.stereotype.Service;


@Service
class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final EmailVerificationTokenService emailVerificationTokenService;

    AuthServiceImpl(final UserService userService,
                    final EmailVerificationTokenService emailVerificationTokenService) {
        this.userService = userService;
        this.emailVerificationTokenService = emailVerificationTokenService;
    }

    @Override
    public UserRead signUp(final UserWrite userWrite) {
        return null;
    }

    @Override
    public AuthDetails signIn(final AuthCredentials authCredentials) {
        return null;
    }
}
