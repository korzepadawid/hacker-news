package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsException;
import io.github.korzepadawid.hackernewsapi.testutil.EmailVerificationTokenFactoryTest;
import io.github.korzepadawid.hackernewsapi.testutil.UserFactoryTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static io.github.korzepadawid.hackernewsapi.testutil.Constants.FAKE_VERIFICATION_TOKEN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private EmailVerificationTokenRepository emailVerificationTokenRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldThrowExceptionWhenUserHasAlreadyExisted() {
        final User userToSave = UserFactoryTest.create();
        when(userRepository.findUserByEmailIgnoreCaseOrUsernameIgnoreCase(userToSave.getEmail(), userToSave.getUsername())).thenReturn(Optional.of(userToSave));

        final Throwable throwable = catchThrowable(() -> userService.create(userToSave));

        assertThat(throwable).isInstanceOf(HackerNewsException.class);
    }

    @Test
    void shouldCreateAndReturnNewUserWhenUserHasNotExisted() {
        final User userToSave = UserFactoryTest.create();
        when(userRepository.findUserByEmailIgnoreCaseOrUsernameIgnoreCase(userToSave.getEmail(), userToSave.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(userToSave);

        final User user = userService.create(userToSave);

        assertThat(user.getEmail()).isEqualTo(userToSave.getEmail());
        assertThat(user.getUsername()).isEqualTo(userToSave.getUsername());
    }

    @Test
    void shouldThrowExceptionWhenInvalidTokenAndNonVerifiedUser() {
        when(emailVerificationTokenRepository.findByToken(anyString())).thenReturn(Optional.empty());

        final Throwable throwable = catchThrowable(() -> userService.verifyUserEmailWithToken(FAKE_VERIFICATION_TOKEN));

        assertThat(throwable).isInstanceOf(HackerNewsException.class);
    }


    @Test
    void shouldThrowExceptionWhenTokenValidAndUserVerified() {
        final User user = UserFactoryTest.create();
        user.setVerified(true);
        final EmailVerificationToken verificationToken = EmailVerificationTokenFactoryTest.createToken(user);
        when(emailVerificationTokenRepository.findByToken(verificationToken.getToken())).thenReturn(Optional.of(verificationToken));

        final Throwable throwable = catchThrowable(() -> userService.verifyUserEmailWithToken(verificationToken.getToken()));

        assertThat(throwable).isInstanceOf(HackerNewsException.class);
    }

    @Test
    void shouldMakeUserVerifiedWhenTokenValidAndUserNonVerified() {
        final User user = UserFactoryTest.create();
        user.setVerified(false);
        final EmailVerificationToken verificationToken = EmailVerificationTokenFactoryTest.createToken(user);
        when(emailVerificationTokenRepository.findByToken(verificationToken.getToken())).thenReturn(Optional.of(verificationToken));

        userService.verifyUserEmailWithToken(verificationToken.getToken());

        assertThat(user.getVerified()).isTrue();
    }
}