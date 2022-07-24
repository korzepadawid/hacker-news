package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.domain.EmailVerificationToken;
import io.github.korzepadawid.hackernewsapi.common.domain.User;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailVerificationTokenServiceImplTest {

    public static final String EMAIL_VERIFICATION_TOKEN_STRING = "asdjfhasjkdfh";

    @Mock
    private EmailVerificationTokenRepository emailVerificationTokenRepository;

    @InjectMocks
    private EmailVerificationTokenServiceImpl emailVerificationTokenService;

    @Test
    void shouldThrowExceptionWhenUserIsNull() {
        final Throwable throwable = catchThrowable(() -> emailVerificationTokenService.createTokenForUser(null));

        assertThat(throwable).isInstanceOf(HackerNewsException.class);
    }

    @Test
    void shouldCreateUnexpiredTokenAtInFutureWhenUserProvided() {
        final User user = UserFactoryTest.createUser();
        final EmailVerificationToken emailVerificationToken = new EmailVerificationToken(EMAIL_VERIFICATION_TOKEN_STRING,
                user);
        when(emailVerificationTokenRepository.save(any(EmailVerificationToken.class))).thenReturn(emailVerificationToken);

        final EmailVerificationToken tokenForUser = emailVerificationTokenService.createTokenForUser(user);

        assertThat(tokenForUser.getToken()).isNotBlank();
        assertThat(tokenForUser.getUser()).isEqualTo(user);
    }
}