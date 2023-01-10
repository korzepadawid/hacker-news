package io.github.korzepadawid.hackernewsapi.auth;

import io.github.korzepadawid.hackernewsapi.user.UserRead;
import io.github.korzepadawid.hackernewsapi.user.UserWrite;


public interface AuthService {

    UserRead signUp(UserWrite userWrite);

    AuthDetails signIn(AuthCredentials authCredentials);
}
