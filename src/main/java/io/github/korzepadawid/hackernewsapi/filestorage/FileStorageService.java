package io.github.korzepadawid.hackernewsapi.filestorage;

import java.io.File;

public interface FileStorageService {

    void putFile(String storageKey, File file);

    void deleteFile(String storageKey);
}
