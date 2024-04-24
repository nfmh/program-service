package com.mhealth.programservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhealth.programservice.entity.Exercise;
import com.mhealth.programservice.repository.ExerciseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class ExerciseControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @BeforeEach
    void setUp() {
        exerciseRepository.deleteAll();
    }

    @Test
    void testGetExerciseById() throws Exception {
        Exercise exercise = new Exercise();
        exercise.setName("Test Exercise");
        exercise.setDescription("Test Description");
        exercise.setCaloriesBurned(200);

        exercise = exerciseRepository.save(exercise);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/program/exercises/{exerciseId}", exercise.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        Exercise responseExercise = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Exercise.class);
        assertEquals(exercise.getName(), responseExercise.getName());
    }

    @Test
    void testGetAllExercises() throws Exception {
        Exercise exercise1 = new Exercise();
        exercise1.setName("Exercise 1");
        exercise1.setDescription("Description 1");
        exercise1.setCaloriesBurned(100);

        Exercise exercise2 = new Exercise();
        exercise2.setName("Exercise 2");
        exercise2.setDescription("Description 2");
        exercise2.setCaloriesBurned(200);

        exercise1 = exerciseRepository.save(exercise1);
        exercise2 = exerciseRepository.save(exercise2);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/program/exercises")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        Exercise[] exercises = objectMapper.readValue(content, Exercise[].class);
        assertEquals(2, exercises.length);
    }

    @Test
    void testGetExerciseById_NotFound() throws Exception {
        Long nonExistentExerciseId = 999L;

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/program/exercises/{exerciseId}", nonExistentExerciseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void testGetExerciseById_InvalidId() throws Exception {
        String invalidExerciseId = "invalid_id";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/program/exercises/{exerciseId}", invalidExerciseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }
}
