package com.task.api.taskapi.entity;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TaskToAddEntity {
    public final String taskName;
    public final List<String> targetUsers;
}
