package com.task.api.taskapi.service.impl;

import com.task.api.taskapi.entity.UserEntity;
import com.task.api.taskapi.repository.IUserRepository;
import com.task.api.taskapi.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Override
    public void addUser(UserEntity user) {
        //Твоя реальзация
        userRepository.inputUser(user);
    }
}
