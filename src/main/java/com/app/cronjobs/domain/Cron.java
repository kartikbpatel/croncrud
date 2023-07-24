package com.app.cronjobs.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.app.cronjobs.enums.CronStatus;
import lombok.Data;
@Data
@Entity
@Table(name = "Crons")
public class Cron extends BaseEntity{
    private String title;
    private String expression;
//    private boolean isRunning;
    @Enumerated(EnumType.STRING)
    private CronStatus status;
}
