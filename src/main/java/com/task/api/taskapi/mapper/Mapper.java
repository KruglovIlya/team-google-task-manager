package com.task.api.taskapi.mapper;

import com.task.api.taskapi.entity.UserEntity;
import com.task.api.taskapi.models.UserDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory){
        // Маппер моделек
        factory.classMap(UserEntity.class, UserDto.class).byDefault().register();

    }
}
