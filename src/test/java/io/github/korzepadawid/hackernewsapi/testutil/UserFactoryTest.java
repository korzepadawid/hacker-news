package io.github.korzepadawid.hackernewsapi.testutil;

import io.github.korzepadawid.hackernewsapi.common.domain.User;

import java.util.UUID;

abstract public class UserFactoryTest {

    public static User create() {
        final User user = new User();

        user.setId("12389987329847239847");
        user.setEmail(UUID.randomUUID() + "@gmail.com");
        user.setPassword("c@oolP@55w0rd123");
        user.setKarmaPoints(450);
        user.setVerified(false);
        user.setUsername("jdoe");

        return user;
    }

    public static User create(final int karma) {
        final User user = create();
        user.setKarmaPoints(karma);
        return user;
    }
}
