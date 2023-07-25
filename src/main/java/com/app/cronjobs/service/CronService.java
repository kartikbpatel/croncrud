package com.app.cronjobs.service;

import com.app.cronjobs.domain.Cron;
import com.app.cronjobs.enums.CronStatus;
import com.app.cronjobs.exceptions.ResourceNotFoundException;
import com.app.cronjobs.repository.CronRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CronService extends BaseService<Cron, Long> {
    private CronRepository cronRepository;
    public CronService(CronRepository cronRepository) {
        super(cronRepository);
        this.cronRepository = cronRepository;
    }

    public Cron changeCronStatus(long id, String status) {
        Optional<Cron> optionalCron = cronRepository.findById(id);
        if (optionalCron.isPresent()) {
            Cron cron = optionalCron.get();
            try {
                CronStatus cronStatus = CronStatus.valueOf(status);
                cron.setStatus(cronStatus);
                cronRepository.save(cron);
                return cron;
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Invalid status value: " + status);
            }
        } else {
            throw new ResourceNotFoundException("Cron with ID " + id + " not found.");
        }
    }

    public List<Cron> getActiveCrons() {
        return cronRepository.findByStatus(CronStatus.START);
    }
}

