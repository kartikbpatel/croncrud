package com.app.cronjobs.dto;

import lombok.Data;

@Data
public class CreateCronDTO {
    private String title;
    private String expression;
}
