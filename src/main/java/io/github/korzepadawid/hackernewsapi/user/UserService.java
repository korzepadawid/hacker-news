package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.domain.User;
import io.github.korzepadawid.hackernewsapi.common.projection.UserRead;


public interface UserService {

    User create(User user);

    void verifyUserEmailWithToken(String verificationToken);

    UserRead findUserByEmailDto(String email);

    User findUserByEmail(String email);

    void updateKarmaPoints(User user, Integer karmaPoints);
}
