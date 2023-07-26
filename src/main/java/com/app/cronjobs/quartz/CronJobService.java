package com.app.cronjobs.quartz;

import com.app.cronjobs.domain.Cron;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Service;

import java.text.ParseException;
@Service
public class CronJobService {

    public JobDetail createJobDetail(Cron cron) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(MyJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName(cron.getId().toString());
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    public CronTrigger createCronTrigger(Cron cron, JobDetail jobDetail){
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setCronExpression(cron.getExpression());
        factoryBean.setName(jobDetail.getKey().getName());
        factoryBean.setJobDetail(jobDetail);
        try {
            factoryBean.afterPropertiesSet();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return factoryBean.getObject();
    }

}
