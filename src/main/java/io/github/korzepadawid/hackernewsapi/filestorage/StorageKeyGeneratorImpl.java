package io.github.korzepadawid.hackernewsapi.filestorage;

import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsError;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsException;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

@Component
class StorageKeyGeneratorImpl implements StorageKeyGenerator {

    public static final String JPEG = "jpg";
    public static final String PNG = "png";
    public static final String GIF = "gif";

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
