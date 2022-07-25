package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.domain.User;

import java.util.Optional;


public interface UserRepository {

    Optional<User> findUserByEmailIgnoreCase(final String email);

    User save(User user);

    Optional<User> findUserByEmailIgnoreCaseOrUsernameIgnoreCase(String email,
                                                                 String username);
}
