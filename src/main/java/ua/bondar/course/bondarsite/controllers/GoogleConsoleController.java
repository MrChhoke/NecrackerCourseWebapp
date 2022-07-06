package ua.bondar.course.bondarsite.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.DriveScopes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.bondar.course.bondarsite.model.user.UserOfShop;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Controller
@Slf4j
public class GoogleConsoleController {

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
            GoogleClientSecrets secrets = GoogleClientSecrets.load(
                    JSON_FACTORY,
                    new InputStreamReader(
                            new FileInputStream(Paths.get(gdSecretKey).toAbsolutePath().toString())
                    )
            );
            flow = new GoogleAuthorizationCodeFlow.Builder(
                    HTTP_TRANSPORT,
                    JSON_FACTORY,
                    secrets,
                    SCOPES)
                        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(credentialsFolder)))
                        .build();
        } catch (IOException e) {
            log.error("GoogleConsoleController: " + e);
        }
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    @GetMapping("/sign_in_google")
    public void GoogleSignIn(HttpServletResponse response){
        try {
            GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
            String redirectURL = url.setRedirectUri(CALLBACK_URI)
                                     .setAccessType("offline")
                                     .build();
            response.sendRedirect(redirectURL);
        } catch (IOException e) {
            log.error("GoogleConsoleController: " + e);
        }
    }

    @GetMapping("/Callback")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    public String saveAuthorizationCode(@AuthenticationPrincipal UserOfShop user,
                                        Model model,
                                        HttpServletRequest request){
        model.addAttribute("user", user);
        String code = request.getParameter("code");
        if(code != null){
            saveToken(code);
            log.info("Токен збережений успішно");
            return "redirect:/";
        }
        return "errors";
    }

    private void saveToken(@NotNull String code){
        try {
            GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(CALLBACK_URI).execute();
            flow.createAndStoreCredential(response,USER_IDENTIFIER_KEY);
        } catch (IOException e) {
            log.error("GoogleConsoleController: " + e);
        }

    }
}
