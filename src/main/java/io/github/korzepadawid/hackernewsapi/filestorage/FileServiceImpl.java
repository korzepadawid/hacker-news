package io.github.korzepadawid.hackernewsapi.filestorage;

import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsError;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsException;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    public static final String JPEG = "jpg";
    public static final String PNG = "png";
    public static final String GIF = "gif";
    public static final long MIN_FILE_SIZE_IN_BYTES = 102_400L; // 100 KB
    public static final long MAX_FILE_SIZE_IN_BYTES = 2_097_152L; // 2 MB
    private static final List<String> AVAILABLE_FILE_MIME_TYPES = List.of(
            MimeTypeUtils.IMAGE_GIF_VALUE,
            MimeTypeUtils.IMAGE_PNG_VALUE,
            MimeTypeUtils.IMAGE_JPEG_VALUE
    );

    @Override
    public File convert(final MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new HackerNewsException(HackerNewsError.INVALID_FILE);
        }
        try {
            final File convFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            final FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(multipartFile.getBytes());
            fos.close();
            return convFile;
        } catch (IOException e) {
            throw new HackerNewsException(HackerNewsError.INVALID_FILE);
        }
    }

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

    @Override
    public String generateFrom(final MultipartFile multipartFile) {
        if (multipartFile == null) {
            throw new HackerNewsException(HackerNewsError.INVALID_FILE);
        }

        final StringBuilder storageKeyBuilder = new StringBuilder();
        storageKeyBuilder.append(UUID.randomUUID());
        storageKeyBuilder.append(".");

        switch (Objects.requireNonNull(multipartFile.getContentType())) {
            case MimeTypeUtils.IMAGE_JPEG_VALUE:
                storageKeyBuilder.append(JPEG);
                break;
            case MimeTypeUtils.IMAGE_PNG_VALUE:
                storageKeyBuilder.append(PNG);
                break;
            case MimeTypeUtils.IMAGE_GIF_VALUE:
                storageKeyBuilder.append(GIF);
                break;
            default:
                throw new HackerNewsException(HackerNewsError.INVALID_FILE_MIME_TYPE);
        }

        return storageKeyBuilder.toString();
    }
}
