package com.app.cronjobs.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class MyJob implements Job {

    private final static Logger LOGGER = Logger.getLogger(MyJob.class.getName());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.log(Level.INFO, "-> Quartz -> Default: imbond");
    }
}