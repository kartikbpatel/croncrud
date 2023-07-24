package com.app.cronjobs.controller;
import com.app.cronjobs.DTO.CreateCronDTO;
import com.app.cronjobs.config.DynamicCronScheduler;
import com.app.cronjobs.cron.DefaultCron;
import com.app.cronjobs.domain.Cron;
import com.app.cronjobs.service.CronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/crons")
public class CronController {

    private final CronService cronService;
    private final DefaultCron defaultCron;
    private final DynamicCronScheduler dynamicCronScheduler; // Inject the DynamicCronScheduler bean

    @Autowired
    public CronController(CronService cronService, DefaultCron defaultCron, DynamicCronScheduler dynamicCronScheduler) {
        this.cronService = cronService;
        this.defaultCron = defaultCron;
        this.dynamicCronScheduler = dynamicCronScheduler;
    }

    @PostMapping
    public Cron createCron(@RequestBody CreateCronDTO createCronDTO) {
        Cron cron = new Cron();
        cron.setTitle(createCronDTO.getTitle());
        cron.setExpression(createCronDTO.getExpression());
        cron = cronService.save(cron);
        return cron;
    }

    @GetMapping("/{id}")
    public Cron getCronById(@PathVariable Long id) {
        return cronService.findById(id);
    }

    @PutMapping("/{id}")
    public Cron changeCronConfig(@PathVariable long id, @RequestBody CreateCronDTO createCronDTO) {
        Cron cron = cronService.findById(id);
        cron.setExpression(createCronDTO.getExpression());
        cron = cronService.save(cron);
        return cron;
    }

    @PutMapping("/{id}/status")
    public Cron changeCronStatus(@PathVariable long id, @RequestParam String status) {
        Cron cron = cronService.changeCronStatus(id, status);
        if ("STOP".equals(status)) {
            stopCronJob(cron);
        } else if ("START".equals(status)) {
            resumeCronJob(cron);
        }
        return cron;
    }

    // Method to stop the cron job
    private void stopCronJob(Cron cron) {
        dynamicCronScheduler.stopCronJob(cron.getId());
    }

    // Method to resume the cron job
    private void resumeCronJob(Cron cron) {
        dynamicCronScheduler.resumeCronJob(cron);
    }
}
