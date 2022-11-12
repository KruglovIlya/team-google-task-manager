package com.task.api.taskapi.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Accounts")
public class AccountEntity {
    public AccountEntity(String name) {
        this.name = name;
    }

    @Id
    private String id;
    public String name;
}
