package com.task.api.taskapi.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;

import java.io.IOException;

public interface IAccountsManagerService {
    String getAuthLink(String userId) throws IOException;
    boolean checkAccountExist(String userId) throws IOException;
    Credential getCredentials(String userCode) throws IOException;

    void addNewCredentials(GoogleTokenResponse response, String userId) throws IOException;
    GoogleTokenResponse getTokenByResponse(String code, String userId) throws IOException;
    String getTeamTaskListFromAccount(String userId);
}
