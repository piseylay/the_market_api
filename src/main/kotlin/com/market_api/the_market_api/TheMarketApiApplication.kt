package com.market_api.the_market_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import th.co.geniustree.springdata.jpa.repository.support.JpaSpecificationExecutorWithProjectionImpl

@SpringBootApplication/*(exclude = [SecurityAutoConfiguration::class])*/
@EnableJpaRepositories(repositoryBaseClass = JpaSpecificationExecutorWithProjectionImpl::class)

class TheMarketApiApplication : SpringBootServletInitializer() {
    override fun configure(builder: SpringApplicationBuilder): SpringApplicationBuilder {
        return builder.sources(TheMarketApiApplication::class.java)
    }
}

fun main(args: Array<String>) {
    runApplication<TheMarketApiApplication>(*args)
}
