package io.github.korzepadawid.hackernewsapi.filestorage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageKeyGenerator {

    String generateFrom(MultipartFile multipartFile);
}
