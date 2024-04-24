package com.mhealth.programservice.service;

import com.mhealth.programservice.entity.Food;
import com.mhealth.programservice.entity.Exercise;
import com.mhealth.programservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramServiceImpl implements ProgramService {

    private final FoodRepository foodRepository;
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ProgramServiceImpl(FoodRepository foodRepository, ExerciseRepository exerciseRepository) {
        this.foodRepository = foodRepository;
        this.exerciseRepository = exerciseRepository;
    }


    @Override
    public Food getFoodById(Long foodId) {
        return foodRepository.findById(foodId).orElse(null);
    }

    @Override
    public List<Food> getAllFood() {
        return foodRepository.findAll();
    }

    @Override
    public Exercise getExerciseById(Long exerciseById) {
        return exerciseRepository.findById(exerciseById).orElse(null);
    }

    @Override
    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }
}
