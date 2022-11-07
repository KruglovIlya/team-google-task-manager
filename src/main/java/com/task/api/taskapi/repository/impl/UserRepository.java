package com.task.api.taskapi.repository.impl;

import com.task.api.taskapi.entity.UserEntity;
import com.task.api.taskapi.repository.IUserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserRepository implements IUserRepository {

    @Override
    public void inputUser(UserEntity user) {
        //Твоя реализация метода
    }
}
