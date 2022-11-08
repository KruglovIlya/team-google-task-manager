package com.task.api.taskapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.task.api.taskapi.entity.UserEntity;
import com.task.api.taskapi.service.IUserService;
import io.swagger.annotations.ApiOperation;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("api/task")
public class HomeController {

    @Autowired
    IUserService userService;

    @ApiOperation(value = "Test")
    @GetMapping("/get")
    public ResponseEntity get() throws JsonProcessingException {
        var result = new ArrayList<String>();
        result.add("test");
        result.add("Test two");

        return new ResponseEntity(result, HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Add new user")
    @PostMapping("/add")
    public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity user){
        userService.addUser(user);
        return ResponseEntity.ok(new UserEntity());
    }

}
