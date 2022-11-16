package com.task.api.taskapi.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class TaskToAddEntity {
    private final String taskName;
    private final List<String> targetUsers;
}
