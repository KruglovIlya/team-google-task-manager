package com.task.api.taskapi.service.impl;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import com.task.api.taskapi.TaskApiApplication;
import com.task.api.taskapi.entity.TaskEntity;
import com.task.api.taskapi.entity.TaskToAddEntity;
import com.task.api.taskapi.service.IAccountsManagerService;
import com.task.api.taskapi.service.ITeamTaskManagerService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

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
    public boolean addTaskToUserAccount(TaskToAddEntity task) throws IOException, GeneralSecurityException {
        if (!accountsManagerService.checkAccountExist(task.targetUser))
            return false;

        Tasks service = getTasksService(task.targetUser);
        String taskListId = accountsManagerService.getTeamTaskListFromAccount(task.targetUser);

        service.tasks().insert(taskListId, new Task().setTitle(task.taskName)).execute();

        return true;
    }

    @Override
    public List<Task> getListTasksByUserId(String userId) throws GeneralSecurityException, IOException {
        String taskListId = accountsManagerService.getTeamTaskListFromAccount(userId);
        var service = getTasksService(userId);

        return service.tasks().list(taskListId).execute().getItems();
    }
}
