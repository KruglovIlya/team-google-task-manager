package com.task.api.taskapi.entity;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@RequiredArgsConstructor
@Document(collection = "Accounts")
@ToString
public class TaskEntity {

}
