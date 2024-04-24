package com.mhealth.programservice.dto;

import lombok.Data;

@Data
public class FoodDTO {
    private Long id;
    private String name;
    private String description;
    private int calories;
}

