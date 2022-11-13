package com.task.api.taskapi.service.impl;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.tasks.TasksScopes;
import com.task.api.taskapi.controller.TasksController;
import com.task.api.taskapi.repository.IAccountRepository;
import com.task.api.taskapi.service.IAccountsManagerService;
import lombok.Getter;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Service
public class AccountManagerService implements IAccountsManagerService {
    @Getter
    private final GoogleAuthorizationCodeFlow flow;

    @Autowired
    private IAccountRepository accountRepository;

    public AccountManagerService() throws GeneralSecurityException, IOException {
        // Load client secrets.
        String CREDENTIALS_FILE_PATH = "/credentials.json";
        InputStream in = TasksController.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow
        String TOKENS_DIRECTORY_PATH = "tokens";
        List<String> SCOPES = Collections.singletonList(TasksScopes.TASKS_READONLY);
        NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        System.out.println("object service is init");

    }

    @Override
    public String getAuthLink(String userId) throws IOException {
        // Response user auth object
        if (null == flow.loadCredential(userId))
            return flow.newAuthorizationUrl().setRedirectUri("http://localhost:8080/api/authorization/callback/" +
                    URLEncoder.encode(userId, String.valueOf(StandardCharsets.UTF_8)) + "/").build();

        return null;
    }

    @Override
    public boolean checkAccountExist(String userId) throws IOException {
        return flow.loadCredential(userId) != null;
    }

    @Override
    public Credential getCredentials(String userCode) throws IOException {
        return flow.loadCredential(userCode);
    }


    @Override
    public void addNewCredentials(GoogleTokenResponse response, String userId) throws IOException {
        flow.createAndStoreCredential(response, userId);
    }

    @Override
    public GoogleTokenResponse getTokenResponse(String code, String userId) throws IOException {
        return flow.newTokenRequest(code).setRedirectUri("http://localhost:8080/api/authorization/callback/" +
                URLEncoder.encode(userId, String.valueOf(StandardCharsets.UTF_8)) + "/").execute();
    }
}
