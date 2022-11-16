package com.task.api.taskapi.controller;

import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.model.Task;
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

    @ApiOperation(value = "Add new task to users")
    @PostMapping("/add")
    public ResponseEntity addTask(@RequestBody TaskToAddEntity task) throws GeneralSecurityException, IOException {
        return ResponseEntity.ok(teamTaskManagerService.addTaskToUserAccount(task));
    }

    @ApiOperation(value = "Get task by userCode")
    @GetMapping(value = "/list", params = {"userName"})
    public ResponseEntity getList(@RequestParam String userName) throws IOException, GeneralSecurityException {

        if (!accountsManagerService.checkAccountExist(userName))
            return ResponseEntity.badRequest().body("Account not found in secrets");

        String taskListId = accountsManagerService.getTeamTaskListNameFromAccount(userName);

        Tasks service = teamTaskManagerService.getTasksService(userName);

        List<Task> taskList = service.tasks().list(taskListId).execute().getItems();

        if (taskList == null || taskList.isEmpty()) {
            return new ResponseEntity<>("No task lists found.", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(taskList, HttpStatus.ACCEPTED);
        }

    }
}
