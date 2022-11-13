package com.task.api.taskapi.controller;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.model.TaskList;
import com.google.api.services.tasks.model.TaskLists;
import com.task.api.taskapi.TaskApiApplication;
import com.task.api.taskapi.service.IAccountsManagerService;
import io.swagger.annotations.ApiOperation;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;


@Controller
@RequestMapping("api/google-tasks")
public class TasksController {
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    @Autowired
    private IAccountsManagerService accountsManagerService;

    TasksController() {
    }
    /**
     * Creates an authorized Credential object.
     *
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */

    private Tasks getTasksService(String userCode) throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        var credential = accountsManagerService.getCredentials(userCode);

        Tasks service = new Tasks.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(TaskApiApplication.getAPPLICATION_NAME())
                .build();

        return service;
    }

    @ApiOperation(value = "Test")
    @GetMapping(value = "/get", params = {"userCode"})
    public ResponseEntity test(@RequestParam String userCode) throws IOException, GeneralSecurityException {

        if (!accountsManagerService.checkAccountExist(userCode))
            return ResponseEntity.ok("Account not found in secrets");


        Tasks service = getTasksService(userCode);

        // Print the first 10 task lists.
        TaskLists result = service.tasklists().list()
                .setMaxResults(10)
                .execute();

        List<TaskList> taskLists = result.getItems();
        if (taskLists == null || taskLists.isEmpty()) {
            return new ResponseEntity("No task lists found.", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(taskLists, HttpStatus.ACCEPTED);
        }

    }
}
