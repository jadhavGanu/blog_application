//package com.project.blogging.config;
//
//import java.util.Collections;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//@Configuration
//public class SwaggerConfig {
//
////	@Bean
////	public Docket api() {
////
////		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo()).select().apis(RequestHandlerSelectors.any())
////				.paths(PathSelectors.any()).build();
////	}
////
////	private ApiInfo getInfo() {
////		return new ApiInfo("Blogging Application : Backend Course ", "This is developed by me", "1.0",
////				"Terms of service", new Contact("Durgesh", "https://code.com", "me123@gmail.com"),
////				"Licence of APIS", "Api License url", Collections.EMPTY_LIST);
////	};

package com.project.blogging.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import springfox.documentation.service.ApiKey;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.project.blogging.payload.AppConstants;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Security Scheme define
        SecurityScheme securityScheme = new SecurityScheme()
                .name(AppConstants.AUTHORIZATION_HEADER)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        // Security Requirement add
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(AppConstants.AUTHORIZATION_HEADER);

        return new OpenAPI()
                .components(new Components().addSecuritySchemes(AppConstants.AUTHORIZATION_HEADER, securityScheme))
                .info(new Info()
                        .title("Blogging Application: Backend API")
                        .description("This is developed by Ganesh.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Ganesh")
                                .url("https://code.com")
                                .email("ganesh123@gmail.com")))
                .addSecurityItem(securityRequirement);
    }
}

