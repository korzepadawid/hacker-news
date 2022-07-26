package io.github.korzepadawid.hackernewsapi.filestorage;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileService {

    File convert(MultipartFile multipartFile);

    void validate(MultipartFile multipartFile);
}
