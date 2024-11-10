package com.example.scraper.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;



@Configuration
public class SchedulerConfig {
    Logger logger = LoggerFactory.getLogger(SchedulerConfig.class);
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10); // Adjust pool size based on your needs
        scheduler.setThreadNamePrefix("scheduled-task-");
        scheduler.setErrorHandler(t ->
                logger.error("Error occurred while executing scheduled task: ", t)
        );
        scheduler.initialize();
        return scheduler;
    }
}

