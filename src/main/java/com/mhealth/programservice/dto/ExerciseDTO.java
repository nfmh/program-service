package com.mhealth.programservice.dto;

import lombok.Data;

@Data
public class ExerciseDTO {
    private Long id;
    private String name;
    private String description;
    private int caloriesBurned;
}