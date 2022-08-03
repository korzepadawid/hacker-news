package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.domain.EmailVerificationToken;
import io.github.korzepadawid.hackernewsapi.common.domain.User;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsException;
import io.github.korzepadawid.hackernewsapi.filestorage.FileService;
import io.github.korzepadawid.hackernewsapi.filestorage.FileStorageService;
import io.github.korzepadawid.hackernewsapi.filestorage.StorageKeyGenerator;
import io.github.korzepadawid.hackernewsapi.testutil.EmailVerificationTokenFactoryTest;
import io.github.korzepadawid.hackernewsapi.testutil.UserFactoryTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static io.github.korzepadawid.hackernewsapi.testutil.Constants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private EmailVerificationTokenRepository emailVerificationTokenRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StorageKeyGenerator storageKeyGenerator;

    @Mock
    private FileService fileService;

    @Mock
    private FileStorageService fileStorageService;

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

    @Test
    void shouldThrowExceptionWhenUserNotFoundByEmail() {
        when(userRepository.findUserByEmailIgnoreCase(anyString())).thenReturn(Optional.empty());

        final Throwable throwable = catchThrowable(() -> userService.setAvatarByEmail(UserFactoryTest.create().getEmail(), null));

        assertThat(throwable).isInstanceOf(HackerNewsException.class);
    }

    @Test
    void shouldUseExistingStorageKeyWhenStorageKeyExists() {
        final User user = UserFactoryTest.create();
        user.setAvatarStorageKey(FAKE_STORAGE_KEY);
        final MockMultipartFile mockMultipartFile = new MockMultipartFile(FILENAME, MOCK_BYTE_ARRAY);
        when(userRepository.findUserByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));

        userService.setAvatarByEmail(user.getEmail(), mockMultipartFile);

        assertThat(user.getAvatarStorageKey()).isEqualTo(FAKE_STORAGE_KEY);
        verify(fileService, times(1)).validate(mockMultipartFile);
        verify(fileStorageService, times(1)).putFile(FAKE_STORAGE_KEY, mockMultipartFile);
        verifyNoMoreInteractions(storageKeyGenerator);
    }

    @Test
    void shouldCreateNewStorageKeyAndSaveUserWhenStorageKeyHasNotExisted() {
        final User user = UserFactoryTest.create();
        user.setAvatarStorageKey(null);
        final MockMultipartFile mockMultipartFile = new MockMultipartFile(FILENAME, MOCK_BYTE_ARRAY);
        when(userRepository.findUserByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));
        when(storageKeyGenerator.generateFrom(mockMultipartFile)).thenReturn(FAKE_STORAGE_KEY);

        userService.setAvatarByEmail(user.getEmail(), mockMultipartFile);

        assertThat(user.getAvatarStorageKey()).isEqualTo(FAKE_STORAGE_KEY);
        verify(userRepository, times(1)).save(user);
        verify(fileService, times(1)).validate(mockMultipartFile);
        verify(fileStorageService, times(1)).putFile(FAKE_STORAGE_KEY, mockMultipartFile);
    }
}