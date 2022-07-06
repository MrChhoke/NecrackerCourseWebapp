package ua.bondar.course.bondarsite.dto;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class DriveClient {

    private HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private final String USER_IDENTIFIER_KEY = "ADMIN";

    @Value("${google.oauth.callback.uri}")
    private String CALLBACK_URI;

    @Value("${google.secret.key.path}")
    private String gdSecretKey;

    @Value("${google.credentials.folder.path}")
    private String  credentialsFolder;

    private GoogleAuthorizationCodeFlow flow;

    @PostConstruct
    public void init(){
        try {
            GoogleClientSecrets secrets = GoogleClientSecrets.load(JSON_FACTORY,new InputStreamReader(new FileInputStream(Paths.get(gdSecretKey).toAbsolutePath().toString())));
            flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,JSON_FACTORY,secrets,SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(credentialsFolder)))
                    .build();
        } catch (IOException e) {
            log.error("DriveClient: " + e);
        }
    }

    public Drive getClient() {
        Drive service = null;
        try {
            service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, flow.loadCredential(USER_IDENTIFIER_KEY))
                    .setApplicationName("Web application 1")
                    .build();
        } catch (IOException e) {
            log.error("DriveClient: " + e);
        }

        return service;
    }

    public boolean isUserAuthenticated(){
        try {
            HTTP_TRANSPORT = new NetHttpTransport();
            JSON_FACTORY = JacksonFactory.getDefaultInstance();
            GoogleClientSecrets secrets = GoogleClientSecrets.load(JSON_FACTORY,new InputStreamReader(new FileInputStream(Paths.get(gdSecretKey).toAbsolutePath().toString())));
            flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,JSON_FACTORY,secrets,SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(credentialsFolder)))
                    .build();
        } catch (IOException e) {
            log.error("DriveClient: " + e);
        }

        boolean isUserAuthenticated = false;
        try {
            Credential credential = flow.loadCredential(USER_IDENTIFIER_KEY);
            if(credential != null){
                boolean tokenValid = credential.refreshToken();
                if(tokenValid){
                    isUserAuthenticated = true;
                }
            }
        } catch (IOException e) {
            log.error("DriveClient: " + e);
        }
        return isUserAuthenticated;
    }
}