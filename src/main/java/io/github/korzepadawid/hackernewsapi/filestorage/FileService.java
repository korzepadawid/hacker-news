package io.github.korzepadawid.hackernewsapi.filestorage;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

interface FileService {

    File convert(MultipartFile multipartFile);

    void validate(MultipartFile multipartFile);
}
