
package com.devjoliveira.appointmentmanagementapi.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenApi() {
    return new OpenAPI()
        .info(new Info()
            .title("APPOINTMENT MANAGEMENT API")
            .description(
                """
                    API for managing appointments system, including:
                      - Professionals,
                      - Customers,
                      - Products,
                      - Metrics.

                    Project developed by DevJoliveira, using tecnologies such as:
                      - Java,
                      - Spring Boot,
                      - Spring Data JPA,
                      - Hibernate Validator,
                      - PostgreSQL,
                      - OpenAPI for documentation.
                    """)
            .version("v1.0.0")
            .contact(new Contact()
                .name("Jonathan Oliveira")
                .email("devjoliveira@gmail.com"))
            .license(new License()
                .name("Apache License Version 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0")))
        .servers(List.of(
            new Server()
                .url("http://localhost:8080")
                .description("Local server")))
        .externalDocs(new ExternalDocumentation()
            .description("Project documentation")
            .url("https://github.com/oliveira-jo/appointment-management-platform"));
  }

}