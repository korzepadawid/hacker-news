package io.github.korzepadawid.hackernewsapi.user;

import io.github.korzepadawid.hackernewsapi.common.domain.EmailVerificationToken;
import io.github.korzepadawid.hackernewsapi.common.domain.User;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsError;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsException;
import io.github.korzepadawid.hackernewsapi.common.projection.UserRead;
import io.github.korzepadawid.hackernewsapi.filestorage.FileService;
import io.github.korzepadawid.hackernewsapi.filestorage.FileStorageService;
import io.github.korzepadawid.hackernewsapi.filestorage.StorageKeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


@Service
class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final FileStorageService fileStorageService;
    private final FileService fileService;
    private final StorageKeyGenerator storageKeyGenerator;

    UserServiceImpl(final UserRepository userRepository,
                    final EmailVerificationTokenRepository emailVerificationTokenRepository,
                    final FileStorageService fileStorageService,
                    final FileService fileService,
                    final StorageKeyGenerator storageKeyGenerator) {
        this.userRepository = userRepository;
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
        this.fileStorageService = fileStorageService;
        this.fileService = fileService;
        this.storageKeyGenerator = storageKeyGenerator;
    }

    @Override
    public User create(final User user) {
        final boolean userPresent = userRepository.findUserByEmailIgnoreCaseOrUsernameIgnoreCase(user.getEmail(),
                user.getUsername()).isPresent();

        if (userPresent) {
            log.error("User {}/{} has already existed.", user.getUsername(), user.getEmail());
            throw new HackerNewsException(HackerNewsError.USER_ALREADY_EXIST);
        }

        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void verifyUserWithToken(final String verificationToken) {
        final EmailVerificationToken emailVerificationToken = emailVerificationTokenRepository.findByToken(verificationToken)
                .orElseThrow(() -> new HackerNewsException(HackerNewsError.INVALID_TOKEN));

        final User user = emailVerificationToken.getUser();

        if (user.getVerified()) {
            log.error("User {} has been already verified", user.getId());
            throw new HackerNewsException(HackerNewsError.USER_ALREADY_VERIFIED);
        }

        user.setVerified(true);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public UserRead findUserByEmail(final String email) {
        return new UserRead(getUserByEmailIgnoreCase(email));
    }

    @Transactional
    @Override
    public void setAvatarByEmail(final String email, final MultipartFile multipartFile) {
        final User user = getUserByEmailIgnoreCase(email);
        final File file = validateAndConvertToFile(multipartFile);
        final String storageKey = useExistingStorageKeyOrGenerateNew(multipartFile, user);

        fileStorageService.putFile(storageKey, file);

        if (firstAvatarOf(user)) {
            user.setAvatarStorageKey(storageKey);
            userRepository.save(user);
        }
    }

    @Transactional
    @Override
    public void deleteAvatarByEmail(final String email) {
        final User user = getUserByEmailIgnoreCase(email);
        final String storageKey = user.getAvatarStorageKey();

        if (storageKey != null) {
            fileStorageService.deleteFile(storageKey);
            user.setAvatarStorageKey(null);
            userRepository.save(user);
        }

    }

    private String useExistingStorageKeyOrGenerateNew(final MultipartFile multipartFile, final User user) {
        return user.getAvatarStorageKey() != null ? user.getAvatarStorageKey() : storageKeyGenerator.generateFrom(multipartFile);
    }

    private boolean firstAvatarOf(final User user) {
        return user.getAvatarStorageKey() == null;
    }

    private File validateAndConvertToFile(final MultipartFile multipartFile) {
        fileService.validate(multipartFile);
        return fileService.convert(multipartFile);
    }

    private User getUserByEmailIgnoreCase(final String email) {
        return userRepository.findUserByEmailIgnoreCase(email)
                .orElseThrow(() -> new HackerNewsException(HackerNewsError.USER_NOT_FOUND));
    }
}
