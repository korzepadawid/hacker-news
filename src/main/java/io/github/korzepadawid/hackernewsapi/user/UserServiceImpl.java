package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.domain.User;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsError;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsException;
import org.springframework.stereotype.Service;


@Service
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(final User user) {
        final boolean userPresent = userRepository.findUserByEmailIgnoreCaseOrUsernameIgnoreCase(user.getEmail(),
                user.getUsername()).isPresent();

        if (userPresent) {
            throw new HackerNewsException(HackerNewsError.USER_ALREADY_EXIST);
        }

        return userRepository.save(user);
    }
}
