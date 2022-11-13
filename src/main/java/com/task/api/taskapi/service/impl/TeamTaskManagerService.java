package com.task.api.taskapi.service.impl;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import com.google.api.services.tasks.Tasks;
import com.task.api.taskapi.TaskApiApplication;
import com.task.api.taskapi.service.IAccountsManagerService;
import com.task.api.taskapi.service.ITeamTaskManagerService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class TeamTaskManagerService implements ITeamTaskManagerService {
    @Autowired
    private IAccountsManagerService accountsManagerService;

    @Override
    public Tasks getTasksService(String userCode) throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        var credential = accountsManagerService.getCredentials(userCode);

        return new Tasks.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(TaskApiApplication.getAPPLICATION_NAME())
                .build();
    }
}
