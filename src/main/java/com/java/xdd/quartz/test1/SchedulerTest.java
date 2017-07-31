package com.java.xdd.quartz.test1;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.quartz.spi.MutableTrigger;

import java.util.Date;

/**
 * Created by qw on 2017/7/10.
 */
public class SchedulerTest {
    String jobName = "name1";
    String jobGroup = "group1";
    String triggerName = "name2";
    String triggerGroup = "group2";

    //基于simpleTrigger
    @Test
    public void test1() throws Exception {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();

        JobKey jobKey = new JobKey(jobName, jobGroup); //job
        JobDetail job = JobBuilder.newJob(TestJob.class).withIdentity(jobKey).build();

        TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
        Trigger trigger = TriggerBuilder.newTrigger() //触发器
                                        .withIdentity(triggerKey)
                                        .startAt(new Date()) //开始时间
                                        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                                                .withIntervalInSeconds(1) //指定一个重复间隔,以秒/分/小时为单位。
                                                .withRepeatCount(10)) //指定反复的次数
                                        .build();

        scheduler.scheduleJob(job, trigger); //开始任务

        Thread.sleep(5000);

        scheduler.pauseJob(jobKey); //暂停任务
        Thread.sleep(5000);

        scheduler.shutdown();
    }

    @Test
    public void test2() throws Exception {
        String jobName = "name3";
        String jobGroup = "group1";
        String triggerName = "name4";
        String triggerGroup = "group2";

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();

        JobKey jobKey = new JobKey(jobName, jobGroup); //job
        JobDetail job = JobBuilder.newJob(TestJob.class).withIdentity(jobKey).build();

        TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
        Trigger trigger = TriggerBuilder.newTrigger()
                                        .withIdentity(triggerKey)
                                        .withSchedule(CronScheduleBuilder.cronSchedule("*/1 * * * * ?"))
                                        .build();

        scheduler.scheduleJob(job, trigger); //开始任务

        Thread.sleep(5000);

        scheduler.pauseJob(jobKey); //暂停任务
        Thread.sleep(5000);

        scheduler.shutdown();
    }
}
