package com.market_api.the_market_api.config

import com.market_api.the_market_api.utils.SwaggerConstant
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SpringFoxConfig {
    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .groupName("the_store-api")
            .apiInfo(apiInfo())
            .securityContexts(listOf(securityContext()))
            .securitySchemes(listOf(apiKey()))
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()
    }
    val AUTHORIZATION_HEADER = "Authorization"

    private fun apiInfo(): ApiInfo {
        return ApiInfo(
            SwaggerConstant.TITLE,
            SwaggerConstant.DESCRIPTION,
            SwaggerConstant.VERSION,
            SwaggerConstant.TERM_OF_SERVICE_URL,
            Contact(SwaggerConstant.CONTACT_NAME, SwaggerConstant.CONTACT_URL, SwaggerConstant.CONTACT_EMAIL),
            SwaggerConstant.CONTACT_VERSION, SwaggerConstant.CONTACT_URL_VERSION,
        )
    }
    fun apiKey(): ApiKey? {
        return ApiKey("Authorization", AUTHORIZATION_HEADER, "header")
    }

    fun securityContext(): SecurityContext? {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .build()
    }

    fun defaultAuth(): List<SecurityReference?>? {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes: Array<AuthorizationScope?> = arrayOfNulls(1)
        authorizationScopes[0] = authorizationScope
        return listOf(SecurityReference("Authorization", authorizationScopes))
    }
}