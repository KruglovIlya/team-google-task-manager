package com.task.api.taskapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class TaskapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskapiApplication.class, args);
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
