package io.github.korzepadawid.hackernewsapi.filestorage;

import org.springframework.web.multipart.MultipartFile;

interface StorageKeyGenerator {

    String generateFrom(MultipartFile multipartFile);
}
