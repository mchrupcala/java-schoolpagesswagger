package com.lambdaschool.school.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// http://localhost:2018/swagger-ui.html
@Configuration
@EnableSwagger2
public class Swagger2Config
{
    @Bean
    public Docket api()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.lambdaschool.school"))
                .paths(PathSelectors.any()).build()
                .useDefaultResponseMessages(false) // Allows only my exception responses
                .ignoredParameterTypes(Pageable.class) // allows only my paging parameter list
                .apiInfo(apiEndPointsInfo());
    }

    private ApiInfo apiEndPointsInfo()
    {
        return new ApiInfoBuilder().title("School Example")
                .description("School Example")
                .contact(new Contact("Michael Chrupcala", "http://www.lambdaschool.com", "michael.chrupcala@gmail.com"))
                .license("MIT").licenseUrl("https://github.com/mchrupcala/java-schoolpagesswagger/LICENSE")
                .version("1.0.0").build();
    }
}