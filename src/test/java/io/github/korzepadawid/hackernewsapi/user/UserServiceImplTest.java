package io.github.korzepadawid.hackernewsapi.user;

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
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

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
}