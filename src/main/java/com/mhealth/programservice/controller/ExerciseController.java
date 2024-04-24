package com.mhealth.programservice.controller;

import com.mhealth.programservice.entity.Exercise;
import com.mhealth.programservice.service.ProgramService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/program/exercises")
public class ExerciseController {

    private final ProgramService programService;

    public ExerciseController(ProgramService programService) {
        this.programService = programService;
    }


    @GetMapping("/{exerciseId}")
    public ResponseEntity<Exercise> getExerciseId(@PathVariable Long exerciseId) {
        Exercise exercise = programService.getExerciseById(exerciseId);
        return exercise != null ? ResponseEntity.ok(exercise) : ResponseEntity.notFound().build();
    }


    @GetMapping
    public ResponseEntity<List<Exercise>> getAllExercises() {
        List<Exercise> exercises = programService.getAllExercises();
        return ResponseEntity.ok(exercises);
    }
}