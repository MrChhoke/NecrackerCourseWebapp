package ua.bondar.course.bondarsite.service.impl;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.FileList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.bondar.course.bondarsite.dto.DriveClient;
import ua.bondar.course.bondarsite.service.FileService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.UUID;


/**
 * The class for using Google API
 * @version 1.0
 * @author Vladyslav Bondar
 */
@Service
@Slf4j
@Primary
public class FileServiceGoogle implements FileService {

    /**
     * The folder id in Google Drive
     */
    private final String folderId = "1QYcYaytfE2mNpuNibIlDgEvbmLU_jLNT";

    private final DriveClient driveClient;

    @Autowired
    public FileServiceGoogle(DriveClient driveClient) {
        this.driveClient = driveClient;
    }

    /**
     * The method for uploding file into google drive
     * @param multipartFile is a file that should be uploaded
     * @return {@link java.lang.String} that contains file id
     */
    public String uploadPhoto(MultipartFile multipartFile) {
        try {
            InputStream initialStream = multipartFile.getInputStream();
            byte[] buffer = new byte[initialStream.available()];
            initialStream.read(buffer);
            StringBuilder builder = new StringBuilder("src/main/resources/static/img/");
            builder.append(UUID.randomUUID());
            builder.append(".png");
            try (OutputStream outStream = new FileOutputStream(
                    Paths.get(builder.toString()).toAbsolutePath().toString()
            )) {
                outStream.write(buffer);
            }
            File file = new File(builder.toString());
            com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
            fileMetadata.setName(UUID.randomUUID().toString());
            fileMetadata.setParents(Collections.singletonList(folderId));
            FileContent mediaContent = new FileContent("image/jpeg", file);
            com.google.api.services.drive.model.File fileGoogle = driveClient.getClient().files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();
            file.delete();
            return fileGoogle.getId();
        } catch (IOException e) {
            log.error("FileService: " + e);
        }
        return null;
    }

    /**
     * The method for uploding file into google drive
     * @param file is link of file that should be uploaded
     * @return {@link java.lang.String} that contains file id
     */
    public String uploadPhoto(File file) {
        try {
            com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
            fileMetadata.setName(UUID.randomUUID().toString());
            fileMetadata.setParents(Collections.singletonList(folderId));
            FileContent mediaContent = new FileContent("image/jpeg", file);
            com.google.api.services.drive.model.File fileGoogle = driveClient.getClient().files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();
            return fileGoogle.getId();
        } catch (IOException e) {
            log.error("FileService: " + e);
        }
        return null;
    }

    public boolean isFileExist(String idFile){
        if(idFile == null || idFile.equals("")) return false;
        try {
            boolean bool = driveClient.getClient()
                    .files()
                    .get(idFile)
                    .execute()
                    .isEmpty();
            return !bool;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * The method for checking download is available
     * @return boolean variable showing availability
     */
    public boolean isFileServiceAccess(){
        return driveClient.isUserAuthenticated();
    }
}
