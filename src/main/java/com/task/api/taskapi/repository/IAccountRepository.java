package com.task.api.taskapi.repository;

import com.task.api.taskapi.entity.AccountEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "accounts", path = "accounts")
public interface IAccountRepository extends MongoRepository<AccountEntity, String> {
    List<AccountEntity> findByName(@Param("name") String name);
}
