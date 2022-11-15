package com.task.api.taskapi.service.impl;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import com.task.api.taskapi.TaskApiApplication;
import com.task.api.taskapi.entity.TaskToAddEntity;
import com.task.api.taskapi.service.IAccountsManagerService;
import com.task.api.taskapi.service.ITeamTaskManagerService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeamTasksManagerService implements ITeamTaskManagerService {
    private final String TEAM_TASK_LIST = "Team task list";

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

    @Override
    public TaskList initTeamTasksListOfAccount(String userCode) throws GeneralSecurityException, IOException {
        Tasks service = getTasksService(userCode);

        TaskList teamList = new TaskList().setTitle(TEAM_TASK_LIST);

        var taskListResult = service.tasklists().insert(teamList).execute();

        return taskListResult;
    }

    @Override
    public Map<String, Boolean> addTaskToUserAccount(TaskToAddEntity task) throws IOException, GeneralSecurityException {
        Map<String, Boolean> result = new HashMap<>();

        for (var user : task.targetUsers) {
            if (!accountsManagerService.checkAccountExist(user))
                result.put(user, false);

            Tasks service = getTasksService(user);
            String taskListId = accountsManagerService.getTeamTaskListNameFromAccount(user);
            service.tasks().insert(taskListId, new Task().setTitle(task.taskName)).execute();

            result.put(user, true);
        }

        return result;
    }
}
