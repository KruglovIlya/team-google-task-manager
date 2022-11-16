package com.task.api.taskapi.controller;

import com.task.api.taskapi.DTO.TaskDTO;
import com.task.api.taskapi.entity.TaskToAddEntity;
import com.task.api.taskapi.service.IAccountsManagerService;
import com.task.api.taskapi.service.ITeamTaskManagerService;
import io.swagger.annotations.ApiOperation;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
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

    @ApiOperation(value = "Get list tasks")
    @GetMapping(value = "/list", params = {"userId"})
    public ResponseEntity getList(@RequestParam String userId) throws GeneralSecurityException, IOException {
        List<TaskDTO> result = new ArrayList<>();
        for (var task : teamTaskManagerService.getListTasksByUserId(userId)) {
            result.add(new TaskDTO(task.getId(), task.getTitle(), task.getStatus()));
        }

        return ResponseEntity.ok(result);
    }
}
