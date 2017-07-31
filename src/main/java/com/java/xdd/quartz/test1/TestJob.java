package com.java.xdd.quartz.test1;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by qw on 2017/7/10.
 */
public class TestJob implements Job{

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("执行job方法！");
    }

}
