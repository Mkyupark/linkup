package com.chippo.linkup.global.config;

import com.chippo.linkup.global.decorator.MdcTaskDecorator;
import com.chippo.linkup.global.exception.AsyncExceptionHandler;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


// 비동기 처리 => 다중 thread 사용
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    // 비동기 작업 수행 함수
    @Bean
    public TaskExecutor taskExecutor() {
        final ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(8);
        taskExecutor.setTaskDecorator(new MdcTaskDecorator());
        taskExecutor.setThreadNamePrefix("async-task-");
        return taskExecutor;
    }

    // 비동기 작업중 예외 처리
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }

}
