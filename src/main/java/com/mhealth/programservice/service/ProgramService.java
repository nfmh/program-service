package com.mhealth.programservice.service;
import com.mhealth.programservice.entity.Food;
import com.mhealth.programservice.entity.Exercise;

import java.util.List;


public interface ProgramService {

    Food getFoodById(Long foodId);
    Exercise getExerciseById(Long exerciseId);
    List<Food> getAllFood();
    List<Exercise> getAllExercises();

}