package io.github.korzepadawid.hackernewsapi.filestorage;

import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsError;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
class FileServiceImpl implements FileService {

    private final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    public static final long MIN_FILE_SIZE_IN_BYTES = 51_200L; // 50 KB
    public static final long MAX_FILE_SIZE_IN_BYTES = 1_572_864L; // 1.5 MB
    private static final List<String> AVAILABLE_FILE_MIME_TYPES = List.of(
            MimeTypeUtils.IMAGE_GIF_VALUE,
            MimeTypeUtils.IMAGE_PNG_VALUE,
            MimeTypeUtils.IMAGE_JPEG_VALUE
    );

    @Override
    public void validate(final MultipartFile multipartFile) {
        validateSize(multipartFile);
        validateMimeType(multipartFile);
    }

    private void validateMimeType(final MultipartFile multipartFile) {
        final String contentType = multipartFile.getContentType();
        if (!AVAILABLE_FILE_MIME_TYPES.contains(contentType)) {
            throw new HackerNewsException(HackerNewsError.INVALID_FILE_MIME_TYPE);
        }
    }

    private void validateSize(final MultipartFile multipartFile) {
        final long fileSize = multipartFile.getSize();
        if (fileSize < MIN_FILE_SIZE_IN_BYTES || fileSize > MAX_FILE_SIZE_IN_BYTES) {
            throw new HackerNewsException(HackerNewsError.INVALID_FILE_SIZE);
        }
    }
}
