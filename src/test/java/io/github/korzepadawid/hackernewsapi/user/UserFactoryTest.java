package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.domain.User;

abstract class UserFactoryTest {

    static User createUser() {
        final User user = new User();

        user.setId("12389987329847239847");
        user.setEmail("johndoe@gmail.com");
        user.setPassword("c@oolP@55w0rd123");
        user.setKarmaPoints(450);
        user.setVerified(false);
        user.setUsername("jdoe");

        return user;
    }
}
