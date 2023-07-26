//package com.app.cronjobs.quartz;
//import com.app.cronjobs.domain.Cron;
//import org.springframework.stereotype.Component;
//
//import org.quartz.*;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Component
//public class CronJobScheduler {
//
//    private static final Logger LOGGER = Logger.getLogger(CronJobScheduler.class.getName());
//
//    @Autowired
//    private Scheduler scheduler;
//
//    private Map<Long, JobKey> scheduledTasks = new HashMap<>();
//
//    public void scheduleCronJob(Cron cron) {
//        String cronExpression = cron.getExpression();
//        long cronId = cron.getId();
//
//        try {
//            JobDetail jobDetail = JobBuilder.newJob(CronJobExecutor.class)
//                    .withIdentity("cronJob_" + cronId)
//                    .usingJobData("cronId", cronId)
//                    .build();
//
//            Trigger trigger = TriggerBuilder.newTrigger()
//                    .withIdentity("cronTrigger_" + cronId)
//                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
//                    .build();
//
//            scheduler.scheduleJob(jobDetail, trigger);
//
//            scheduledTasks.put(cronId, jobDetail.getKey());
//        } catch (SchedulerException | IllegalArgumentException e) {
//            LOGGER.log(Level.SEVERE, "-> Quartz -> Error scheduling cron job with ID: " + cronId, e);
//        }
//    }
//
//    public void stopCronJob(long cronId) {
//        JobKey jobKey = scheduledTasks.get(cronId);
//        if (jobKey != null) {
//            try {
//                scheduler.deleteJob(jobKey);
//                scheduledTasks.remove(cronId);
//            } catch (SchedulerException e) {
//                LOGGER.log(Level.SEVERE, "-> Quartz -> Error stopping cron job with ID: " + cronId, e);
//            }
//        }
//    }
//
//    public void resumeCronJob(Cron cron) {
//        long cronId = cron.getId();
//        stopCronJob(cronId);
//        scheduleCronJob(cron);
//    }
//}
