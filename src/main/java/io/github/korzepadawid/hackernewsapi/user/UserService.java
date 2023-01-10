package io.github.korzepadawid.hackernewsapi.user;


public interface UserService {

    User create(User user);

    void verifyUserEmailWithToken(String verificationToken);

    UserRead findUserByEmailDto(String email);

    User findUserByEmail(String email);

    void updateKarmaPoints(User user, Integer karmaPoints);
}
