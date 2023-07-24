package com.app.cronjobs.cron;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DefaultCron {

    @Scheduled(cron = "*/10 * * * * *")
    public void printMessage() {
        System.out.println("Default: imbond");
    }
}
