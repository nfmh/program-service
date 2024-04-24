package com.mhealth.programservice.unit;

import com.mhealth.programservice.controller.ExerciseController;
import com.mhealth.programservice.entity.Exercise;
import com.mhealth.programservice.service.ProgramService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

 class ExerciseControllerTests {

    @Mock
    private ProgramService programService;

    @InjectMocks
    private ExerciseController exerciseController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testGetExerciseById() {
        long exerciseId = 1L;
        Exercise exercise = new Exercise();
        exercise.setId(exerciseId);
        exercise.setName("Test Exercise");

        when(programService.getExerciseById(exerciseId)).thenReturn(exercise);

        ResponseEntity<Exercise> responseEntity = exerciseController.getExerciseId(exerciseId);
        Exercise responseExercise = responseEntity.getBody();

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(exercise.getName(), responseExercise.getName());
    }

    @Test
     void testGetAllExercises() {
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(new Exercise());
        exerciseList.add(new Exercise());

        when(programService.getAllExercises()).thenReturn(exerciseList);

        ResponseEntity<List<Exercise>> responseEntity = exerciseController.getAllExercises();
        List<Exercise> responseExerciseList = responseEntity.getBody();

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(2, responseExerciseList.size());
    }

    @Test
     void testGetExerciseById_NotFound() {
        long nonExistentExerciseId = 999L;

        when(programService.getExerciseById(nonExistentExerciseId)).thenReturn(null);

        ResponseEntity<Exercise> responseEntity = exerciseController.getExerciseId(nonExistentExerciseId);

        assertEquals(404, responseEntity.getStatusCodeValue());
    }
}
