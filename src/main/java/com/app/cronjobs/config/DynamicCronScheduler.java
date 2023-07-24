package com.app.cronjobs.config;

import com.app.cronjobs.domain.Cron;
import com.app.cronjobs.service.CronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Component
public class DynamicCronScheduler {
    private final CronService cronService;
    private final TaskScheduler taskScheduler;
    private final Map<Long, ScheduledFuture<?>> scheduledTasks;


    @Autowired
    public DynamicCronScheduler(CronService cronService, TaskScheduler taskScheduler) {
        this.cronService = cronService;
        this.taskScheduler = taskScheduler;
        this.scheduledTasks = new HashMap<>();
    }

    @PostConstruct
    public void initializeCronJobs() {
        List<Cron> cronJobs = cronService.getActiveCrons();
        cronJobs.forEach(this::scheduleCronJob);
    }

    private void scheduleCronJob(Cron cron) {
        String cronExpression = cron.getExpression();
        long cronId = cron.getId();
        Runnable task = () -> {
            System.out.println("Executing cron job with ID: " + cronId);
        };
        try {
            CronTrigger cronTrigger = new CronTrigger(cronExpression);
            ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(task, cronTrigger);
            scheduledTasks.put(cronId, scheduledFuture);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid cron expression for cron ID: " + cronId);
        }
    }

    public void stopCronJob(long cronId) {
        ScheduledFuture<?> scheduledFuture = scheduledTasks.get(cronId);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            scheduledTasks.remove(cronId);
        }
    }

    public void resumeCronJob(Cron cron) {
        long cronId = cron.getId();
        stopCronJob(cronId);
        scheduleCronJob(cron);
    }

}
