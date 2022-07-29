package io.github.korzepadawid.hackernewsapi.filestorage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    void putFile(String storageKey, MultipartFile file);

    void deleteFile(String storageKey);
}
