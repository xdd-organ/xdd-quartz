package com.java.xdd.quartz.controller;

import com.java.xdd.quartz.test1.TestJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author xdd
 * @date 2019/10/15
 */
@RequestMapping("test")
@RestController
public class TestController {

    @Autowired
    private Scheduler scheduler;

    String jobName = "name1";
    String jobGroup = "group1";
    String triggerName = "name2";
    String triggerGroup = "group2";

    @RequestMapping("test")
    public void test() throws Exception{
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
    }
}
