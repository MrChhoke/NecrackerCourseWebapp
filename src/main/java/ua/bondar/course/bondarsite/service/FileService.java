package ua.bondar.course.bondarsite.service;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.FileList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.bondar.course.bondarsite.dto.DriveClient;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    /**
     * The folder id in Google Drive
     **/
    private String folderId = "1QYcYaytfE2mNpuNibIlDgEvbmLU_jLNT";

    @Autowired
    private DriveClient driveClient;

    /**
     * @param multipartFile - file from frontend
     * @return String that is id of photo
     **/



    public String uploadPhoto(MultipartFile multipartFile) {
        try {
            InputStream initialStream = multipartFile.getInputStream();
            byte[] buffer = new byte[initialStream.available()];
            initialStream.read(buffer);
            File file = new File(Paths.get("src/main/resources/static/img/photo.png").toAbsolutePath().toString());
            try (OutputStream outStream = new FileOutputStream(file)) {
                outStream.write(buffer);
            }
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

    public boolean isFileServiceAccess(){
        return driveClient.isUserAuthenticated();
    }
}
