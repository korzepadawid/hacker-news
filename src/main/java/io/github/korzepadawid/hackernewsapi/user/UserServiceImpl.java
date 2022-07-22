package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.domain.User;
import io.github.korzepadawid.hackernewsapi.common.projection.UserWrite;
import org.springframework.stereotype.Service;


@Service
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(final UserWrite userWrite) {
        return null;
    }
}
