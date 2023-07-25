package com.app.cronjobs.cron;
import com.app.cronjobs.repository.CronRepository;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.quartz.*;

@Component
public class CronJobScheduler {
    private CronRepository cronRepository;

//    @Scheduled(fixedDelay = 1000)
//    public void runCronJobs() {
//        List<Cron> runningCronJobs = cronRepository.findByStatus(CronStatus.START);
//
//        for (Cron cron : runningCronJobs) {
//            try {
//                if (isCronExpressionMatched(cron.getExpression())) {
//                    System.out.println("Running cron job with id: " + cron.getId());
//                }
//            } catch (Exception e) {
//                System.err.println("Error executing cron job with id: " + cron.getId());
//                e.printStackTrace();
//            }
//        }
//    }
    private boolean isCronExpressionMatched(String cronExpression) throws ParseException, SchedulerException {
        CronExpression cronExpr = new CronExpression(cronExpression);
        Date currentTime = new Date();

        // Find the next matching time after the current time
        Date nextExecutionTime = cronExpr.getNextValidTimeAfter(currentTime);

        // Compare the next execution time with the current time
        return nextExecutionTime != null && !nextExecutionTime.after(currentTime);
    }

//    private void startCronJob(Cron cron) {
//        Runnable task = () -> {
//            // Your code to execute the cron job logic goes here
//            System.out.println("Running cron job with id: " + cron.getId());
//        };
//
//        executorService.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS); // Adjust the delay and period as needed
//    }
}
