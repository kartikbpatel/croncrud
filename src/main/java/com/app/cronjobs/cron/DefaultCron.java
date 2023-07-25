package com.app.cronjobs.cron;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DefaultCron {
    private static final Logger LOGGER = Logger.getLogger(DefaultCron.class.getName());

    @Scheduled(cron = "*/10 * * * * *")
    public void printMessage() {
        LOGGER.log(Level.INFO, "Default: imbond");
    }

}
