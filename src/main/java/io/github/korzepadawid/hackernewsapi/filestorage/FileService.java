package io.github.korzepadawid.hackernewsapi.filestorage;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    void validate(MultipartFile multipartFile);
}
