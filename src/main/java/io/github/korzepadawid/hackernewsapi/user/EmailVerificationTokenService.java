package io.github.korzepadawid.hackernewsapi.user;


public interface EmailVerificationTokenService {

    EmailVerificationToken createTokenForUser(User user);
}
