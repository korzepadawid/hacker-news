package io.github.korzepadawid.hackernewsapi.auth;

import io.github.korzepadawid.hackernewsapi.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    AuthServiceImpl(final UserService userService,
                    final EmailVerificationTokenService emailVerificationTokenService,
                    final EmailSenderService emailSenderService,
                    final PasswordEncoder passwordEncoder,
                    final AuthenticationManager authenticationManager,
                    final JwtService jwtService) {
        this.userService = userService;
        this.emailVerificationTokenService = emailVerificationTokenService;
        this.emailSenderService = emailSenderService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public UserRead signUp(final UserWrite userWrite) {
        final User userToCreate = mapDtoToEntity(userWrite);
        final User savedUser = userService.create(userToCreate);
        final EmailVerificationToken verificationToken = emailVerificationTokenService.createTokenForUser(savedUser);
        emailSenderService.sendConfirmationEmail(verificationToken);
        log.info("New user has been created: {}", userWrite.getUsername());
        return new UserRead(savedUser);
    }

    @Override
    public AuthDetails signIn(final AuthCredentials authCredentials) {
        final Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authCredentials.getEmail(), authCredentials.getPassword()));

        final UserDetails authenticationPrincipal = (UserDetails) authentication.getPrincipal();
        final String jwt = jwtService.generateToken(authenticationPrincipal.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new AuthDetails(jwt);
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
