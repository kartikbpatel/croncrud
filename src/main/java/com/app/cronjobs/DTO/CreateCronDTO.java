package com.app.cronjobs.DTO;

import lombok.Data;

@Data
public class CreateCronDTO {
    private String title;
    private String expression;
}
