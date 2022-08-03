package io.github.korzepadawid.hackernewsapi.filestorage;

import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.MimeTypeUtils;

import static io.github.korzepadawid.hackernewsapi.testutil.Constants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@ExtendWith(MockitoExtension.class)
class StorageKeyGeneratorImplTest {


    @InjectMocks
    private StorageKeyGeneratorImpl storageKeyGenerator;

    @Test
    void shouldExceptionWhenUnsupportedMimeType() {
        final MockMultipartFile mockMultipartFile = new MockMultipartFile(FILENAME,
                VALID_ORIGINAL_FILE_NAME,
                MimeTypeUtils.TEXT_XML_VALUE,
                VALID_FILE_SIZE_BETWEEN_MIN_AND_MAX_IN_BYTES);

        final Throwable throwable = catchThrowable(() -> storageKeyGenerator.generateFrom(mockMultipartFile));

        assertThat(throwable).isInstanceOf(HackerNewsException.class);
    }

    @Test
    void shouldGenerateValidStorageKeyWhenMimeTypeJpeg() {
        final MockMultipartFile mockMultipartFile = new MockMultipartFile(FILENAME,
                VALID_ORIGINAL_FILE_NAME,
                MimeTypeUtils.IMAGE_JPEG_VALUE,
                VALID_FILE_SIZE_BETWEEN_MIN_AND_MAX_IN_BYTES);

        final String storageKey = storageKeyGenerator.generateFrom(mockMultipartFile);

        assertThat(storageKey).endsWith(StorageKeyGeneratorImpl.JPEG);
    }

    @Test
    void shouldGenerateValidStorageKeyWhenMimeTypePng() {
        final MockMultipartFile mockMultipartFile = new MockMultipartFile(FILENAME,
                VALID_ORIGINAL_FILE_NAME,
                MimeTypeUtils.IMAGE_PNG_VALUE,
                VALID_FILE_SIZE_BETWEEN_MIN_AND_MAX_IN_BYTES);

        final String storageKey = storageKeyGenerator.generateFrom(mockMultipartFile);

        assertThat(storageKey).endsWith(StorageKeyGeneratorImpl.PNG);
    }

    @Test
    void shouldGenerateValidStorageKeyWhenMimeTypeGif() {
        final MockMultipartFile mockMultipartFile = new MockMultipartFile(FILENAME,
                VALID_ORIGINAL_FILE_NAME,
                MimeTypeUtils.IMAGE_GIF_VALUE,
                VALID_FILE_SIZE_BETWEEN_MIN_AND_MAX_IN_BYTES);

        final String storageKey = storageKeyGenerator.generateFrom(mockMultipartFile);

        assertThat(storageKey).endsWith(StorageKeyGeneratorImpl.GIF);
    }
}