package com.task.api.taskapi.service;

import com.google.api.services.tasks.Tasks;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface ITeamTaskManagerService {
    Tasks getTasksService(String userCode) throws GeneralSecurityException, IOException;
}
