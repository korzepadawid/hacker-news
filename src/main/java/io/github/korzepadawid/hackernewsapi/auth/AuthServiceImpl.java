package io.github.korzepadawid.hackernewsapi.auth;

import io.github.korzepadawid.hackernewsapi.common.domain.EmailVerificationToken;
import io.github.korzepadawid.hackernewsapi.common.domain.User;
import io.github.korzepadawid.hackernewsapi.common.projection.AuthCredentials;
import io.github.korzepadawid.hackernewsapi.common.projection.AuthDetails;
import io.github.korzepadawid.hackernewsapi.common.projection.UserRead;
import io.github.korzepadawid.hackernewsapi.common.projection.UserWrite;
import io.github.korzepadawid.hackernewsapi.user.EmailVerificationTokenService;
import io.github.korzepadawid.hackernewsapi.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private static final int DEFAULT_KARMA_POINTS = 0;
    private static final boolean DEFAULT_EMAIL_VERIFICATION_STATUS = false;

    private final UserService userService;
    private final EmailVerificationTokenService emailVerificationTokenService;
    private final EmailSenderService emailSenderService;
    private final PasswordEncoder passwordEncoder;

    AuthServiceImpl(final UserService userService,
                    final EmailVerificationTokenService emailVerificationTokenService,
                    final EmailSenderService emailSenderService,
                    final PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.emailVerificationTokenService = emailVerificationTokenService;
        this.emailSenderService = emailSenderService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserRead signUp(final UserWrite userWrite) {
        final User userToCreate = mapDtoToEntity(userWrite);
        final User savedUser = userService.create(userToCreate);
        final EmailVerificationToken verificationToken = emailVerificationTokenService.createTokenForUser(savedUser);
        emailSenderService.sendEmailConfirmationEmail(verificationToken);
        log.info("New user has been created: {}", userWrite.getUsername());
        return new UserRead(savedUser);
    }

    @Override
    public AuthDetails signIn(final AuthCredentials authCredentials) {
        // TODO: 13.07.2022
        return null;
    }

    private User mapDtoToEntity(final UserWrite userWrite) {
        final User user = new User();
        user.setUsername(userWrite.getUsername());
        user.setEmail(userWrite.getEmail());
        user.setPassword(passwordEncoder.encode(userWrite.getPassword()));
        user.setKarmaPoints(DEFAULT_KARMA_POINTS);
        user.setVerified(DEFAULT_EMAIL_VERIFICATION_STATUS);
        return user;
    }
}
