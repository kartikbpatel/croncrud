package com.app.cronjobs.repository;
import com.app.cronjobs.domain.Cron;
import com.app.cronjobs.enums.CronStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CronRepository extends JpaRepository<Cron, Long> {
    List<Cron> findByStatus(CronStatus cronStatus);
}