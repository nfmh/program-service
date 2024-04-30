package com.mhealth.programservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhealth.programservice.controller.ExerciseController;
import com.mhealth.programservice.entity.Exercise;
import com.mhealth.programservice.service.ProgramService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExerciseController.class)
@AutoConfigureMockMvc
 class ExerciseIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProgramService programService;

    @Test
    void getExerciseById_ReturnsExercise() throws Exception {
        Exercise exercise = new Exercise();
        exercise.setId(1L);
        exercise.setName("Running");
        exercise.setDescription("Run for cardio");
        exercise.setCaloriesBurned(500);

        when(programService.getExerciseById(1L)).thenReturn(exercise);

        mockMvc.perform(get("/program/exercises/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Running"))
                .andExpect(jsonPath("$.description").value("Run for cardio"))
                .andExpect(jsonPath("$.caloriesBurned").value(500));
    }


    @Test
     void getAllExercises_ReturnsAllExercises() throws Exception {
        Exercise exercise1 = new Exercise();
        exercise1.setId(1L);
        exercise1.setName("Running");
        exercise1.setDescription("Run for cardio");
        exercise1.setCaloriesBurned(500);

        Exercise exercise2 = new Exercise();
        exercise2.setId(2L);
        exercise2.setName("Cycling");
        exercise2.setDescription("Cycle for cardio");
        exercise2.setCaloriesBurned(350);

        List<Exercise> exerciseList = Arrays.asList(exercise1, exercise2);

        when(programService.getAllExercises()).thenReturn(exerciseList);

        mockMvc.perform(get("/program/exercises")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Running"))
                .andExpect(jsonPath("$[0].description").value("Run for cardio"))
                .andExpect(jsonPath("$[0].caloriesBurned").value(500))
                .andExpect(jsonPath("$[1].name").value("Cycling"))
                .andExpect(jsonPath("$[1].description").value("Cycle for cardio"))
                .andExpect(jsonPath("$[1].caloriesBurned").value(350));
    }

    @Test
    void getNonExistentExercise_ReturnsNotFound() throws Exception {
        when(programService.getExerciseById(100L)).thenReturn(null);

        mockMvc.perform(get("/program/exercises/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
