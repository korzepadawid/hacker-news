package io.github.korzepadawid.hackernewsapi.auth;

import io.github.korzepadawid.hackernewsapi.common.projection.AuthCredentials;
import io.github.korzepadawid.hackernewsapi.common.projection.AuthDetails;
import io.github.korzepadawid.hackernewsapi.common.projection.UserRead;
import io.github.korzepadawid.hackernewsapi.common.projection.UserWrite;


public interface AuthService {

    UserRead signUp(UserWrite userWrite);

    AuthDetails signIn(AuthCredentials authCredentials);
}
