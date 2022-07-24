package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.domain.EmailVerificationToken;
import io.github.korzepadawid.hackernewsapi.common.domain.User;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final String FAKE_VERIFICATION_TOKEN = "ahsdfgashf";

    @Mock
    private EmailVerificationTokenRepository emailVerificationTokenRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldThrowExceptionWhenUserHasAlreadyExisted() {
        final User userToSave = UserFactoryTest.createUser();
        when(userRepository.findUserByEmailIgnoreCaseOrUsernameIgnoreCase(userToSave.getEmail(), userToSave.getUsername())).thenReturn(Optional.of(userToSave));

        final Throwable throwable = catchThrowable(() -> userService.create(userToSave));

        assertThat(throwable).isInstanceOf(HackerNewsException.class);
    }

    @Test
    void shouldCreateAndReturnNewUserWhenUserHasNotExisted() {
        final User userToSave = UserFactoryTest.createUser();
        when(userRepository.findUserByEmailIgnoreCaseOrUsernameIgnoreCase(userToSave.getEmail(), userToSave.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(userToSave);

        final User user = userService.create(userToSave);

        assertThat(user.getEmail()).isEqualTo(userToSave.getEmail());
        assertThat(user.getUsername()).isEqualTo(userToSave.getUsername());
    }

    @Test
    void shouldThrowExceptionWhenInvalidTokenAndNonVerifiedUser() {
        when(emailVerificationTokenRepository.findByToken(anyString())).thenReturn(Optional.empty());

        final Throwable throwable = catchThrowable(() -> userService.verifyUserWithToken(FAKE_VERIFICATION_TOKEN));

        assertThat(throwable).isInstanceOf(HackerNewsException.class);
    }


    @Test
    void shouldThrowExceptionWhenTokenValidAndUserVerified() {
        final User user = UserFactoryTest.createUser();
        user.setVerified(true);
        final EmailVerificationToken verificationToken = EmailVerificationTokenFactoryTest.createToken(user);
        when(emailVerificationTokenRepository.findByToken(verificationToken.getToken())).thenReturn(Optional.of(verificationToken));

        final Throwable throwable = catchThrowable(() -> userService.verifyUserWithToken(verificationToken.getToken()));

        assertThat(throwable).isInstanceOf(HackerNewsException.class);
    }

    @Test
    void shouldMakeUserVerifiedWhenTokenValidAndUserNonVerified() {
        final User user = UserFactoryTest.createUser();
        user.setVerified(false);
        final EmailVerificationToken verificationToken = EmailVerificationTokenFactoryTest.createToken(user);
        when(emailVerificationTokenRepository.findByToken(verificationToken.getToken())).thenReturn(Optional.of(verificationToken));

        userService.verifyUserWithToken(verificationToken.getToken());

        assertThat(user.getVerified()).isTrue();
    }
}