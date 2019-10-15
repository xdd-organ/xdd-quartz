package com.java.xdd.quartz.test1;

import com.java.xdd.quartz.service.TestService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by qw on 2017/7/10.
 */
public class TestJob implements Job{

    private Logger logger = LoggerFactory.getLogger(TestJob.class);

    @Autowired
    private TestService testService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("执行job方法！");
        testService.test();//注入testService 执行相关业务操作
    }

}
