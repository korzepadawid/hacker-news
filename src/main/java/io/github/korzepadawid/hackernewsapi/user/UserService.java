package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.domain.User;
import io.github.korzepadawid.hackernewsapi.common.projection.UserRead;
import org.springframework.web.multipart.MultipartFile;


public interface UserService {

    User create(User user);

    void verifyUserWithToken(String verificationToken);

    UserRead findUserByEmail(String email);

    void setAvatarByEmail(String email, MultipartFile multipartFile);

    void deleteAvatarByEmail(String email);
}
