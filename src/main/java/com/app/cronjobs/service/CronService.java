package com.app.cronjobs.service;

import com.app.cronjobs.domain.Cron;
import com.app.cronjobs.enums.CronStatus;
import com.app.cronjobs.repository.CronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CronService extends BaseService<Cron, Long> {
    @Autowired
    private CronRepository cronRepository;
    public CronService(CronRepository cronRepository) {
        super(cronRepository);
        this.cronRepository = cronRepository;
    }

    public Cron changeCronStatus(long id, String status) {
        Cron cron = cronRepository.findById(id).get();
        if (cron != null) {
            cron.setStatus(CronStatus.valueOf(status));
            cronRepository.save(cron);
        }
        return cron;
    }

    public List<Cron> getActiveCrons() {
        return cronRepository.findByStatus(CronStatus.START);
    }
}

