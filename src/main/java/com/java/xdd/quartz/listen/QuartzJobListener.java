package com.java.xdd.quartz.listen;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author xdd
 * @date 2019/10/15
 */
@Component
public class QuartzJobListener implements JobListener {

    private Logger logger = LoggerFactory.getLogger(QuartzJobListener.class);

    @Override
    public String getName() {
        return "JobListener_QuartzJobListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        logger.info("Scheduler 在 JobDetail 将要被执行时调用这个方法");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
        logger.info("Scheduler 在 JobDetail 即将被执行，但又被 TriggerListener否决了时调用这个方法。");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        logger.info("Scheduler 在 JobDetail 被执行之后调用这个方法。");
    }
}
