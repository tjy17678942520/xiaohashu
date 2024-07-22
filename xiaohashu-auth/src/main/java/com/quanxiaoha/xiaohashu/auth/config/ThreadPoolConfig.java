package com.quanxiaoha.xiaohashu.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author TuJiayuan
 * @Date 2024/7/22 14:12
 * @Version 1.0
 */
@Configuration
public class ThreadPoolConfig {

    /**
     * @Description
     * @Param
     * @Return
     */
    @Bean
    public Executor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心数
        executor.setCorePoolSize(10);
        // 最大线程数
        executor.setMaxPoolSize(50);
        // 队列容量
        executor.setQueueCapacity(200);
        // 线程活跃时间
        executor.setKeepAliveSeconds(30);
        // 线程名称前缀
        executor.setThreadNamePrefix("taskExecutor-");
        // 设置拒绝策略:由调用线程处理（一般为主线程）
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //等待所有任务结束后在关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //设置等待时间，如果超过这个时间还没有销毁就强制销毁，以确保应用能关
        executor.setAwaitTerminationSeconds(60);

        executor.initialize();
        return executor;
    }

}
