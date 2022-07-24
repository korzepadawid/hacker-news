package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.domain.EmailVerificationToken;
import io.github.korzepadawid.hackernewsapi.common.domain.User;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsError;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;

    UserServiceImpl(final UserRepository userRepository,
                    final EmailVerificationTokenRepository emailVerificationTokenRepository) {
        this.userRepository = userRepository;
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
    }

    @Override
    public User create(final User user) {
        final boolean userPresent = userRepository.findUserByEmailIgnoreCaseOrUsernameIgnoreCase(user.getEmail(),
                user.getUsername()).isPresent();

        if (userPresent) {
            log.error("User {}/{} has already existed.", user.getUsername(), user.getEmail());
            throw new HackerNewsException(HackerNewsError.USER_ALREADY_EXIST);
        }

        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void verifyUserWithToken(final String verificationToken) {
        final EmailVerificationToken emailVerificationToken = emailVerificationTokenRepository.findByToken(verificationToken)
                .orElseThrow(() -> new HackerNewsException(HackerNewsError.INVALID_TOKEN));

        final User user = emailVerificationToken.getUser();

        if (user.getVerified()) {
            log.error("User {} has been already verified", user.getId());
            throw new HackerNewsException(HackerNewsError.USER_ALREADY_VERIFIED);
        }

        user.setVerified(true);
        userRepository.save(user);
    }
}
