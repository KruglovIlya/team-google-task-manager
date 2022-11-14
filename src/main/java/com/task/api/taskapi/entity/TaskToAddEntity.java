package com.task.api.taskapi.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TaskToAddEntity {
    public final String taskName;
    public final String targetUser;
}
