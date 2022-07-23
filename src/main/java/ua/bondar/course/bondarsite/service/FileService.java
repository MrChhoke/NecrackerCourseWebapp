package ua.bondar.course.bondarsite.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileService {
    String uploadPhoto(MultipartFile multipartFile);
    String uploadPhoto(File file);
    boolean isFileExist(String idFile);
    boolean isFileServiceAccess();
}
