package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsError;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


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

    @Override
    public void verifyUserEmailWithToken(final String verificationToken) {
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

    @Override
    public UserRead findUserByEmailDto(final String email) {
        return new UserRead(findUserByEmail(email));
    }

    @Override
    public User findUserByEmail(final String email) {
        return userRepository.findUserByEmailIgnoreCase(email)
                .orElseThrow(() -> new HackerNewsException(HackerNewsError.USER_NOT_FOUND));
    }

    @Override
    public void updateKarmaPoints(final User user, final Integer karmaPoints) {
        user.setKarmaPoints(karmaPoints);
        userRepository.save(user);
    }
}
