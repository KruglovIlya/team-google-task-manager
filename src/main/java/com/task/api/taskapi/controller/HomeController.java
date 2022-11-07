package com.task.api.taskapi.controller;

import com.task.api.taskapi.entity.UserEntity;
import com.task.api.taskapi.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/task")
public class HomeController {

    @Autowired
    IUserService userService;

    @ApiOperation(value = "Test")
    @GetMapping("/get")
    public ResponseEntity get() {
        return new ResponseEntity<String>("fuck",HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Add new user")
    @PostMapping("/add")
    public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity user){
        userService.addUser(user);
        return ResponseEntity.ok(new UserEntity());
    }

}
