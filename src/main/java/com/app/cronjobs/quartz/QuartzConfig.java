package com.app.cronjobs.quartz;
import com.app.cronjobs.domain.Cron;
import com.app.cronjobs.service.CronService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class QuartzConfig {

    private final CronService cronService;
    private final CronJobService cronJobService;

    public QuartzConfig(CronService cronService, CronJobService cronJobService) {
        this.cronService = cronService;
        this.cronJobService = cronJobService;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(getAllCronTriggers());
        schedulerFactoryBean.setAutoStartup(true);
        return schedulerFactoryBean;
    }

    public Trigger[] getAllCronTriggers() {
        List<Cron> activeCrons = cronService.getActiveCrons();
        List<Trigger> triggers = new ArrayList<>();

        for (Cron cron : activeCrons) {
            JobDetail jobDetail = cronJobService.createJobDetail(cron);
            CronTrigger cronTrigger = cronJobService.createCronTrigger(cron, jobDetail);
            triggers.add(cronTrigger);
        }

        return triggers.toArray(new Trigger[0]);
    }
}