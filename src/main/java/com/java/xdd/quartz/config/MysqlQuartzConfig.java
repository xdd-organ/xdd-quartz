package com.java.xdd.quartz.config;

import com.java.xdd.quartz.listen.QuartListen;
import com.java.xdd.quartz.listen.QuartzJobListener;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author xdd
 * @date 2019/10/15
 */
@Configuration
public class MysqlQuartzConfig {
    private Logger logger = LoggerFactory.getLogger(MysqlQuartzConfig.class);

    @Autowired
    private SpringJobFactory springJobFactory;
    @Autowired
    private QuartListen quartListen;
    @Autowired
    private QuartzJobListener quartzJobListener;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setTaskExecutor(initQuartzExecutor());//设置线程池
        bean.setStartupDelay(5);//延时启动 秒
        bean.setSchedulerListeners(quartListen);//监听器
        bean.setGlobalJobListeners(quartzJobListener);
        bean.setJobFactory(springJobFactory);//使spring注入有效

        bean.setDataSource(driverManagerDataSource());
        bean.setQuartzProperties(properties());//如果持久化使用数据库连接
        return bean;
    }

    @Bean
    public Properties properties() {
        Properties properties = new Properties();
        properties.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        properties.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        properties.put("org.quartz.jobStore.dataSource", "myDatasource");
        return properties;
    }

    @Bean
    public DriverManagerDataSource driverManagerDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/quartz");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    @Bean
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }


    @Bean
    public ThreadPoolTaskExecutor initQuartzExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(100);
        executor.setMaxPoolSize(100);
        executor.setKeepAliveSeconds(200);
        executor.setQueueCapacity(2000);
        executor.setTaskDecorator(new MDCTaskDecorator());
        executor.setThreadNamePrefix("MDCRunner-");
        executor.setRejectedExecutionHandler(
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        logger.warn("任务线程池队列已满.");
                    }
                }
        );
        executor.initialize();
        return executor;
    }

    //如果是子线程，集成父线程MDC数据
    public class MDCTaskDecorator implements TaskDecorator {
        public MDCTaskDecorator() {
        }

        public Runnable decorate(Runnable runnable) {
            return new MDCTaskDecorator.MDCContinueRunableDecorator(runnable);
        }

        protected class MDCContinueRunableDecorator implements Runnable {
            private final Runnable delegate;
            protected final Map<String, String> logContextMap;

            public MDCContinueRunableDecorator(Runnable runnable) {
                this.delegate = runnable;
                this.logContextMap = MDC.getCopyOfContextMap();
            }

            public void run() {
                if (this.logContextMap != null) {
                    MDC.setContextMap(this.logContextMap);
                }
                this.delegate.run();
                MDC.clear();
            }
        }
    }
}
