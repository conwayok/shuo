package com.conway.shuo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


/** Created by Conway */
@Configuration
@EnableSwagger2
class SwaggerConfig {
  @Bean
  fun api(): Docket {
    return Docket(DocumentationType.SWAGGER_2)
      .securityContexts(listOf(securityContext()))
      .securitySchemes(listOf(apiKey()))
      .select()
      .apis(RequestHandlerSelectors.any())
      .paths(PathSelectors.any())
      .build()
  }

  private fun apiKey(): ApiKey {
    return ApiKey("JWT", "Authorization", "header")
  }

  private fun securityContext(): SecurityContext {
    return SecurityContext.builder().securityReferences(defaultAuth()).build()
  }

  private fun defaultAuth(): List<SecurityReference> {
    val authorizationScope = AuthorizationScope("global", "accessEverything")
    val authorizationScopes: Array<AuthorizationScope> = arrayOf(authorizationScope)
    return listOf(SecurityReference("JWT", authorizationScopes))
  }
}
