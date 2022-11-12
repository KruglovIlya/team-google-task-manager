package com.task.api.taskapi.repository;

import com.task.api.taskapi.entity.AccountEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IAccountRepository extends MongoRepository<AccountEntity, String> {
}
