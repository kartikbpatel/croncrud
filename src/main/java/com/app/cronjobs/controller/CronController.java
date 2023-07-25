package com.app.cronjobs.controller;
import com.app.cronjobs.dto.CreateCronDTO;
import com.app.cronjobs.config.DynamicCronScheduler;
import com.app.cronjobs.cron.DefaultCron;
import com.app.cronjobs.domain.Cron;
import com.app.cronjobs.service.CronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/crons")
public class CronController {

    private final CronService cronService;
    private final DefaultCron defaultCron;
    private final DynamicCronScheduler dynamicCronScheduler; // Inject the DynamicCronScheduler bean

    public CronController(CronService cronService, DefaultCron defaultCron, DynamicCronScheduler dynamicCronScheduler) {
        this.cronService = cronService;
        this.defaultCron = defaultCron;
        this.dynamicCronScheduler = dynamicCronScheduler;
    }

    @PostMapping
    public ResponseEntity<Cron> createCron(@RequestBody CreateCronDTO createCronDTO) {
        Cron cron = Cron.builder()
                .title(createCronDTO.getTitle())
                .expression(createCronDTO.getExpression())
                .build();
        cron = cronService.save(cron);
        return ResponseEntity.ok(cron);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cron> getCronById(@PathVariable Long id) {
        Cron cron = cronService.findById(id);
        if (cron != null) {
            return ResponseEntity.ok(cron);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cron> changeCronConfig(@PathVariable long id, @RequestBody CreateCronDTO createCronDTO) {
        Cron cron = cronService.findById(id);
        if (cron != null) {
            cron.setExpression(createCronDTO.getExpression());
            cron = cronService.save(cron);
            return ResponseEntity.ok(cron);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Cron> changeCronStatus(@PathVariable long id, @RequestParam String status) {
        Cron cron = cronService.changeCronStatus(id, status);
        if (cron != null) {
            if ("STOP".equals(status)) {
                stopCronJob(cron);
            } else if ("START".equals(status)) {
                resumeCronJob(cron);
            }
            return ResponseEntity.ok(cron);
        } else {
            return ResponseEntity.notFound().build();
        }
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
