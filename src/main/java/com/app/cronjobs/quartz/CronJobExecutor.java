//package com.app.cronjobs.quartz;
//
//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.springframework.stereotype.Component;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Component
//public class CronJobExecutor implements Job {
//
//    private static final Logger LOGGER = Logger.getLogger(CronJobExecutor.class.getName());
//
//    @Override
//    public void execute(JobExecutionContext context) throws JobExecutionException {
//        long cronId = context.getJobDetail().getJobDataMap().getLong("cronId");
//        LOGGER.log(Level.INFO, "-> Quartz -> Executing cron job with ID: " + cronId);
//    }
//}
