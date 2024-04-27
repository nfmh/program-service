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
        // No need to delete all exercises as we're using actual data
    }

    @Test
    void testGetExerciseById() throws Exception {
        // Assuming the first exercise in the database is used for testing
        Exercise exercise = exerciseRepository.findAll().get(0);

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
        // Fetch the actual count of exercises from the database
        long actualExerciseCount = exerciseRepository.count();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/program/exercises")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        Exercise[] exercises = objectMapper.readValue(content, Exercise[].class);

        // Compare the actual count of exercises with the count of items returned by the endpoint
        assertEquals(actualExerciseCount, exercises.length);
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
