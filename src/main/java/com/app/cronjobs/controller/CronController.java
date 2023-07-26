package com.app.cronjobs.controller;

import com.app.cronjobs.domain.Cron;
import com.app.cronjobs.dto.CreateCronDTO;
import com.app.cronjobs.enums.CronStatus;
import com.app.cronjobs.quartz.CronJobService;
import com.app.cronjobs.service.CronService;
import org.quartz.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cron")
public class CronController {

    private final CronService cronService;
    private final CronJobService cronJobService;
    private final Scheduler scheduler;

    public CronController(CronService cronService, CronJobService cronJobService, Scheduler scheduler) {
        this.cronService = cronService;
        this.cronJobService = cronJobService;
        this.scheduler = scheduler;
    }

    @PostMapping
    public ResponseEntity<Cron> createCron(@RequestBody CreateCronDTO createCronDTO) {
        Cron cron = Cron.builder()
                .title(createCronDTO.getTitle())
                .expression(createCronDTO.getExpression())
                .status(CronStatus.STOP)
                .build();
        cron = cronService.save(cron);
        return ResponseEntity.ok(cron);
    }

    @PutMapping("/{cronId}/start")
    public ResponseEntity<String> startCron(@PathVariable Long cronId) {
        Cron cron = cronService.findById(cronId);
        if (cron == null) {
            return new ResponseEntity<>("Cron not found", HttpStatus.NOT_FOUND);
        }

        if (cron.getStatus() == CronStatus.START) {
            return new ResponseEntity<>("Cron is already active", HttpStatus.OK);
        }

        cron.setStatus(CronStatus.START);
        cronService.save(cron);

        try {
            JobKey jobKey = new JobKey(cron.getId().toString());
            if (scheduler.checkExists(jobKey)) {
                scheduler.deleteJob(jobKey);
            }
            JobDetail jobDetail = cronJobService.createJobDetail(cron);
            CronTrigger cronTrigger = cronJobService.createCronTrigger(cron, jobDetail);
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (
                SchedulerException e) {
            return new ResponseEntity<>("Failed to start cron", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Cron started successfully", HttpStatus.OK);
    }

    @PutMapping("/{cronId}/stop")
    public ResponseEntity<String> stopCron(@PathVariable Long cronId) {
        Cron cron = cronService.findById(cronId);
        if (cron == null) {
            return new ResponseEntity<>("Cron not found", HttpStatus.NOT_FOUND);
        }

        if (cron.getStatus() == CronStatus.STOP) {
            return new ResponseEntity<>("Cron is already inactive", HttpStatus.OK);
        }

        cron.setStatus(CronStatus.STOP);
        cronService.save(cron);

        try {
            JobKey jobKey = new JobKey(cron.getId().toString());
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            return new ResponseEntity<>("Failed to stop cron", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Cron stopped successfully", HttpStatus.OK);
    }

    @PutMapping("/{cronId}")
    public ResponseEntity<Cron> changeCronConfig(@PathVariable long cronId, @RequestBody CreateCronDTO createCronDTO) {
        Cron cron = cronService.findById(cronId);
        if (cron != null) {
            cron.setExpression(createCronDTO.getExpression());
            cron = cronService.save(cron);
            return ResponseEntity.ok(cron);
        } else {
            return ResponseEntity.notFound().build();
        }
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
}
