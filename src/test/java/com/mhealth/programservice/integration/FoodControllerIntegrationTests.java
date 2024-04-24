package com.mhealth.programservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhealth.programservice.entity.Food;
import com.mhealth.programservice.repository.FoodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class FoodControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FoodRepository foodRepository;

    @BeforeEach
    void setUp() {
        foodRepository.deleteAll();
    }

    @Test
    void testGetFoodById() throws Exception {
        Food food = new Food();
        food.setName("Test Food");
        food.setDescription("Test Description");
        food.setCalories(100);

        food = foodRepository.save(food);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/program/food/{foodId}", food.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        Food responseFood = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Food.class);
        assertEquals(food.getName(), responseFood.getName());
    }

    @Test
    void testGetAllFood() throws Exception {
        Food food1 = new Food();
        food1.setName("Food 1");
        food1.setDescription("Description 1");
        food1.setCalories(100);

        Food food2 = new Food();
        food2.setName("Food 2");
        food2.setDescription("Description 2");
        food2.setCalories(200);

        food1 = foodRepository.save(food1);
        food2 = foodRepository.save(food2);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/program/food")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        Food[] foods = objectMapper.readValue(content, Food[].class);
        assertEquals(2, foods.length);
    }

    @Test
    void testGetFoodById_NotFound() throws Exception {
        Long nonExistentFoodId = 999L;

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/program/food/{foodId}", nonExistentFoodId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void testGetFoodById_InvalidId() throws Exception {
        String invalidFoodId = "invalid_id";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/program/food/{foodId}", invalidFoodId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

// Add more integration tests as needed

}
