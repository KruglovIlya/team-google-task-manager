package com.task.api.taskapi.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class TaskDTO {
    @Getter
    final String taskId;
    @Getter
    final String name;
    @Getter
    final String status;
}
