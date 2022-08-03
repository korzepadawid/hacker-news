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
class FileServiceImplTest {

    @InjectMocks
    private FileServiceImpl fileService;

    @Test
    void shouldThrowExceptionWhenFileIsTooBig() {
        final MockMultipartFile mockMultipartFile = new MockMultipartFile(FILENAME,
                VALID_ORIGINAL_FILE_NAME,
                MimeTypeUtils.IMAGE_PNG_VALUE,
                TOO_BIG_FILE_SIZE_IN_BYTES);

        final Throwable throwable = catchThrowable(() -> fileService.validate(mockMultipartFile));

        assertThat(throwable).isInstanceOf(HackerNewsException.class);
    }

    @Test
    void shouldThrowExceptionWhenFileIsTooSmall() {
        final MockMultipartFile mockMultipartFile = new MockMultipartFile(FILENAME,
                VALID_ORIGINAL_FILE_NAME,
                MimeTypeUtils.IMAGE_PNG_VALUE,
                TOO_SMALL_FILE_SIZE_IN_BYTES);

        final Throwable throwable = catchThrowable(() -> fileService.validate(mockMultipartFile));

        assertThat(throwable).isInstanceOf(HackerNewsException.class);
    }

    @Test
    void shouldThrowExceptionWhenFileHasInvalidMimeType() {
        final MockMultipartFile mockMultipartFile = new MockMultipartFile(FILENAME,
                VALID_ORIGINAL_FILE_NAME,
                MimeTypeUtils.TEXT_XML_VALUE,
                VALID_MIN_FILE_SIZE_IN_BYTES);

        final Throwable throwable = catchThrowable(() -> fileService.validate(mockMultipartFile));

        assertThat(throwable).isInstanceOf(HackerNewsException.class);
    }

    @Test
    void shouldNotThrowExceptionWhenFileIsValidAndSizeIsMinimum() {
        final MockMultipartFile mockMultipartFile = new MockMultipartFile(FILENAME,
                VALID_ORIGINAL_FILE_NAME,
                MimeTypeUtils.IMAGE_JPEG_VALUE,
                VALID_MIN_FILE_SIZE_IN_BYTES);

        fileService.validate(mockMultipartFile);
    }

    @Test
    void shouldNotThrowExceptionWhenFileIsValidAndSizeIsMaximum() {
        final MockMultipartFile mockMultipartFile = new MockMultipartFile(FILENAME,
                VALID_ORIGINAL_FILE_NAME,
                MimeTypeUtils.IMAGE_JPEG_VALUE,
                VALID_MAX_FILE_SIZE_IN_BYTES);

        fileService.validate(mockMultipartFile);
    }

    @Test
    void shouldNotThrowExceptionWhenFileIsValidAndSizeIsBetweenMinimumAndMaximum() {
        final MockMultipartFile mockMultipartFile = new MockMultipartFile(FILENAME,
                VALID_ORIGINAL_FILE_NAME,
                MimeTypeUtils.IMAGE_JPEG_VALUE,
                VALID_FILE_SIZE_BETWEEN_MIN_AND_MAX_IN_BYTES);

        fileService.validate(mockMultipartFile);
    }
}