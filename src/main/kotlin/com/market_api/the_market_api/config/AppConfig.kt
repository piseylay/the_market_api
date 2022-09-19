package com.market_api.the_market_api.config
import com.market_api.the_market_api.responseFormat.response.ResponseObjectMap
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor


@Configuration
class AppConfig {
    @Bean
    fun getResponseObj()= ResponseObjectMap()

    @Bean
    @Primary
    fun getTaskExecutor(): TaskExecutor? {
        val threadPoolTaskExecutor = ThreadPoolTaskExecutor()
        threadPoolTaskExecutor.initialize()
        threadPoolTaskExecutor.corePoolSize = 1
        threadPoolTaskExecutor.maxPoolSize = 5
        /*threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true)
        threadPoolTaskExecutor.setAwaitTerminationSeconds(30)*/
        return threadPoolTaskExecutor
    }


}