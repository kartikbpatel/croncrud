package com.app.cronjobs.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.app.cronjobs.enums.CronStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Crons")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cron extends BaseEntity{
    private String title;
    private String expression;
    @Enumerated(EnumType.STRING)
    private CronStatus status;
}
