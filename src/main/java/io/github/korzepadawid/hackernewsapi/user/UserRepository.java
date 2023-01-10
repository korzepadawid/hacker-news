package io.github.korzepadawid.hackernewsapi.user;

import java.util.Optional;


public interface UserRepository {

    Optional<User> findUserByEmailIgnoreCase(final String email);

    User save(User user);

    Optional<User> findUserByEmailIgnoreCaseOrUsernameIgnoreCase(String email,
                                                                 String username);
}
