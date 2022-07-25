package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.domain.User;
import io.github.korzepadawid.hackernewsapi.common.projection.UserRead;


public interface UserService {

    User create(User user);

    void verifyUserWithToken(String verificationToken);

    UserRead findUserByEmail(String email);
}
