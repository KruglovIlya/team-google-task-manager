package com.task.api.taskapi;

import com.task.api.taskapi.entity.AccountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


@SpringBootApplication
@EnableSwagger2
public class TaskApiApplication {
	private static final String APPLICATION_NAME = "Google Tasks API Java Quickstart";


	public static String getApplicationName(){
		return APPLICATION_NAME;
	}

	public static void main(String[] args) {
		SpringApplication.run(TaskApiApplication.class, args);

	}

	@Bean
	public Docket weatherApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.task.api.taskapi")).build()
				.apiInfo(apiInfo())
				.useDefaultResponseMessages(false);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Task")
				.description("API Task")
				.license("License")
				.licenseUrl("https://unlicense.org/")
				.termsOfServiceUrl("")
				.version(getClass().getPackage().getImplementationVersion())
				.build();
	}

}
