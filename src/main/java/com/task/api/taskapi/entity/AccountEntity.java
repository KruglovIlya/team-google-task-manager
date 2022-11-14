package com.task.api.taskapi.entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@RequiredArgsConstructor
@Document(collection = "Accounts")
@ToString
public class AccountEntity {
    @Id
    private final String id;
    @Getter
    private final String name;
    @Getter
    @Setter
    private String taskListId;
}
