package com.java.xdd.quartz.listen;

import com.java.xdd.quartz.service.TestService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xdd
 * @date 2019/10/15
 */
@Component
public class QuartListen implements SchedulerListener {

    private Logger logger = LoggerFactory.getLogger(QuartListen.class);

    @Autowired
    private TestService testService;

    @Override
    public void jobScheduled(Trigger trigger) {
        logger.info("Scheduler 在有新的 JobDetail 部署时调用此方法");
    }

    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        logger.info("Scheduler 在有新的 JobDetail卸载时调用此方法");
    }

    @Override
    public void triggerFinalized(Trigger trigger) {
        logger.info("Scheduler 调用这个方法是 当一个 Trigger 来到了再也不会触发的状态时调用这个方法。除非这个 Job 已设置成了持久性，否则它就会从 Scheduler 中移除。");
    }

    @Override
    public void triggerPaused(TriggerKey triggerKey) {
        logger.info("Scheduler 调用这个方法是发生在一个 Trigger 或 Trigger 组被暂停时。假如是 Trigger 组的话，triggerName 参数将为 null。");
    }

    @Override
    public void triggersPaused(String s) {
        logger.info("2Scheduler 调用这个方法是发生在一个 Trigger 或 Trigger 组被暂停时。假如是 Trigger 组的话，triggerName 参数将为 null。");
    }

    @Override
    public void triggerResumed(TriggerKey triggerKey) {
        logger.info("Scheduler 调用这个方法是发生成一个 Trigger 或 Trigger 组从暂停中恢复时。假如是 Trigger 组的话，triggerName 参数将为 null。");
    }

    @Override
    public void triggersResumed(String s) {
        logger.info("2Scheduler 调用这个方法是发生成一个 Trigger 或 Trigger 组从暂停中恢复时。假如是 Trigger 组的话，triggerName 参数将为 null。");
    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
        logger.info("Scheduler 调用这个方法是 一个新的任务被动态添加时执行。");
    }

    @Override
    public void jobDeleted(JobKey jobKey) {
        logger.info("Scheduler 调用这个方法是 一个新的任务被动态删除时执行");
    }

    @Override
    public void jobPaused(JobKey jobKey) {
        logger.info("Scheduler 调用这个方法是 当一个或一组 JobDetail 暂停时调用这个方法。");
    }

    @Override
    public void jobsPaused(String s) {
        logger.info("2Scheduler 调用这个方法是 当一个或一组 JobDetail 暂停时调用这个方法。");
    }

    @Override
    public void jobResumed(JobKey jobKey) {
        logger.info("Scheduler 调用这个方法是 当一个或一组 Job 从暂停上恢复时调用这个方法。假如是一个 Job 组，jobName 参数将为 null。");
    }

    @Override
    public void jobsResumed(String s) {
        logger.info("2Scheduler 调用这个方法是 当一个或一组 Job 从暂停上恢复时调用这个方法。假如是一个 Job 组，jobName 参数将为 null。");
    }

    @Override
    public void schedulerError(String s, SchedulerException e) {
        logger.info("Scheduler 的正常运行期间产生一个严重错误时调用这个方法。");
    }

    @Override
    public void schedulerInStandbyMode() {
        logger.info("Scheduler 调用这个方法是 schedulerInStandbyMode");
    }

    @Override
    public void schedulerStarted() {
        logger.info("Scheduler 调用这个方法是 schedulerStarted");
    }

    @Override
    public void schedulerStarting() {
        logger.info("Scheduler 调用这个方法是 schedulerStarting");
    }

    @Override
    public void schedulerShutdown() {
        logger.info("Scheduler 调用这个方法用来通知 SchedulerListener Scheduler 将要被关闭。");
    }

    @Override
    public void schedulerShuttingdown() {
        logger.info("scheduler 正在关闭时被执行");
    }

    @Override
    public void schedulingDataCleared() {
        logger.info("Scheduler 调用这个方法是 schedulingDataCleared");
    }
}
