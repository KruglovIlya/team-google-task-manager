package com.task.api.taskapi.controller;

import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import com.task.api.taskapi.entity.TaskToAddEntity;
import com.task.api.taskapi.service.IAccountsManagerService;
import com.task.api.taskapi.service.ITeamTaskManagerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;


@Controller
@RequestMapping("api/tasks")
public class TasksController {
    @Autowired
    private IAccountsManagerService accountsManagerService;

    @Autowired
    private ITeamTaskManagerService teamTaskManagerService;

    TasksController() {
    }

    @ApiOperation(value = "Add new task to user")
    @PostMapping("/add")
    public ResponseEntity addTask(@RequestBody TaskToAddEntity task) throws GeneralSecurityException, IOException {
        if (teamTaskManagerService.addTaskToUserAccount(task))
            return new ResponseEntity("ok", HttpStatus.ACCEPTED);

        return new ResponseEntity("Error! Account not exist in database!", HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Test")
    @GetMapping(value = "/get", params = {"userCode"})
    public ResponseEntity test(@RequestParam String userCode) throws IOException, GeneralSecurityException {

        if (!accountsManagerService.checkAccountExist(userCode))
            return ResponseEntity.ok("Account not found in secrets");


        Tasks service = teamTaskManagerService.getTasksService(userCode);

        // Print the first 10 task lists.
        TaskList result = service.tasklists().get("").execute();

        List<Task> taskList = service.tasks().list("Арены").execute().getItems();

        if (taskList == null || taskList.isEmpty()) {
            return new ResponseEntity("No task lists found.", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(taskList, HttpStatus.ACCEPTED);
        }

    }
}
